package com.example.latte.wechat;

import android.app.Activity;

import com.example.latte.app.ConfigType;
import com.example.latte.app.Latte;
import com.example.latte.wechat.callbacks.IWeChatSignInCallback;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class LatteWeChat {
    public static final String APP_ID = Latte.getConfiguration(ConfigType.WE_CHAT_APP_ID);
    public static final String APP_SECRET = Latte.getConfiguration(ConfigType.WE_CHAT_APP_SECRET);

    private IWeChatSignInCallback mIWeChatSignInSuccess = null;

    private final IWXAPI WXAPI;

    private LatteWeChat() {
        final Activity activity = Latte.getConfiguration(ConfigType.ACTIVITY);
        WXAPI = WXAPIFactory.createWXAPI(activity, APP_ID, true);
        WXAPI.registerApp(APP_ID);
    }

    public final IWXAPI getWXAPI() {
        return WXAPI;
    }

    public final void signIn() {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "random_state";
        WXAPI.sendReq(req);
    }

    private static final class Holder {
        private static final LatteWeChat latteWeChat = new LatteWeChat();
    }

    public static LatteWeChat getInstance() {
        return Holder.latteWeChat;
    }

    public LatteWeChat onSignInSuccess(IWeChatSignInCallback weChatSignInCallback) {
        this.mIWeChatSignInSuccess = weChatSignInCallback;
        return this;
    }
    public IWeChatSignInCallback getSignInSuccess() {
        return mIWeChatSignInSuccess;
    }

}
