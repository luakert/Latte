package com.example.latte;

import android.os.Bundle;
import android.view.View;

import com.example.latte.delegate.LatteDelegate;

public class ExampleDelegate extends LatteDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(Bundle saveIns, View rootView) {

    }
}
