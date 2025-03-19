package com.example.latte.app;

import android.app.Activity;
import android.os.Handler;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

public class Configurator {
    public static final HashMap<Object, Object> LATTE_CONFIGS = new HashMap<>();
    public static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    private Configurator() {
        LATTE_CONFIGS.put(ConfigType.CONFIG_READEY, false);
    }

    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    private static final Handler HANDLER = new Handler();

    final HashMap<Object, Object> getLatteConfigs() {
        return LATTE_CONFIGS;
    }

    private static class Holder {
        public static final Configurator INSTANCE = new Configurator();
    }

    public final void configure() {
        initIcons();
        LATTE_CONFIGS.put(ConfigType.CONFIG_READEY, true);
        LATTE_CONFIGS.put(ConfigType.HANDLER, HANDLER);
    }

    public final Configurator withAppHost(String host) {
        LATTE_CONFIGS.put(ConfigType.API_HOST, host);
        return this;
    }

    private void initIcons() {
        if (ICONS.size() > 0) {
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }

    public final Configurator withIcon(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        return this;
    }

    public final Configurator withInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        LATTE_CONFIGS.put(ConfigType.INTERCEPTORS, INTERCEPTORS);
        return this;
    }

    public final Configurator withWeChatAppID(String appID) {
        LATTE_CONFIGS.put(ConfigType.WE_CHAT_APP_ID, appID);
        return this;
    }

    public final Configurator withWeChatAppSecret(String appSecret) {
        LATTE_CONFIGS.put(ConfigType.WE_CHAT_APP_SECRET, appSecret);
        return this;
    }

    public final Configurator withWeChatActivity(Activity activity) {
        LATTE_CONFIGS.put(ConfigType.ACTIVITY, activity);
        return this;
    }

    public final Configurator withInterceptors(ArrayList<Interceptor> interceptor) {
        INTERCEPTORS.addAll(interceptor);
        LATTE_CONFIGS.put(ConfigType.INTERCEPTORS, INTERCEPTORS);
        return this;
    }

    private void checkConfiguration() {
        final boolean isConfiging = (boolean) LATTE_CONFIGS.get(ConfigType.CONFIG_READEY);
        if (!isConfiging) {
            throw new RuntimeException("Config is not ready");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key) {
        checkConfiguration();
        final Object value = LATTE_CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + " IS NULL");
        }
        return (T) LATTE_CONFIGS.get(key);
    }


}
