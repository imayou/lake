package io.ayou.lake.demo.rpc.handler;

import io.ayou.lake.demo.rpc.serialize.SerializeUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by haoyifen on 2017/6/16 22:11.
 */
public abstract class RPCEncoder<T> extends MessageToByteEncoder<T> {
	@Override
	protected void encode(ChannelHandlerContext ctx, T msg, ByteBuf out) throws Exception {
		byte[] bytes = SerializeUtils.serialize(msg);
		out.writeBytes(bytes);
	}
}
