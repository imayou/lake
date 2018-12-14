package io.ayou.lake.remoting.netty.client;

import io.ayou.lake.registry.zookeeper.Discovery;
import io.ayou.lake.registry.zookeeper.InstanceDetails;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.curator.x.discovery.ServiceInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author AYOU
 */
public class ConnectionManager {

    private static Logger logger = LoggerFactory.getLogger(ConnectionManager.class);

    @Autowired
    private Discovery discovery;

    private EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
    private ConcurrentHashMap<String, RpcClientHandler> handlers = new ConcurrentHashMap<>();

    public RpcClientHandler getHandler(String serviceName) {
        List<ServiceInstance<InstanceDetails>> services = discovery.get(serviceName);
        if (services.size() == 0) {
            return null;
        }
        Random random = new Random();
        int i = random.nextInt(services.size());
        ServiceInstance<InstanceDetails> instanceDetails = services.get(i);
        RpcClientHandler handler = connect(instanceDetails.getAddress().replace("lake://",""), instanceDetails.getPort());
        return handler;
    }

    private RpcClientHandler connect(String address, Integer port) {
        RpcClientHandler handler = handlers.get(address);
        if (handler != null) {
            return handler;
        }
        synchronized (this) {
            handler = handlers.get(address);
            if (handler != null) {
                return handler;
            }
            Bootstrap bootstrap = new Bootstrap();
            ClientChannelInitializer channelInitializer = new ClientChannelInitializer();
            try {
                bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(channelInitializer).option(ChannelOption.SO_KEEPALIVE, true);
                ChannelFuture connectFuture = bootstrap.connect(address, port).sync();
                connectFuture.sync();
                handlers.put(address, channelInitializer.getRpcClientHandler());
                return channelInitializer.getRpcClientHandler();
            } catch (Exception e) {
                e.printStackTrace();
                logger.warn(e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }
}
