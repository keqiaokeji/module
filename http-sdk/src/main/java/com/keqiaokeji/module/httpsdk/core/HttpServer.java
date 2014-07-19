package com.keqiaokeji.module.httpsdk.core;

import com.keqiaokeji.module.httpsdk.domain.SdkRequest;
import com.keqiaokeji.module.httpsdk.domain.SdkResponse;
import com.keqiaokeji.module.httpsdk.utils.LoggerUtils;
import com.keqiaokeji.module.httpsdk.utils.SdkContext;
import com.keqiaokeji.module.httpsdk.utils.StringSdk;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


/**
 * 请求远程服务
 *
 * @author keqikeji
 */
public class HttpServer {

    /**
     * 请求远程服务
     *
     * @param sdkRequest 请求时需要的sdkRequest对象（封装了请求所需的信息）
     * @return 请求响应对象
     * @throws Exception
     */
    public static HttpResponse doHttpRequest(SdkRequest sdkRequest) throws Exception {
        String url = sdkRequest.getRequestUrl();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(getPostParam(sdkRequest.getParams()), SdkContext.encode));
        if (SdkContext.showLog) {
            LoggerUtils.debug("开始请求：\n" + url + "?" + getParams(sdkRequest.getParams()));
        }
        return doHttpRequest(httpPost);
    }

    /**
     * 执行远程请求
     *
     * @param httpUriRequest http请求信息对象
     * @return 请求响应信息
     * @throws Exception
     */
    public static HttpResponse doHttpRequest(final HttpUriRequest httpUriRequest) throws Exception {
        HttpResponse response;
        if (httpUriRequest == null) {
            return null;
        }
        BasicHttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, SdkContext.timeout);// 设置连接的超时时间
        HttpConnectionParams.setSoTimeout(httpParams, SdkContext.timeout);// 设置连接成功之后获取数据的超时时间
        HttpClient httpClient = new DefaultHttpClient(httpParams);
        response = httpClient.execute(httpUriRequest);
        return response;

    }

    /**
     * 将参数转换为POST形式的参数
     *
     * @param params 参数集合
     * @return POST参数字符串
     * @throws java.io.UnsupportedEncodingException
     */
    public static List<NameValuePair> getPostParam(Map<String, String> params) throws UnsupportedEncodingException {
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        for (Entry<String, String> entry : params.entrySet()) {
            paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return paramList;
    }

    /**
     * 将参数转换为GET形式的参数
     *
     * @param params 参数
     * @return get形式的参数
     * @throws UnsupportedEncodingException
     */
    public static String getParams(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder postParam = new StringBuilder();
        Set<Entry<String, String>> entrySet = params.entrySet();
        for (Entry<String, String> entry : entrySet) {
            postParam.append(entry.getKey());
            postParam.append("=");
            String value = entry.getValue();
            if (StringSdk.isNotBlank(value)) {
                value = URLEncoder.encode(entry.getValue(), SdkContext.encode);
            }
            postParam.append(value);
            postParam.append("&");
        }
        return StringSdk.cutLastSign(postParam.toString(), "&");
    }

    /**
     * 解析请求远程服务之后返回的信息
     *
     * @param sdkResponse 请求返回的信息对象
     * @return 请求服务之后的字节数组
     * @throws Exception
     */
    public static byte[] parseResponse(SdkResponse sdkResponse) throws Exception {
        HttpResponse httpResponse = sdkResponse.getResponse();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HttpEntity entity = httpResponse.getEntity();
        if (entity == null) {
            return null;
        }
        InputStream content = entity.getContent();
        byte[] buffer = new byte[SdkContext.tempBuffSize];
        int length;
        while ((length = content.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }
        return outputStream.toByteArray();
    }

}
