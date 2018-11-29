package io.ayou.lake.demo.rpc.server;

import io.ayou.lake.demo.rpc.handler.RPCDecoder;
import io.ayou.lake.demo.rpc.handler.RPCEncoder;
import io.ayou.lake.demo.rpc.pojo.RPCResponse;
import io.ayou.lake.demo.rpc.pojo.RpcCall;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by haoyifen on 2017/6/19 18:52.
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
	@Autowired
	RPCServerHandler rpcServerHandler;
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline()
				.addLast(new LengthFieldBasedFrameDecoder(65535, 0, 4, 0, 4))
				.addLast(new RPCDecoder<RpcCall>() {
				})
				.addLast(new LengthFieldPrepender(4, false))
				.addLast(new RPCEncoder<RPCResponse>() {
				})
				.addLast(rpcServerHandler);
	}
}
