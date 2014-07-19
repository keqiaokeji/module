package com.keqiaokeji.module.httpsdk.attributes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口函数中参数的注解，该注解必须有
 * 
 * @author keqikeji
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface SdkParam {

	/**
	 * 参数键的名称
	 */
	String value();

}
