package io.ayou.lake.registry.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Client {
    private String address;
    private String path;
    private CuratorFramework client;
    private ServiceDiscovery<InstanceDetails> serviceDiscovery;

    public Client(@Value("${lake.register.address}") String address, @Value("${lake.register.path:/lake}") String path) {
        this.address = address;
        this.path = path;
        try {
            this.client = CuratorFrameworkFactory.newClient(address, 15 * 1000, 5000,
                    new ExponentialBackoffRetry(1000, 3));
            this.client.start();
            JsonInstanceSerializer<InstanceDetails> serializer = new JsonInstanceSerializer<InstanceDetails>(InstanceDetails.class);
            serviceDiscovery = ServiceDiscoveryBuilder.builder(InstanceDetails.class).client(client).serializer(serializer).basePath(path).build();
            serviceDiscovery.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getAddress() {
        return address;
    }

    public String getPath() {
        return path;
    }

    public CuratorFramework getClient() {
        return client;
    }

    public ServiceDiscovery<InstanceDetails> getServiceDiscovery() {
        return serviceDiscovery;
    }
}
