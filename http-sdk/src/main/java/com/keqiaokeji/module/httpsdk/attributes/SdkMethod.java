package com.keqiaokeji.module.httpsdk.attributes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口函数的注解，该注解在函数上必须有
 * 
 * @author keqikeji
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SdkMethod {

	/**
	 * 函数的命名空间，如：/login.do
	 * 
	 * @return
	 */
	String value() default "";

}
