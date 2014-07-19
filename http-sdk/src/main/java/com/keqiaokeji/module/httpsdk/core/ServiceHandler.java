package com.keqiaokeji.module.httpsdk.core;

import com.keqiaokeji.module.httpsdk.attributes.SdkInterface;
import com.keqiaokeji.module.httpsdk.attributes.SdkMethod;
import com.keqiaokeji.module.httpsdk.attributes.SdkParam;
import com.keqiaokeji.module.httpsdk.domain.SdkRequest;
import com.keqiaokeji.module.httpsdk.domain.SdkResponse;
import com.keqiaokeji.module.httpsdk.utils.*;
import org.apache.http.HttpResponse;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;


/**
 * 接口的代理器
 *
 * @author keqikeji
 */
public class ServiceHandler implements InvocationHandler {

    /**
     * 接口的代理器
     */
    protected ServiceHandler() {
        super();
    }


    /**
     * 重写代理的业务处理
     *
     * @param proxy  被代理的类
     * @param method 当前调用的函数
     * @param args   函数中的参数
     * @return 接口的返回值
     * @throws Exception
     */
    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Exception {
        Object result;
        String requestKey = proxy.getClass().getName() + method.getName();
        SdkRequest sdkRequest = Cache.sdkRequests.get(requestKey);
        if (sdkRequest == null) {// 如果缓存里面不存在则需要创建并保存到缓存中
            sdkRequest = new SdkRequest();
            parseInterface(proxy, sdkRequest);
            parseMethod(proxy, method, sdkRequest);
            Cache.sdkRequests.put(requestKey, sdkRequest);
        }
        parseParameter(proxy, method, args, sdkRequest);
        addCommonAttribute(sdkRequest);
        result = getResultByServer(sdkRequest);
        return result;
    }

    /**
     * 如果接口类上有SdkInterface注解则解析其内容
     *
     * @param proxy      被代理的类
     * @param sdkRequest 请求之前信息存储对象
     */
    private void parseInterface(Object proxy, SdkRequest sdkRequest) {
        Class<? extends Object> proxyClass = proxy.getClass();
        Class<?>[] interfaces = proxyClass.getInterfaces();
        for (Class<?> soaInterface : interfaces) {// 接口可能有多个注解，找到SdkInterface类型的注释
            if (soaInterface.isAnnotationPresent(SdkInterface.class)) {
                SdkInterface service = soaInterface.getAnnotation(SdkInterface.class);
                sdkRequest.setInterfaceNamespace(service.value());
                break;
            }
        }
    }

    /**
     * 解析函数注解SdkMethod的内容
     *
     * @param proxy      被代理的类
     * @param method     请求的函数
     * @param sdkRequest 请求之前信息存储对象
     */
    private void parseMethod(Object proxy, Method method, SdkRequest sdkRequest) {
        if (method.isAnnotationPresent(SdkMethod.class)) {
            SdkMethod SdkMethod = method.getAnnotation(SdkMethod.class);
            if (StringSdk.isNotEmpty(SdkMethod.value())) {
                sdkRequest.setMethodNamespace(SdkMethod.value());
            }
            sdkRequest.setReturnType(method.getGenericReturnType());
        }

        if (StringSdk.isEmpty(sdkRequest.getMethodNamespace())) {
            throw new RuntimeException("接口类[" + proxy.getClass().getName() + "]中的函数[" + method.getName() + "]必须添加["
                    + SdkMethod.class.getName() + "]注解，并且注解的属性值不能为空。");
        }
    }

    /**
     * 解析函数的参数及其值
     *
     * @param proxy      被代理的类
     * @param method     当前调用的函数
     * @param args       函数中的参数
     * @param sdkRequest 请求之前信息存储对象
     */
    private void parseParameter(Object proxy, Method method, Object[] args, SdkRequest sdkRequest) {
        sdkRequest.getParams().clear();// 先清空缓存的参数，因为request对象将会重复使用
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < args.length; i++) {
            Annotation[] argAnnotations = parameterAnnotations[i];
            for (Annotation param : argAnnotations) {// 找到用SdkParam标注的参数
                if (param instanceof SdkParam) {
                    SdkParam sdkParam = (SdkParam) param;
                    String paramName = sdkParam.value();
                    sdkRequest.getParams().put(paramName, StringSdk.objectToString(args[i]));
                    break;
                }
            }
        }
    }

    /**
     * 将公共属性添加到请求信息中
     *
     * @param sdkRequest 请求之前信息存储对象
     */
    private void addCommonAttribute(SdkRequest sdkRequest) {
        for (Entry<String, Object> attribute : SdkContext.getCommonAttributes().entrySet()) {
            sdkRequest.getParams().put(attribute.getKey(), StringSdk.objectToString(attribute.getValue()));
        }
    }

    /**
     * 通过请求服务获得结果
     *
     * @param sdkRequest 请求之前信息存储对象
     * @return 接口返回值类型的对象或null
     * @throws Exception
     */
    public Object getResultByServer(final SdkRequest sdkRequest) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 用异步任务的原因主要是因为该SDK可同时在安卓项目中使用，但是安卓4.0之后强制要求所有的网路请求必须放在异步任务中，否则代码运行会报错
        FutureTask<Object> futureTask = new FutureTask<Object>(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return HttpServer.doHttpRequest(sdkRequest);
            }
        });
        executorService.submit(futureTask);
        HttpResponse httpResponse = (HttpResponse) futureTask.get();
        SdkResponse sdkResponse = formatResult(sdkRequest, httpResponse);
        return sdkResponse == null ? null : sdkResponse.getResult();
    }

    /**
     * 转换请求结果
     *
     * @param sdkRequest   请求之前信息存储对象
     * @param httpResponse http请求之后的响应对象
     * @return sdk请求之后的响应对象
     * @throws Exception
     */
    private SdkResponse formatResult(SdkRequest sdkRequest, HttpResponse httpResponse) throws Exception {
        if (httpResponse == null) {
            return null;
        }
        SdkResponse sdkResponse = new SdkResponse();
        sdkResponse.setResponse(httpResponse);
        byte[] bytes = HttpServer.parseResponse(sdkResponse);
        if (bytes == null || bytes.length == 0) {
            return null;
        } else {
            String jsonResult = new String(bytes, SdkContext.encode);
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("请求网络出现异常，状态码status_code:" + httpResponse.getStatusLine().getStatusCode());
            }
            if (SdkContext.showLog) {
                LoggerUtils.debug("请求URL[" + sdkRequest.getRequestUrl() + "]返回的结果如下：\n" + jsonResult);
            }
            if (sdkRequest.getReturnType() != void.class) {// 函数的返回值类型是void的函数不处理
//                sdkResponse.setResult(JsonUtilSdk.parseObject(jsonResult, sdkRequest.getReturnType()));
                sdkResponse.setResult(JsonUtil.toObject(jsonResult, sdkRequest.getReturnType()));
            }
        }
        return sdkResponse;
    }

}