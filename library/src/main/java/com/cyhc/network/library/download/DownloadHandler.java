package com.cyhc.network.library.download;

import android.os.AsyncTask;


import com.cyhc.network.library.callback.ErrorListener;
import com.cyhc.network.library.callback.FailureListener;
import com.cyhc.network.library.callback.RequestListener;
import com.cyhc.network.library.callback.SuccessListener;
import com.cyhc.network.library.net.RestCreator;

import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 傅令杰 on 2017/4/2
 */

public final class DownloadHandler {

    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final RequestListener REQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final SuccessListener SUCCESS;
    private final FailureListener FAILURE;
    private final ErrorListener ERROR;

    public DownloadHandler(String url,
                           RequestListener request,
                           String downDir,
                           String extension,
                           String name,
                           SuccessListener success,
                           FailureListener failure,
                           ErrorListener error) {
        this.URL = url;
        this.REQUEST = request;
        this.DOWNLOAD_DIR = downDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
    }


    public void handleDownload() {
        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }

        RestCreator.getApiServece().download(URL, PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            final ResponseBody responseBody = response.body();
                            final SaveFileTask task = new SaveFileTask(REQUEST, SUCCESS);
                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                                    DOWNLOAD_DIR, EXTENSION, responseBody, NAME);

                            //这里一定要注意判断，否则文件下载不全
                            if (task.isCancelled()) {
                                if (REQUEST != null) {
                                    REQUEST.onRequestEnd();
                                }
                            }
                        } else {
                            if (ERROR != null) {
                                ERROR.error(response.code(), response.message());
                            }
                        }
                        RestCreator.getParams().clear();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (FAILURE != null) {
                            FAILURE.failure();
                            RestCreator.getParams().clear();
                        }
                    }
                });

    }
}
