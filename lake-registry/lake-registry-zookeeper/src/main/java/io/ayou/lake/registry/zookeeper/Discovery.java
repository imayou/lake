package io.ayou.lake.registry.zookeeper;

import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.x.discovery.ServiceInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author AYOU
 */
@Component
public class Discovery {

    @Autowired
    private Client client;

    public void listInstances() throws Exception {

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

    public List<ServiceInstance<InstanceDetails>> get(String serviceName) {
        try {
            Collection<ServiceInstance<InstanceDetails>> serviceInstance = client.getServiceDiscovery().queryForInstances(serviceName);
            List<ServiceInstance<InstanceDetails>> lists = new ArrayList<>();
            serviceInstance.stream().forEach(instance -> lists.add(instance));
            return lists;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseableUtils.closeQuietly(client.getServiceDiscovery());
        }
        return null;
    }

    public Client getClient() {
        return client;
    }
}
