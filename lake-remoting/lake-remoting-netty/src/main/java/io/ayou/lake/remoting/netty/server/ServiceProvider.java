package io.ayou.lake.remoting.netty.server;

import co.paralleluniverse.fibers.Suspendable;
import io.ayou.lake.container.spring.annotion.RpcService;
import io.ayou.lake.protocol.RpcRequest;
import io.ayou.lake.registry.zookeeper.InstanceDetails;
import io.ayou.lake.registry.zookeeper.Register;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceType;
import org.apache.curator.x.discovery.UriSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author AYOU
 */
public class ServiceProvider implements ApplicationContextAware {
    private static Logger logger = LoggerFactory.getLogger(ServiceProvider.class);

    private ApplicationContext context;
    public static Map<String, Object> classNameToServiceMap = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
        Map<String, Object> serviceBeanMap = context.getBeansWithAnnotation(RpcService.class);
        if (serviceBeanMap.isEmpty()) {
            return;
        }
        try {
            for (Object serviceBean : serviceBeanMap.values()) {
                RpcService rpcService = serviceBean.getClass().getAnnotation(RpcService.class);
                String serviceName = rpcService.value().getName();
                classNameToServiceMap.put(serviceName, serviceBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Suspendable
    public Object invoke(RpcRequest request) throws Exception {
        Object result = null;
        // 获取服务实例
        String serviceName = request.getInterfaceName();
        Object serviceBean = classNameToServiceMap.get(serviceName);
        if (serviceBean == null) {
            throw new RuntimeException(String.format("can not find service bean by key: %s", serviceName));
        }
        // 获取反射调用所需的变量
        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();

        // 反射
        Method method = serviceClass.getMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method.invoke(serviceBean, parameters);
        //Cglib reflect
        //FastClass serviceFastClass = FastClass.create(serviceClass);
        //FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
        //return serviceFastMethod.invoke(serviceBean, parameters);
    }
}
