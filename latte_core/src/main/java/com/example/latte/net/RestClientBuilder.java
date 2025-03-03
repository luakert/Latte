package com.example.latte.net;

import android.content.Context;

import com.example.latte.net.callback.IError;
import com.example.latte.net.callback.IFailure;
import com.example.latte.net.callback.IRequest;
import com.example.latte.net.callback.ISuccess;
import com.example.latte.ui.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RestClientBuilder {
    private String mUrl;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParms();
    private IRequest mIrequest;
    private ISuccess mIsuccess;
    private IFailure mIfailure;
    private IError mIerror;
    private String mDownloadDir;
    private String mExtension;
    private String mName;
    private RequestBody mBoy;
    private LoaderStyle LOAD_STYLE = null;
    private Context CONTEXT = null;
    private File mFile;

    RestClientBuilder() {

    }

    public final RestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder param(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RestClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final RestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final RestClientBuilder file(String file) {
        this.mFile = new File(file);
        return this;
    }

    public final RestClientBuilder name(String name) {
        this.mName = name;
        return this;
    }

    public final RestClientBuilder dir(String dir) {
        this.mDownloadDir = dir;
        return this;
    }

    public final RestClientBuilder extension(String extension) {
        this.mExtension = extension;
        return this;
    }



    public final RestClientBuilder raw(String raw) {
        this.mBoy = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),raw);
        return this;
    }

    public final RestClientBuilder onRequset(IRequest request) {
        this.mIrequest = request;
        return this;
    }

    public final RestClientBuilder success(ISuccess success) {
        this.mIsuccess = success;
        return this;
    }

    public final RestClientBuilder fail(IFailure failure) {
        this.mIfailure = failure;
        return this;
    }

    public final RestClientBuilder error(IError error) {
        this.mIerror = error;
        return this;
    }

    public final RestClientBuilder loader(Context context, LoaderStyle style) {
        this.CONTEXT = context;
        this.LOAD_STYLE = style;
        return this;
    }

    public final RestClientBuilder loader(Context context) {
        this.CONTEXT = context;
        this.LOAD_STYLE = LoaderStyle.BallClipRotatePulseIndicator;
        return this;
    }

    public final RestClient build() {
        return new RestClient(mUrl, PARAMS, mIrequest, mIsuccess, mIfailure, mIerror, mDownloadDir, mExtension,
                mName ,mBoy,  CONTEXT, LOAD_STYLE, mFile);
    }


}
