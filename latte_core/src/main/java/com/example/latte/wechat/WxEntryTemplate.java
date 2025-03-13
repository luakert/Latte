package com.example.latte.wechat;

public class WxEntryTemplate extends BaseWXEntryActivity {
    @Override
    protected void onSignInSuccess(String userInfo) {
        LatteWeChat.getInstance().getSignInSuccess().onSignInSuccess(userInfo);
    }

    @Override
    protected void onResume() {
        super.onResume();
        finish();
        overridePendingTransition(0,0);
    }
}
