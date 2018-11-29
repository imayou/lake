package io.ayou.lake.demo.rpc.autoconfiguration;


import io.ayou.lake.demo.rpc.registry.ServiceRegistry;
import io.ayou.lake.demo.rpc.server.RPCServerHandler;
import io.ayou.lake.demo.rpc.server.RandomPortServiceStarter;
import io.ayou.lake.demo.rpc.server.ServerChannelInitializer;
import io.ayou.lake.demo.rpc.server.ServiceProvider;
import org.springframework.context.annotation.Bean;

/**
 * Created by haoyifen on 2017/6/19 18:02.
 */
public class ServiceRegistryAutoConfiguration {
    @Bean
    public ServiceRegistry serviceRegistry() {
        return new ServiceRegistry();
    }

    @Bean
    public ServiceProvider serviceProvider() {
        return new ServiceProvider();
    }

    @Bean
    public RPCServerHandler rpcServerHandler() {
        return new RPCServerHandler();
    }

    @Bean
    public ServerChannelInitializer serverChannelInitializer() {
        return new ServerChannelInitializer();
    }

    @Bean
    public RandomPortServiceStarter serviceStarter() {
        return new RandomPortServiceStarter();
    }
}
