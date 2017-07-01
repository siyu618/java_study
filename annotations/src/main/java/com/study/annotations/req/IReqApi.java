package com.study.annotations.req;

/**
 * Created by tianyuzhi on 17/5/8.
 */
public interface IReqApi {

    @ReqType(reqType = ReqType.ReqTypeEnum.POST)
    @ReqUrl(reqUrl = "www.xxx.com/openApi/login")
    String login(@ReqParam("userId") String userId, @ReqParam("pwd") String pwd);
}
