package com.keqiaokeji.module.httpsdk.utils;


/**
 * 字符串操作的公共类
 * 
 * @author keqikeji
 * 
 */
public class StringSdk {

	/**
	 * 判断字符串是否为空白，至少包含一个可见字符(null、空字符串、纯空格均为空）
	 * @param content
	 * @return
	 */
	public static boolean isBlank(String content) {
		boolean result = false;
		if (content == null || content.trim().length() == 0){
			result = true;
		}
		return result;
	}
	
	/**
	 * 判断字符串是否不为空白
	 * @param content
	 * @return
	 */
	public static boolean isNotBlank(String content) {
		return !isBlank(content);
	}
	
	/**
	 * 判断字符串是否为空（null、空字符串均为空）
	 * @param content
	 * @return
	 */
	public static boolean isEmpty(String content) {
		return content == null || content.trim().equals(SdkConstants.EMPTY);
	}

	/**
	 * 判断字符串是否不为空
	 * @param content
	 * @return
	 */
	public static boolean isNotEmpty(String content) {
		return !isEmpty(content);
	}

	/**
	 * 对象转换为字符串，如果不是9大基本类型，则通过JOSN转换
	 * 
	 * @param obj 要转换的对象
	 * @return
	 */
	public static String objectToString(Object obj) {
		if(obj == null){
			return SdkConstants.EMPTY;
		}
		
		if (obj instanceof Integer || obj instanceof Long || obj instanceof Short || obj instanceof Character || obj instanceof String
				|| obj instanceof Boolean || obj instanceof Byte || obj instanceof Float || obj instanceof Double) {
			return obj.toString();
		}
		return JsonUtilSdk.toJSONString(obj);
	}
	
	/**
	 * 去掉最后一个标记
	 * 
	 * @param content
	 *            内容
	 * @param sign
	 *            标记
	 * @return
	 * @author HAIKANG SONG
	 * @time 2013-5-2下午4:55:06
	 */
	public static String cutLastSign(String content, String sign) {
		if (isEmpty(content)) {
			return "";
		}
		if (content.endsWith(sign)) {
			content = content.substring(0, content.lastIndexOf(sign));
		}
		return content;
	}

}
