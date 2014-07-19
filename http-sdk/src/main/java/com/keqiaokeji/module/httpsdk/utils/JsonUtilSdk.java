package com.keqiaokeji.module.httpsdk.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import java.lang.reflect.Type;

/**
 * Json转行工具栏
 * Created by keqiaokeji on 14-5-24.
 */
public class JsonUtilSdk {


    public static String toJSONString(Object object) {
       return JSON.toJSONString(object);
    }

    public static <T> T parseObject(String json, Type clazz) {
        return JSON.parseObject(json, clazz, Feature.AutoCloseSource, Feature.IgnoreNotMatch, Feature.IgnoreNotMatch);
    }



}
