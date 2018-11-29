package io.ayou.lake.demo.rpc.autoconfiguration;

import io.ayou.lake.demo.rpc.client.ConnectionManager;
import io.ayou.lake.demo.rpc.client.ServiceProxyUtil;
import io.ayou.lake.demo.rpc.registry.ServiceDiscovery;
import org.springframework.context.annotation.Bean;

/**
 * Created by haoyifen on 2017/6/19 18:03.
 */
public class ServiceDiscoveryAutoConfiguration {
	@Bean
	public ServiceDiscovery serviceDiscovery() {
		return new ServiceDiscovery();
	}
	@Bean
	public ConnectionManager connectionManager() {
		return new ConnectionManager();
	}
	@Bean
	public ServiceProxyUtil serviceProxyUtil(){
		return new ServiceProxyUtil();
	}
}
