package com.example.latte.ec.main.index;

import android.os.Bundle;
import android.view.View;

import com.example.latte.delegate.bottom.BottomItemDelegate;
import com.example.latte.ec.R;

public class IndexDelegate extends BottomItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(Bundle saveIns, View rootView) {

    }
}
