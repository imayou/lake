package io.ayou.lake.demo.rpc.client;

import co.paralleluniverse.strands.SettableFuture;
import io.ayou.lake.demo.rpc.pojo.RPCResponse;
import io.ayou.lake.demo.rpc.pojo.RpcCall;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.util.Assert;

import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by haoyifen on 2017/6/17 16:05.
 * @author yangs
 */
public class RPCClientHandlerImpl extends SimpleChannelInboundHandler<RPCResponse> implements RPCClientHandler{


	private Channel channel;
	private SocketAddress serverAddress;
	private ConcurrentHashMap<String, SettableFuture<RPCResponse>> requestContext = new ConcurrentHashMap<>(16);

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
	protected void channelRead0(ChannelHandlerContext ctx, RPCResponse msg) throws Exception {
		String requestId = msg.getRequestId();
		SettableFuture<RPCResponse> rpcResponseFuture = requestContext.get(requestId);
		Assert.notNull(rpcResponseFuture, "rpcResponseFuture for requestId " + requestId + " should not be null");
		requestContext.remove(requestId);
		rpcResponseFuture.set(msg);
	}

	@Override
	public SettableFuture<RPCResponse> sendRequest(RpcCall rpcCall) {
		String requestId = rpcCall.getId();
		SettableFuture<RPCResponse> future = new SettableFuture<>();
		requestContext.put(requestId, future);
		Assert.notNull(requestId, "requestId not null");
		channel.writeAndFlush(rpcCall);
		return future;
	}

	public SocketAddress getServerAddress() {
		return serverAddress;
	}
}
