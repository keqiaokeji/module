package com.keqiaokeji.module.httpsdk.attributes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口类的注解，该注解为函数命名空间前缀注解，如果不需要统一前缀时可以忽略
 * 
 * @author keqikeji
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SdkInterface {

	/**
	 * 应用的命名空间，将作为该接口类所有函数的命名空间前缀，例如：/uc/usercontrol
	 * 
	 * @return
	 */
	String value() default "";

}
