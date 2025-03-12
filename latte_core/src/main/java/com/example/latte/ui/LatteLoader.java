package com.example.latte.ui;

import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatDialog;

import com.example.latte.core.R;
import com.example.latte.util.Dimenutil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Dictionary;

public class LatteLoader {
    private static final int LOADER_SIZE_SCALE = 8;
    private static final int LOADER_OFFSET = 10;
    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();
    public static final String DEFAULT_LOADER = LoaderStyle.BallClipRotatePulseIndicator.name();

    public static void showLoading(Context context, Enum<LoaderStyle> styleEnum) {
        showLoading(context, styleEnum.name());
    }

    public static void showLoading(Context context, String type) {
        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);
        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type, context);
        dialog.setContentView(avLoadingIndicatorView);

        int devicewidth = Dimenutil.getScreenWidth();
        int devicehigh = Dimenutil.getScreenHigh();

        Window window = dialog.getWindow();

        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = devicewidth / LOADER_SIZE_SCALE;
            lp.height = devicehigh / LOADER_SIZE_SCALE;
            lp.height = lp.height + devicehigh / LOADER_OFFSET;
            lp.gravity = Gravity.CENTER;
        }

        LOADERS.add(dialog);
        dialog.show();
    }

    public static void showLoading(Context context) {
        showLoading(context, DEFAULT_LOADER);
    }

    public static void stopLoading() {
        for (AppCompatDialog loader : LOADERS) {
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.cancel();
                }
            }
        }
    }
}
