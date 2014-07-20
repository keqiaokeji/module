package com.keqiaokeji.module.httpsdk.test;


import com.keqiaokeji.module.httpsdk.utils.JsonUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 服务端返回信息类型
 * @param <T>
 */
public class JsonResult<T> {

    private String statusCode;

    private String statusMsg;

    private String content;

    private List<T> dataRows = new ArrayList<T>();

    private String token;

    private long timestamp = new Date().getTime();

    public JsonResult() {
    }

    public JsonResult(String statusCode, String statusMsg) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
//        this.token = AppCommonContexts.getToken();
    }

    public JsonResult(String statusCode, String statusMsg, String content) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
        this.content = content;
//        this.token = AppCommonContexts.getToken();
    }

    public JsonResult(String statusCode, String statusMsg, List<T> obj) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
        this.dataRows = obj;
//        this.token = AppCommonContexts.getToken();
    }

    public JsonResult(String statusCode, String statusMsg, Object obj) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
        this.dataRows.add((T) obj);
//        this.token = AppCommonContexts.getToken();
    }

    /**
     * 将结果集封装到集合中
     * @param obj
     */
    public void addResult(T obj) {
        dataRows.add(obj);
    }

    /**
     * 取出集合中的第一个结果集
     * @return
     */
    public T getResultFirst() {
        if (dataRows.size() > 0) {
            return dataRows.get(0);
        } else {
            return null;
        }
    }


    public List<T> getDataRows() {
        return dataRows;
    }

    public void setDataRows(List<T> dataRows) {
        this.dataRows = dataRows;
    }

    public void setContentToJsonString(Object object) {
        this.content = JsonUtil.toJson(object);
    }

    public <T> T getContentObject(Class<T> clazz) {
        return JsonUtil.toObject(this.content, clazz);
    }


    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
