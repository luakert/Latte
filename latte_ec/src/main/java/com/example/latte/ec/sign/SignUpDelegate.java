package com.example.latte.ec.sign;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.latte.delegate.LatteDelegate;
import com.example.latte.ec.R;
import com.example.latte.ec.R2;
import com.example.latte.net.RestClient;
import com.example.latte.net.callback.ISuccess;
import com.example.latte.util.log.LatteLogger;

import butterknife.BindView;
import butterknife.OnClick;

public class SignUpDelegate extends LatteDelegate {
    @BindView(R2.id.edit_sign_up_name)
    TextInputEditText mName = null;
    @BindView(R2.id.edit_sign_up_email_address)
    TextInputEditText mEmail = null;
    @BindView(R2.id.edit_sign_up_phonenumber)
    TextInputEditText mPhoneNumber = null;
    @BindView(R2.id.edit_sign_up_password)
    TextInputEditText mPassWord = null;
    @BindView(R2.id.edit_sign_up_reset_pwd)
    TextInputEditText mResetpwd = null;

    @OnClick(R2.id.edit_sign_up)
    void OnClickSignUp() {
        if (checkForm()) {
            RestClient.builder()
                    .url("http://192.168.56.1:8080/RestDataServer/api/user_profile.php")
                    .params("name", mName.getText().toString())
                    .params("email", mEmail.getText().toString())
                    .params("phone", mPhoneNumber.getText().toString())
                    .params("password", mPassWord.getText().toString())
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            LatteLogger.json("USER_PROFILE", response);
                            SignHandler.onSignUp(response);
                        }
                    })
                    .build()
                    .post();
        }
    }

    @OnClick(R2.id.tv_link_sign_in)
    void onClickLink() {
        start(new SignInDelegate());
    }

    private boolean checkForm() {
        String name = mName.getText().toString();
        String email = mEmail.getText().toString();
        String phoneNumber = mPhoneNumber.getText().toString();
        String passWord = mPassWord.getText().toString();
        String resetpwd = mResetpwd.getText().toString();

        boolean isPass = true;

        if (name.isEmpty()) {
            mName.setError("请输入姓名");
        } else {
            mName.setError(null);
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("邮件格式错误");
            isPass = false;
        } else {
            mEmail.setError(null);
        }

        if (phoneNumber.isEmpty() || !Patterns.PHONE.matcher(phoneNumber).matches()) {
            mPhoneNumber.setError("号码格式错误");
            isPass = false;
        } else {
            mPhoneNumber.setError(null);
        }

        if (passWord.isEmpty() || passWord.length() < 6) {
            mPassWord.setError("至少6位密码");
            isPass = false;
        } else {
            mPassWord.setError(null);
        }

        if (resetpwd.isEmpty() || resetpwd.length() < 6 || !(resetpwd.equals(passWord))) {
            mResetpwd.setError("新旧密码不相同");
            isPass = false;
        } else {
            mResetpwd.setError(null);
        }

        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_up;
    }

    @Override
    public void onBindView(Bundle saveIns, View rootView) {

    }
}
