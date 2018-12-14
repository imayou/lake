package io.ayou.lake.remoting.netty.client;

import co.paralleluniverse.fibers.Suspendable;
import co.paralleluniverse.strands.SettableFuture;
import io.ayou.lake.protocol.RpcRequest;
import io.ayou.lake.protocol.RpcResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Created by haoyifen on 2017/6/17 23:44.
 */
public class ServiceProxyUtil {
    @Autowired
    private ConnectionManager connectionManager;

    public <T> T serviceProxy(Class<T> serviceInterface) {
        Object proxyInstance = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{serviceInterface}, new InvocationHandler() {
            @Suspendable
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws ExecutionException, InterruptedException {
                if (Object.class.equals(method.getDeclaringClass())) {
                    switch (method.getName()) {
                        case "equals":
                            return proxy == args[0];
                        case "hashCode":
                            return System.identityHashCode(proxy);
                        case "toString":
                            return proxy.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(proxy)) + ", with InvocationHandler " + this;
                        default:
                            throw new IllegalStateException(String.valueOf(method));
                    }
                }
                String name = method.getDeclaringClass().getName();
                String methodName = method.getName();
                //RpcResponse
                RpcRequest request = new RpcRequest();
                request.setId(UUID.randomUUID().toString());
                request.setInterfaceName(name);
                request.setMethodName(methodName);
                Class<?>[] parameterTypes = method.getParameterTypes();
                //String[] parameterTypeStrings =
                Arrays.stream(parameterTypes).map(Class::getName).toArray(String[]::new);
                request.setParameterTypes(method.getParameterTypes());
                request.setParameters(args);
                RpcClientHandler rpcClientHandler = connectionManager.getHandler(serviceInterface.getName());
                SettableFuture<RpcResponse> rpcResponseSettableFuture = rpcClientHandler.sendRequest(request);
                RpcResponse rpcResponse = rpcResponseSettableFuture.get();
                return rpcResponse.getResult();
            }
        });
        return (T) proxyInstance;
    }
}
