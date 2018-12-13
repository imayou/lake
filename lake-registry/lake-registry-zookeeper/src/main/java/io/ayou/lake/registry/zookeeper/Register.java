package io.ayou.lake.registry.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceInstance;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class Register {
    @Autowired
    private Client client;


    public void registerService(ServiceInstance<InstanceDetails> serviceInstance) throws Exception {
        client.getServiceDiscovery().registerService(serviceInstance);
    }

    public void unregisterService(ServiceInstance<InstanceDetails> serviceInstance) throws Exception {
        client.getServiceDiscovery().unregisterService(serviceInstance);

    }

    public void updateService(ServiceInstance<InstanceDetails> serviceInstance) throws Exception {
        client.getServiceDiscovery().updateService(serviceInstance);

    }

    public void close() throws IOException {
        client.getServiceDiscovery().close();
    }
}
