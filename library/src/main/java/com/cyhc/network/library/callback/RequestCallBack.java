package com.cyhc.network.library.callback;

import android.os.Handler;

import com.cyhc.network.library.ui.LatteLoader;
import com.cyhc.network.library.ui.LoaderStyle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RequestCallBack  implements Callback<String> {

    private final SuccessListener SUCCESS;
    private final FailureListener FAILURE;
    private final ErrorListener ERROR;
    private final RequestListener REQUEST;
    private final LoaderStyle LOADER_STYLE;
    private static final Handler  HANDLER = new Handler();

    public RequestCallBack(SuccessListener SUCCESS, FailureListener FAILURE, ErrorListener ERROR, RequestListener REQUEST, LoaderStyle LOADER_STYLE) {
        this.SUCCESS = SUCCESS;
        this.FAILURE = FAILURE;
        this.ERROR = ERROR;
        this.REQUEST = REQUEST;
        this.LOADER_STYLE = LOADER_STYLE;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()){
            if (call.isExecuted()){
                if (SUCCESS != null){
                    SUCCESS.success(response.body());
                }
            }
        }else {
            if (ERROR != null){
                ERROR.error(response.code(),response.message());
            }
        }
        stopLoading();
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {

        if (FAILURE != null){
            FAILURE.failure();
        }if (REQUEST != null){
            REQUEST.onRequestEnd();
        }
        stopLoading();
    }

    private void  stopLoading(){
        if (LOADER_STYLE != null){
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LatteLoader.stopLoading();
                }
            },1000);
        }
    }
}
