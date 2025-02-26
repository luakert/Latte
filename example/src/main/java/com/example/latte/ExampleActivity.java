package com.example.latte;

import com.example.latte.activity.ProxyActivity;
import com.example.latte.delegate.LatteDelegate;

public class ExampleActivity extends ProxyActivity {

    @Override
    public LatteDelegate setRootDelegate() {
        return new ExampleDelegate();
    }
}