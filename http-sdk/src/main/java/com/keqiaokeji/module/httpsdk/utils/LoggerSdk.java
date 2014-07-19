package com.keqiaokeji.module.httpsdk.utils;

/**
 * Dborm日志记录处理接口
 *
 * @author KEQIAO KEJI
 * @time 2013年10月29日 @上午10:50:37
 */
public interface LoggerSdk {


    /**
     * 调试信息
     *
     * @param msg 信息
     * @author KEQIAO KEJI
     */
    public void debug(String msg);


    /**
     * 异常
     *
     * @param msg 异常信息
     * @param e      异常对象
     * @author KEQIAO KEJI
     */
    public void error(String msg, Throwable e);

}
