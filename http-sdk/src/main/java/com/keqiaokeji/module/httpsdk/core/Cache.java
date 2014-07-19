package com.keqiaokeji.module.httpsdk.core;

import com.keqiaokeji.module.httpsdk.domain.SdkRequest;

import java.util.HashMap;
import java.util.Map;


/**
 * 缓存
 * @author keqikeji
 *
 */
public class Cache {

	/**
	 * 将解析过的request缓存起来方便下次使用
	 */
	public static Map<String, SdkRequest> sdkRequests = new HashMap<String, SdkRequest>();
	
	
	
}
