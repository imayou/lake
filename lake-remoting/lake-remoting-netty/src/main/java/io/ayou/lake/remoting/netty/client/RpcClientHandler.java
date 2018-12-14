package io.ayou.lake.remoting.netty.client;

import co.paralleluniverse.strands.SettableFuture;
import io.ayou.lake.protocol.RpcRequest;
import io.ayou.lake.protocol.RpcResponse;

/**
 * @author AYOU
 */
public interface RpcClientHandler {
    SettableFuture<RpcResponse> sendRequest(RpcRequest rpcRequest);
}