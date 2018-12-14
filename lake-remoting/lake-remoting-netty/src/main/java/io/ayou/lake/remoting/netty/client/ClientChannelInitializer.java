package io.ayou.lake.remoting.netty.client;

import io.ayou.lake.protocol.RpcRequest;
import io.ayou.lake.protocol.RpcResponse;
import io.ayou.lake.remoting.netty.handler.RPCDecoder;
import io.ayou.lake.remoting.netty.handler.RPCEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * @author AYOU
 */
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    public RpcClientHandlerImpl getRpcClientHandler() {
        return rpcClientHandler;
    }

    private RpcClientHandlerImpl rpcClientHandler = new RpcClientHandlerImpl();

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(65535, 0, 4, 0, 4))
                .addLast(new RPCDecoder<RpcRequest>() {})
                .addLast(new LengthFieldPrepender(4, false))
                .addLast(new RPCEncoder<RpcResponse>() {})
                .addLast(rpcClientHandler);
    }
}
