package io.ayou.lake.consumer;

import io.ayou.lake.example.api.api.UserService;
import io.ayou.lake.example.api.domain.User;
import io.ayou.lake.remoting.netty.client.ConnectionManager;
import io.ayou.lake.remoting.netty.client.ServiceProxyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

/**
 * @ClassName CApplication
 */
@SpringBootApplication(scanBasePackages = "io.ayou.lake")
public class CApplication {
    @Bean
    public ConnectionManager connectionManager() {
        return new ConnectionManager();
    }

    @Bean
    public ServiceProxyUtil serviceProxyUtil() {
        return new ServiceProxyUtil();
    }

    @Autowired
    ServiceProxyUtil serviceProxyUtil;

    public static void main(String[] args) {
        SpringApplication.run(CApplication.class, args);
    }

    @PostConstruct
    public void test() {
        UserService userService = serviceProxyUtil.serviceProxy(UserService.class);
        User u = userService.get("1");
        System.out.println(u);
    }
}
