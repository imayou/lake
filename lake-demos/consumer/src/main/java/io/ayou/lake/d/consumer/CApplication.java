package io.ayou.lake.d.consumer;

import co.paralleluniverse.fibers.Fiber;
import io.ayou.lake.d.api.UserService;
import io.ayou.lake.d.domain.User;
import io.ayou.lake.demo.rpc.autoconfiguration.EnableServiceDiscovery;
import io.ayou.lake.demo.rpc.client.ServiceProxyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

/**
 * @author AYOU
 */
@EnableServiceDiscovery
@SpringBootApplication
public class CApplication {
    private static Logger logger = LoggerFactory.getLogger(CApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CApplication.class, args);
    }

    @Autowired
    ServiceProxyUtil serviceProxyUtil;

    @PostConstruct
    public void test(){
        UserService userService = serviceProxyUtil.serviceProxy("test", UserService.class);
        User u = userService.get("1");
        System.out.println(u);
    }

    /*@Bean
    public CommandLineRunner runner(ServiceProxyUtil serviceProxyUtil) {
        return (String... args) -> {
            UserService userService = serviceProxyUtil.serviceProxy("test", UserService.class);
            for (int i = 0; i < 10; i++) {
                Instant startNew = Instant.now();
                logger.info("start new");
                int total = 10000;
                Fiber[] fibers = new Fiber[total];
                for (int j = 0; j < total; j++) {
                    int finalI = j;
                    Fiber<Void> fiber = new Fiber<Void>(() -> {
                        User users = userService.get("1");
                        if (finalI % (total - 1) == 0) {
                            logger.debug(finalI + " : " + users.toString());
                        }
                    }).start();
                    fibers[j] = fiber;
                }
                for (Fiber fiber : fibers) {
                    fiber.join();
                }
                Instant finish = Instant.now();
                long usingMills = Duration.between(startNew, finish).toMillis();
                logger.info("finish {} reqs, using {} ms, rqs is {}", total, usingMills, total * 1000 / usingMills);
                System.out.println(" ");
            }
        };
    }*/
}
