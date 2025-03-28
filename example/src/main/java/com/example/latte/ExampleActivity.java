package com.example.latte;

import android.os.Bundle;
import android.os.PersistableBundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import android.widget.Toast;

import com.example.latte.activity.ProxyActivity;
import com.example.latte.app.ISignListener;
import com.example.latte.app.Latte;
import com.example.latte.delegate.LatteDelegate;
import com.example.latte.ec.launcher.ILauncherListener;
import com.example.latte.ec.launcher.LauncherDelegate;
import com.example.latte.ec.launcher.LauncherScrollDelegate;
import com.example.latte.ec.launcher.OnLauncherFinishTag;
import com.example.latte.ec.main.EcBottomDelegate;
import com.example.latte.ec.sign.SignInDelegate;
import com.example.latte.ec.sign.SignUpDelegate;

public class ExampleActivity extends ProxyActivity implements ISignListener, ILauncherListener {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        Latte.getConfigurator().withWeChatActivity(this);
    }

    @Override
    public LatteDelegate setRootDelegate() {
        return new EcBottomDelegate();
    }

    @Override
    public void onSignInSuccess() {
        Toast.makeText(this, "登陆成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag) {
            case SIGNED:
                Toast.makeText(this, "已登陆", Toast.LENGTH_LONG).show();
                startWithPop(new EcBottomDelegate());
                break;
            case NOT_SIGNED:
                Toast.makeText(this, "未登陆", Toast.LENGTH_LONG).show();
                startWithPop(new EcBottomDelegate());
                break;
            default:
                break;
        }

    }
}