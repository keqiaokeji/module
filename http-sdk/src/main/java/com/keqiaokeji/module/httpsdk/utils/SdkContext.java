package com.keqiaokeji.module.httpsdk.utils;

import com.tbc.soa.json.type.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Sdk中的环境变量
 *
 * @author keqikeji
 */
public class SdkContext {

    /**
     * 服务器的URL（如：http://172.16.204.228:8011/service）
     */
    public static String serverBaseUrl;

    /**
     * 存放每次请求都需要添加的公共属性
     */
    private static Map<String, Object> commonAttributes = new HashMap<String, Object>();

    private static String commonAttributesJson;

    /**
     * 请求服务器的超时时长（单位为毫秒）
     */
    public static int timeout = 2 * 60 * 1000;

    /**
     * 临时缓冲区的大小
     */
    public static int tempBuffSize = 1024 * 4;

    /**
     * 设置请求结果的编码类型
     */
    public static String encode = SdkConstants.HTTP_UTF8;

    /**
     * 是否显示请求日志，开发环境的时候建议显示，上线之后时建议不显示
     */
    public static boolean showLog = true;

    public static LoggerSdk log;

    /**
     * 设置公共属性
     *
     * @param key
     * @param obj
     */
    public static void putCommonAttributes(String key, Object obj) {
        commonAttributes.put(key, obj);
//        commonAttributesJson = JsonUtilSdk.toJSONString(commonAttributes);
        commonAttributesJson = JsonUtil.toJson(commonAttributes);
    }

    /**
     * 获取公共属性
     *
     * @return
     */
    public static Map<String, Object> getCommonAttributes() {
        if (commonAttributes.size() == 0) {
//			String attributes = SharedPreferenceUtils.getMemory(SdkConstants.SDK_COMMON_ATTRIBUES, "", String.class);
            if (StringSdk.isNotBlank(commonAttributesJson)) {
                Type type = new TypeToken<HashMap<String, Object>>() {
                }.getType();
                Map<String, Object> attributes = JsonUtil.toObject(commonAttributesJson, type);
                if (attributes != null) {
                    commonAttributes.putAll(attributes);
                }
            }
        }
        return commonAttributes;
    }

}
