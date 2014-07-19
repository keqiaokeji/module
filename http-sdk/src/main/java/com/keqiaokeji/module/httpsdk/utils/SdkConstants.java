package com.keqiaokeji.module.httpsdk.utils;

/**
 * Sdk中的常量
 * 
 * @author keqikeji
 * 
 */
public interface SdkConstants {

	/**
	 * 设置请求编码类型为UTF-8
	 */
	String HTTP_UTF8 = "UTF-8";

	/**
	 * URL中的分隔符
	 */
	String URL_SEPARATOR_SLASH = "/";

	String URL_SEPARATOR_QUESTION = "?";
	String URL_SEPARATOR_POINT = ".";
	String PARAM_EQUALS = "=";
	String PARAM_SEPARATOR = "&";
	String DEF_PARAM_NAME_PREFIX = "param[";
	String DEF_PARAM_NAME_SUBFIX = "]";
	String EMPTY = "";
	String SDK_COMMON_ATTRIBUES = "SDK_COMMON_ATTRIBUES";//SDK的公共参数的键

}
