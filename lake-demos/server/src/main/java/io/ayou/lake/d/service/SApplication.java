package io.ayou.lake.d.service;

import io.ayou.lake.demo.rpc.autoconfiguration.EnableServiceRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author AYOU
 */
@EnableServiceRegistry
@SpringBootApplication
public class SApplication {
    public static void main(String[] args) {
        SpringApplication.run(SApplication.class, args);
    }
}
