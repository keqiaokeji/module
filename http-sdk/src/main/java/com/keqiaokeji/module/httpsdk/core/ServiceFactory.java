package com.keqiaokeji.module.httpsdk.core;

import java.lang.reflect.Proxy;

/**
 * 接口管理
 * 
 * @author keqikeji
 * 
 */
public class ServiceFactory {

	/**
	 * 获得接口的实例（实际上是将接口注册到代理器里）
	 * 
	 * @param serviceInterface
	 *            接口类
	 * @return 接口实例
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getService(Class<T> serviceInterface) throws Exception {
		if (serviceInterface == null || !serviceInterface.isInterface()) {
			return null;
		}

		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		Class<?>[] interfaces = new Class<?>[1];
		interfaces[0] = serviceInterface;
		Object proxyInstance = Proxy.newProxyInstance(contextClassLoader, interfaces, new ServiceHandler());// 将Service注册到代理
		return (T) proxyInstance;
	}

}
