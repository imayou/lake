package io.ayou.lake.provider;

import io.ayou.lake.registry.zookeeper.Register;
import io.ayou.lake.remoting.netty.server.RPCServerHandler;
import io.ayou.lake.remoting.netty.server.ServerChannelInitializer;
import io.ayou.lake.remoting.netty.server.ServiceProvider;
import io.ayou.lake.remoting.netty.server.ServiceStarter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


/**
 * @ClassName PApplication
 */
@SpringBootApplication(scanBasePackages = "io.ayou.lake")
public class PApplication {

    @Bean
    public Register serviceRegistry() {
        return new Register();
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
    public ServiceStarter serviceStarter() {
        return new ServiceStarter();
    }

    public static void main(String[] args) {
        SpringApplication.run(PApplication.class, args);
    }
}
