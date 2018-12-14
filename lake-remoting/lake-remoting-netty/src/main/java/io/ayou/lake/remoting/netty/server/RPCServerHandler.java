package io.ayou.lake.remoting.netty.server;

import co.paralleluniverse.fibers.FiberExecutorScheduler;
import co.paralleluniverse.fibers.FiberScheduler;
import io.ayou.lake.protocol.RpcRequest;
import io.ayou.lake.protocol.RpcResponse;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.*;

/**
 * Created by haoyifen on 2017/6/16 11:30.
 */
@Sharable
public class RPCServerHandler extends SimpleChannelInboundHandler<RpcRequest> {
	private final Executor executor = Executors.newFixedThreadPool(20);
	private final FiberScheduler scheduler = new FiberExecutorScheduler("rpcHandler", executor);
	@Autowired
	private ServiceProvider serviceProvider;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RpcRequest request) throws Exception {
		scheduler.<Void> newFiber(() -> {
			try {
				Object result = serviceProvider.invoke(request);
				RpcResponse response = RpcResponse.ok(request.getId(), result);
				ctx.writeAndFlush(response);
			}catch (Exception e){
				e.printStackTrace();
			}
			return null;
		}).start();
	}
}
