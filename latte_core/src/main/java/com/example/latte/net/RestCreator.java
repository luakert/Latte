package com.example.latte.net;

import com.example.latte.app.ConfigType;
import com.example.latte.app.Latte;

import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RestCreator {

    private static final class ParamHolder {
         public static final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    }

    public static WeakHashMap<String, Object> getParms() {
        return ParamHolder.PARAMS;
    }

    public static RestSerivce getRestService() {
        return RestServiceHolder.REST_SERIVCE;
    }

    private static final class RetrofitHolder {
        private static final String BASE_URL = (String) Latte.getConfigurator()
                                                            .get(ConfigType.API_HOST.name());
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();


    }

    private static final class OKHttpHolder {
        private static final int TIME_OUT =60;
        private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    private static final class RestServiceHolder {
        private static final RestSerivce REST_SERIVCE = RetrofitHolder.RETROFIT_CLIENT.create(RestSerivce.class);
    }

}
