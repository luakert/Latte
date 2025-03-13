package com.example.latte.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.latte.net.RestClient;
import com.example.latte.net.callback.ISuccess;
import com.example.latte.util.log.LatteLogger;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

public abstract class BaseWXEntryActivity extends BaseWXActivity {
    protected abstract void onSignInSuccess(String userInfo);

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        final String code = ((SendAuth.Resp) baseResp).code;
        final StringBuilder authurl = new StringBuilder();
        authurl.append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=")
                .append(LatteWeChat.APP_ID)
                .append("&secret=")
                .append(LatteWeChat.APP_SECRET)
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");
        LatteLogger.d("authurl", authurl.toString());
    }

    private void getAuth(String authUrl) {
        RestClient.builder()
                .url(authUrl)
                .success(response -> {
                    JSONObject jsonObject = JSON.parseObject(response);
                    String accessToken = jsonObject.getString("access_token");
                    String openId = jsonObject.getString("openid");
                    StringBuilder userInfoUrl = new StringBuilder();
                    userInfoUrl.append("https://api.weixin.qq.com/sns/userinfo?access_token=")
                            .append(accessToken)
                            .append("&openid=")
                            .append(openId)
                            .append("&lang=")
                            .append("zh_CN");
                    LatteLogger.d("usrinfourl", userInfoUrl.toString());
                }).build().get();
    }

    private void getUserInfo(String userInfoUrl) {
        RestClient.builder()
                .url(userInfoUrl)
                .success(response -> {
                    onSignInSuccess(response);
                })
                .fail(() -> {

                })
                .error((code, msg) ->
                {

                })
                .build().get();
    }
}
