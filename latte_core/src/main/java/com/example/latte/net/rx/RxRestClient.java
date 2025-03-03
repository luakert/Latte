package com.example.latte.net.rx;

import android.content.Context;

import com.example.latte.net.HttpMethod;
import com.example.latte.net.RestCreator;
import com.example.latte.ui.LatteLoader;
import com.example.latte.ui.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class RxRestClient {
    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParms();
    private final RequestBody BODY;
    private File file;
    private LoaderStyle LOAD_STYLE;
    private final Context CONTEXT;

    public RxRestClient(String url, WeakHashMap<String, Object> params,
                        RequestBody boy,
                        Context context,
                        LoaderStyle loaderStyle,
                        File file) {
        this.URL = url;
        PARAMS.putAll(params);
        this.BODY = boy;
        this.CONTEXT = context;
        this.file = file;
        this.LOAD_STYLE = loaderStyle;
    }

    public static RxRestClientBuilder builder() {
        return new RxRestClientBuilder();
    }

    public final Observable<String> get() {
        return request(HttpMethod.GET);
    }

    public final Observable<String> upload() {
        return request(HttpMethod.UPLOAD);
    }

    public final Observable<String> post() {
//        request(HttpMethod.POST);
        if (BODY == null) {
           return request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be unnull");
            }
            return request(HttpMethod.POST_RAW);
        }
    }

    public final Observable<String> put() {
        if (BODY == null) {
            return request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be unnull");
            }
            return request(HttpMethod.PUT_RAW);
        }
    }

    public final Observable<String> delete() {
        return request(HttpMethod.DELETE);
    }

    public final Observable<ResponseBody> download() {
        return RestCreator.getRxRestService().download(URL, PARAMS);
    }

    private Observable<String> request(HttpMethod method) {
        final RxRestService restService = RestCreator.getRxRestService();
        Observable<String> call = null;

        if (LOAD_STYLE != null) {
            LatteLoader.showLoading(CONTEXT, LOAD_STYLE);
        }

        switch (method) {
            case GET:
                call = restService.get(URL, PARAMS);
                break;
            case POST:
                call = restService.post(URL, PARAMS);
                break;
            case PUT:
                call = restService.put(URL, PARAMS);
                break;
            case POST_RAW:
                call = restService.postRaw(URL, BODY);
                break;
            case PUT_RAW:
                call = restService.putRaw(URL, BODY);
                break;
            case DELETE:
                call = restService.delete(URL, PARAMS);
                break;
            case UPLOAD:
                RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), file);
                MultipartBody.Part uploadbody = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                call = restService.upload(URL, uploadbody);
                break;
            default:
                break;
        }

        return call;
    }

}
