package com.keqiaokeji.module.httpsdk.domain;

import com.keqiaokeji.module.httpsdk.utils.SdkContext;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;


/**
 * Request请求时从接口中解析出的数据
 * 
 * @author keqikeji
 */
public class SdkRequest {

	/**
	 * 参数列表
	 */
	private Map<String, String> params = new HashMap<String, String>();
	
	
	/**
	 * 请求服务的命名空间
	 */
	private String interfaceNamespace;

	/**
	 * 函数的命名空间
	 */
	private String methodNamespace;

	/**
	 * 远程服务的URL
	 */
	public String getRequestUrl() {
		return SdkContext.serverBaseUrl + getInterfaceNamespace() + getMethodNamespace();
	}

	/**
	 * 使用POST还是GET方式
	 */
	private String method;

	/**
	 * 函数返回值的类型
	 */
	private Type returnType;

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public String getInterfaceNamespace() {
		return interfaceNamespace;
	}

	public void setInterfaceNamespace(String interfaceNamespace) {
		this.interfaceNamespace = interfaceNamespace;
	}

	public String getMethodNamespace() {
		return methodNamespace;
	}

	public void setMethodNamespace(String methodNamespace) {
		this.methodNamespace = methodNamespace;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Type getReturnType() {
		return returnType;
	}

	public void setReturnType(Type returnType) {
		this.returnType = returnType;
	}
	
	
	
}
