package io.ayou.lake.demo.rpc.server;

import co.paralleluniverse.fibers.FiberExecutorScheduler;
import co.paralleluniverse.fibers.FiberScheduler;
import io.ayou.lake.demo.rpc.pojo.RPCResponse;
import io.ayou.lake.demo.rpc.pojo.RpcCall;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by haoyifen on 2017/6/16 11:30.
 */
@Sharable
public class RPCServerHandler extends SimpleChannelInboundHandler<RpcCall> {
    private final ExecutorService executor = Executors.newFixedThreadPool(20);
    private final FiberScheduler scheduler = new FiberExecutorScheduler("rpcHandler", executor);
    @Autowired
    private ServiceProvider serviceProvider;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcCall call) throws Exception {
        scheduler.<Void>newFiber(() -> {
            Object result = serviceProvider.invoke(call);
            RPCResponse response = RPCResponse.ok(call.getId(), result);
            ctx.writeAndFlush(response);
            return null;
        }).start();
    }
}
