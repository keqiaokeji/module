package com.keqiaokeji.module.httpsdk.domain;

import org.apache.http.HttpResponse;

/**
 * SOA请求之后的返回信息
 * 
 * @author keqikeji
 * 
 */
public class SdkResponse {

	/**
	 * 请求信息
	 */
	private SdkRequest request;

	/**
	 * 请求之后返回的response
	 */
	private HttpResponse response;
	
	/**
	 * 返回值
	 */
	private Object result;
	

	public SdkRequest getRequest() {
		return request;
	}

	public void setRequest(SdkRequest request) {
		this.request = request;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public HttpResponse getResponse() {
		return response;
	}

	public void setResponse(HttpResponse response) {
		this.response = response;
	}

}
