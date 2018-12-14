package io.ayou.lake.remoting.netty.server;

import io.ayou.lake.registry.zookeeper.InstanceDetails;
import io.ayou.lake.registry.zookeeper.Register;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceType;
import org.apache.curator.x.discovery.UriSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.SocketUtils;

import javax.annotation.PostConstruct;
import java.net.InetAddress;

/**
 * Created by haoyifen on 2017/6/19 19:08.
 */
public class ServiceStarter implements AutoCloseable{
    private static Logger logger = LoggerFactory.getLogger(ServiceStarter.class);
    @Autowired
    private ServerChannelInitializer serverChannelInitializer;
    @Autowired
    private Register register;
    @Value("${lake.protocol.port}")
    private Integer port;
    @Value("${lake.protocol.address}")
    private String address;

    private NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    private NioEventLoopGroup workerGroup = new NioEventLoopGroup();


    @PostConstruct
    public ChannelFuture start() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try {
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(serverChannelInitializer).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            //int inetPort = SocketUtils.findAvailableTcpPort();
            ChannelFuture bindFuture = serverBootstrap.bind(port).sync();
            bindFuture.addListener(future -> {
                if (future.isSuccess()) {
                    //register.register(inetPort);
                    for (String interfaceName : ServiceProvider.classNameToServiceMap.keySet()) {
                        InstanceDetails instanceDetails = new InstanceDetails(interfaceName, "version", "desc", port, address, "", 1);
                        ServiceInstance<InstanceDetails> serviceInstance = ServiceInstance.<InstanceDetails>builder()
                                .name(interfaceName)
                                .port(port)
                                //address不写的话，会取本地ip
                                .address(address)
                                .payload(instanceDetails)
                                .uriSpec(new UriSpec("{scheme}://{address}:{port}"))
                                .serviceType(ServiceType.DYNAMIC)
                                .build();
                        register.registerService(serviceInstance);
                    }
                    logger.info("register service to zk success with port " + port);
                } else {
                    logger.info("bind to " + port + "failed, exit");
                    System.exit(1);
                }
            });
            return bindFuture;
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
