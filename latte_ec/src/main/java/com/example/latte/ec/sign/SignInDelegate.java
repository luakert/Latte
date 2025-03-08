package com.example.latte.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.View;

import com.example.latte.app.ISignListener;
import com.example.latte.delegate.LatteDelegate;
import com.example.latte.ec.R;
import com.example.latte.ec.R2;
import com.example.latte.net.RestClient;
import com.example.latte.net.callback.ISuccess;
import com.example.latte.util.log.LatteLogger;

import butterknife.BindView;
import butterknife.OnClick;

public class SignInDelegate extends LatteDelegate {
    @BindView(R2.id.edit_sign_in_email_address)
    TextInputEditText mName = null;
    @BindView(R2.id.edit_sign_in_password)
    TextInputEditText mPassWord = null;
    private ISignListener mIsignListener;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mIsignListener = ((ISignListener) activity);
        }
    }

    @OnClick(R2.id.edit_sign_in)
    void onClickSignIn() {
        if (checkForm()) {
            RestClient.builder()
                    .url("http://192.168.56.1:8080/RestDataServer/api/user_profile.php")
                    .params("name", mName.getText().toString())
                    .params("password", mPassWord.getText().toString())
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            LatteLogger.json("USER_PROFILE", response);
                            SignHandler.onSignIn(response, mIsignListener);
                        }
                    })
                    .build()
                    .post();
        }
    }

    @OnClick(R2.id.icon_sign_in_wechat)
    void onClickWeChat() {

    }

    @OnClick(R2.id.tv_link_sign_up)
    void onClickLink() {
        start(new SignUpDelegate());
    }

    @Override
    public Object setLayout() {

        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(Bundle saveIns, View rootView) {

    }

    private boolean checkForm() {
        String name = mName.getText().toString();
        String passWord = mPassWord.getText().toString();

        boolean isPass = true;
        if (name.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(name).matches()) {
            mName.setError("邮件格式错误");
            isPass = false;
        } else {
            mName.setError(null);
        }

        if (passWord.isEmpty() || passWord.length() < 6) {
            mPassWord.setError("至少6位密码");
            isPass = false;
        } else {
            mPassWord.setError(null);
        }
        return isPass;
    }

}
