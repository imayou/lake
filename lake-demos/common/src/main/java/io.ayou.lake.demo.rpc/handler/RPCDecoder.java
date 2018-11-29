package io.ayou.lake.demo.rpc.handler;

import io.ayou.lake.demo.rpc.serialize.SerializeUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.springframework.core.ResolvableType;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by haoyifen on 2017/6/16 21:27.
 */
public abstract class RPCDecoder<T> extends ByteToMessageDecoder {
	private Class<T> aClass;

	public RPCDecoder() {
		//对于获取有多重泛型的就会出错
///		 Type superClass = getClass().getGenericSuperclass();
//		 aClass = (Class<T>) ((ParameterizedType) superClass).getActualTypeArguments()[0];
		ResolvableType resolvableType = ResolvableType.forInstance(this);
		ResolvableType genericType = resolvableType.getSuperType().getGeneric(0);
		Class<?> rawClass = genericType.getRawClass();
		aClass = (Class<T>) rawClass;
	}

	public static void main(String[] args) {
		RPCDecoder<List<String>> rpcCallRPCDecoder = new RPCDecoder<List<String>>(){};
		Assert.isTrue(rpcCallRPCDecoder.aClass.equals(List.class),"class equals");
	}

	public Class<T> getaClass() {
		return aClass;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		int readableLength = in.readableBytes();
		byte[] bytes = new byte[readableLength];
		in.readBytes(bytes);
		T object = SerializeUtils.deserialize(bytes, aClass);
		out.add(object);
	}
}
