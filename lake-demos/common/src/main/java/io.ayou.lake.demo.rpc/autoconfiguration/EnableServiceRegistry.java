package io.ayou.lake.demo.rpc.autoconfiguration;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by haoyifen on 2017/6/19 17:57.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ServiceRegistryAutoConfiguration.class)
public @interface EnableServiceRegistry {
}
