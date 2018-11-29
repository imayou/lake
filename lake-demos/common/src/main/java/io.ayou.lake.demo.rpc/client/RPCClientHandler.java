package io.ayou.lake.demo.rpc.client;


import co.paralleluniverse.strands.SettableFuture;
import io.ayou.lake.demo.rpc.pojo.RPCResponse;
import io.ayou.lake.demo.rpc.pojo.RpcCall;

/**
 * Created by haoyifen on 2017/6/19 14:58.
 */
public interface RPCClientHandler {
    SettableFuture<RPCResponse> sendRequest(RpcCall rpcCall);
}