package io.ayou.lake.demo.rpc.pojo;


/**
 * Created by haoyifen on 2017/6/16 22:08.
 */
public class RPCResponse {
	private String requestId;
	private int code;
	private String msg;
	private Object data;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public static RPCResponse ok(String requestId, Object data) {
		RPCResponse rpcResponse = new RPCResponse();
		rpcResponse.setRequestId(requestId);
		rpcResponse.setCode(0);
		rpcResponse.setData(data);
		return rpcResponse;
	}
}
