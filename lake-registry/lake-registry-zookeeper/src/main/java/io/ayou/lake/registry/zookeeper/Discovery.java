package io.ayou.lake.registry.zookeeper;

import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class Discovery {

    @Autowired
    private Client client;

    private void listInstances() throws Exception {

        try {
            Collection<String> serviceNames = client.getServiceDiscovery().queryForNames();
            System.out.println(serviceNames.size() + " type(s)");
            for (String serviceName : serviceNames) {
                Collection<ServiceInstance<InstanceDetails>> instances = client.getServiceDiscovery().queryForInstances(serviceName);
                System.out.println(serviceName);
                for (ServiceInstance<InstanceDetails> instance : instances) {
                    // outputInstance(instance);
                }
            }
        } finally {
            CloseableUtils.closeQuietly(client.getServiceDiscovery());
        }
    }

    private void get(String serviceName) {
        try {
            Collection<ServiceInstance<InstanceDetails>> serviceNames = client.getServiceDiscovery().queryForInstances(serviceName);
            serviceNames.stream().forEach(instance -> {
                //outputInstance(instance);
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseableUtils.closeQuietly(client.getServiceDiscovery());
        }

    }
}
