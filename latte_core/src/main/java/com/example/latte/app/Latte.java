package com.example.latte.app;

import android.content.Context;

import java.util.HashMap;

public final class Latte {
    public static Configurator init(Context context) {
        getConfigurator().put(ConfigType.APPLICATION_CONTEXT.name(), context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static Context getApplication() {
        return ((Context) getConfigurator().get(ConfigType.APPLICATION_CONTEXT.name()));
    }

    public static HashMap<String, Object> getConfigurator() {
        return Configurator.getInstance().getLatteConfigs();
    }
}
