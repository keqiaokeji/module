package com.keqiaokeji.module.httpsdk.test;

import com.keqiaokeji.module.httpsdk.core.ServiceFactory;
import com.keqiaokeji.module.httpsdk.utils.SdkContext;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HttpSdkTest {

    public HttpSdkTest() {
    }

    @Test
    public void init() {
        try {
            initContext();
            login();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initContext() {
        SdkContext.showLog = true;
        SdkContext.serverBaseUrl = "http://192.168.1.112:8082/TeZiZai-svc";
        SdkContext.log = new LoggerTools();
    }

    private void login() {
        try {
            UserService userService = ServiceFactory.getService(UserService.class);
            JsonResult<UserInfo> result = userService.login("keqiaokeji", "songhaikang", "111");
            UserInfo userInfo = result.getResultFirst();
            System.out.printf(userInfo.getUserId());
            assertEquals(result.getResultFirst() != null, true);


        } catch (Exception e) {
            e.printStackTrace();
            assertEquals(true, false);
        }


    }


}
