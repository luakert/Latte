package com.example.latte.net;

import android.content.Context;

import com.example.latte.net.callback.IError;
import com.example.latte.net.callback.IFailure;
import com.example.latte.net.callback.IRequest;
import com.example.latte.net.callback.ISuccess;
import com.example.latte.net.callback.RequestCallbacks;
import com.example.latte.net.download.Downloadhandler;
import com.example.latte.ui.LatteLoader;
import com.example.latte.ui.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class RestClient {
    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParms();
    private final IRequest IREQUEST;
    private final ISuccess ISUCCESS;
    private final IFailure IFAILURE;
    private final IError IERROR;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final RequestBody BODY;
    private File file;
    private LoaderStyle LOAD_STYLE;
    private final Context CONTEXT;

    public RestClient(String url, WeakHashMap<String, Object> params,
                      IRequest irequest,
                      ISuccess isuccess,
                      IFailure ifailure,
                      IError ierror,
                      String download_dir,
                      String extension,
                      String name,
                      RequestBody boy,
                      Context context,
                      LoaderStyle loaderStyle,
                      File file) {
        this.URL = url;
        PARAMS.putAll(params);
        this.IREQUEST = irequest;
        this.ISUCCESS = isuccess;
        this.IFAILURE = ifailure;
        this.IERROR = ierror;
        this.DOWNLOAD_DIR = download_dir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.BODY = boy;
        this.CONTEXT = context;
        this.file = file;
        this.LOAD_STYLE = loaderStyle;
    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }

    public final void get() {
        request(HttpMethod.GET);
    }

    public final void post() {
//        request(HttpMethod.POST);
        if (BODY == null) {
            request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be unnull");
            }
            request(HttpMethod.POST_RAW);
        }
    }

    public final void put() {
        if (BODY == null) {
            request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be unnull");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }

    public final void download() {
        new Downloadhandler(URL, IREQUEST, ISUCCESS, IFAILURE, IERROR,
                DOWNLOAD_DIR, EXTENSION, NAME).handleDownload();
    }

    private void request(HttpMethod method) {
        final RestSerivce restService = RestCreator.getRestService();
        Call<String> call = null;
        if (IREQUEST != null) {
            IREQUEST.onRequestStart();
        }

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
                call = RestCreator.getRestService().upload(URL, uploadbody);
                break;
            default:
                break;
        }

        if (call != null) {
            call.enqueue(getRequestCallback());

        }
    }

    private Callback<String> getRequestCallback() {
        return new RequestCallbacks(IREQUEST, ISUCCESS, IFAILURE, IERROR, LOAD_STYLE);
    }
}
