package com.keqiaokeji.module.httpsdk.test;

import com.keqiaokeji.module.httpsdk.attributes.SdkInterface;
import com.keqiaokeji.module.httpsdk.attributes.SdkMethod;
import com.keqiaokeji.module.httpsdk.attributes.SdkParam;

/**
 * Created by shk on 14-7-19.
 */
@SdkInterface
public interface UserService {

    @SdkMethod("/anonymous/uc/loginUserWithAccreditCode.do")
    public JsonResult<UserInfo> login(@SdkParam("corpCode") String corpCode, @SdkParam("username") String username, @SdkParam("password") String password);

}
