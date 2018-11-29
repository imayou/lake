package io.ayou.lake.demo.rpc.autoconfiguration;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by haoyifen on 2017/6/19 18:02.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ServiceDiscoveryAutoConfiguration.class)
public @interface EnableServiceDiscovery {
}
