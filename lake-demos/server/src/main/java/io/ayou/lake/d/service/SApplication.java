package io.ayou.lake.d.service;

import io.ayou.lake.demo.rpc.autoconfiguration.EnableServiceRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author AYOU
 */

@EnableServiceRegistry
@SpringBootApplication(scanBasePackages = "io.ayou.lake")
public class SApplication {
    public static void main(String[] args) {
        SpringApplication.run(SApplication.class, args);
    }
}
