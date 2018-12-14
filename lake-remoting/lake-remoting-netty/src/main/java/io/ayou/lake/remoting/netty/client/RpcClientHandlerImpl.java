package io.ayou.lake.remoting.netty.client;

import co.paralleluniverse.strands.SettableFuture;
import io.ayou.lake.protocol.RpcRequest;
import io.ayou.lake.protocol.RpcResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.util.Assert;

import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author AYOU
 */
public class RpcClientHandlerImpl extends SimpleChannelInboundHandler<RpcResponse> implements RpcClientHandler {
    private Channel channel;
    private SocketAddress serverAddress;
    private ConcurrentHashMap<String, SettableFuture<RpcResponse>> requestContext = new ConcurrentHashMap<>(16);

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        channel = ctx.channel();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        serverAddress = ctx.channel().remoteAddress();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
        String requestId = msg.getRequestId();
        SettableFuture<RpcResponse> rpcResponseFuture = requestContext.get(requestId);
        Assert.notNull(rpcResponseFuture, "rpcResponseFuture for requestId " + requestId + " should not be null");
        requestContext.remove(requestId);
        rpcResponseFuture.set(msg);
    }

    @Override
    public SettableFuture<RpcResponse> sendRequest(RpcRequest request) {
        String requestId = request.getId();
        SettableFuture<RpcResponse> future = new SettableFuture<>();
        requestContext.put(requestId, future);
        Assert.notNull(requestId, "requestId not null");
        channel.writeAndFlush(request);
        return future;
    }

    public SocketAddress getServerAddress() {
        return serverAddress;
    }
}
