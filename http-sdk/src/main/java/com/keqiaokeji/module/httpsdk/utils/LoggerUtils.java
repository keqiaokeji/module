package com.keqiaokeji.module.httpsdk.utils;


/**
 * 日志处理类
 *
 * @author KEQIAO KEJI
 * @time 2013-4-17 下午3:07:28
 */
public class LoggerUtils {

    /**
     * 调试信息
     *
     * @param msg 信息
     * @author KEQIAO KEJI
     */
    public static void debug(String msg) {
        if (SdkContext.log != null) {
            SdkContext.log.debug(msg);
        }
    }


    /**
     * 异常
     *
     * @param msg 异常信息
     * @param e   异常对象
     * @author KEQIAO KEJI
     */
    public static void error(String msg, Throwable e) {
        if (SdkContext.log != null) {
            SdkContext.log.error(msg, e);
        }
    }

    /**
     * 异常
     *
     * @param e 异常对象
     * @author KEQIAO KEJI
     */
    public static void error(Throwable e) {
        if (SdkContext.log != null) {
            SdkContext.log.error("",e);
        }
    }


}
