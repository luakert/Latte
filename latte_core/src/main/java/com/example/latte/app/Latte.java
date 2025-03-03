package com.example.latte.app;

import android.content.Context;

import java.util.HashMap;

public final class Latte {
    public static Configurator init(Context context) {
        Configurator.getInstance().getLatteConfigs()
                .put(ConfigType.APPLICATION_CONTEXT, context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static Context getApplication() {
        return ((Context) getConfiguration(ConfigType.APPLICATION_CONTEXT));
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }
    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }
}
