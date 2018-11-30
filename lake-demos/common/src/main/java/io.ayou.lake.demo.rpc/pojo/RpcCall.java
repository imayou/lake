package io.ayou.lake.demo.rpc.pojo;


/**
 * Created by haoyifen on 2017/6/1 9:52.
 */
public class RpcCall {
	private String id;
	private String className;
	private String methodName;
	private String[] parameterTypes;
	private Object[] parameters;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String[] getParameterTypes() {
		return parameterTypes;
	}

	public void setParameterTypes(String[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

	public RpcCall copy() {
		RpcCall other = new RpcCall();
		this.id = other.id;
		this.className = other.className;
		this.methodName = other.methodName;
		this.parameterTypes = other.parameterTypes;
		this.parameters = other.parameters;
		return other;
	}
}
