package com.example.latte.net.download;

import android.os.AsyncTask;

import com.example.latte.net.RestCreator;
import com.example.latte.net.callback.IError;
import com.example.latte.net.callback.IFailure;
import com.example.latte.net.callback.IRequest;
import com.example.latte.net.callback.ISuccess;

import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Downloadhandler {
    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParms();
    private final IRequest IREQUEST;
    private final ISuccess ISUCCESS;
    private final IFailure IFAILURE;
    private final IError IERROR;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;

    public Downloadhandler(String URL, IRequest IREQUEST, ISuccess ISUCCESS, IFailure IFAILURE,
                           IError IERROR, String DOWNLOAD_DIR, String EXTENSION, String NAME) {
        this.URL = URL;
        this.IREQUEST = IREQUEST;
        this.ISUCCESS = ISUCCESS;
        this.IFAILURE = IFAILURE;
        this.IERROR = IERROR;
        this.DOWNLOAD_DIR = DOWNLOAD_DIR;
        this.EXTENSION = EXTENSION;
        this.NAME = NAME;
    }

    public final void handleDownload() {
        if (IREQUEST != null) {
            IREQUEST.onRequestStart();
        }

        RestCreator.getRestService().download(URL, PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            final SaveFileTask task = new SaveFileTask(IREQUEST, ISUCCESS);
                            final ResponseBody responseBody = response.body();

                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                                    DOWNLOAD_DIR, EXTENSION, responseBody, NAME);

                            //这里一定要注意判断，否则文件下载不全
                            if (task.isCancelled()) {
                                if (IREQUEST != null) {
                                    IREQUEST.onRequestEnd();
                                }
                            }
                        } else {
                            if (IERROR != null) {
                                IERROR.onError(response.code(), response.message());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (IFAILURE != null) {
                            IFAILURE.onFailure();
                        }

                    }
                });

    }
}
