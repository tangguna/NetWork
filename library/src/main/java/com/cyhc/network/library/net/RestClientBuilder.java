package com.cyhc.network.library.net;

import android.content.Context;
import android.util.Log;


import com.cyhc.network.library.callback.ErrorListener;
import com.cyhc.network.library.callback.FailureListener;
import com.cyhc.network.library.callback.RequestListener;
import com.cyhc.network.library.callback.SuccessListener;
import com.cyhc.network.library.ui.LoaderStyle;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

import io.reactivex.annotations.Nullable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RestClientBuilder {
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private static final Map<String,String> HEADERS = RestCreator.getHeaders();
    private String mUrl = null;
    private RequestListener mIRequest = null;
    private SuccessListener mISuccess = null;
    private FailureListener mIFailure = null;
    private ErrorListener mIError = null;
    private RequestBody mBody = null;
    private Context mContext = null;
    private LoaderStyle mLoaderStyle = null;
    private File mFile = null;
    private String mDownloadDir = null;
    private String mExtension = null;
    private String mName = null;

    RestClientBuilder() {
    }

    /**
     * 基地址
     * @param url
     * @return
     */
    public final RestClientBuilder  url( String url) {
        this.mUrl = url;
        return this;
    }

    /**
     * 地址
     * @param url
     * @param map
     *          map为动态变化地址（内部为请求添加头）
     * @return
     */
    public final RestClientBuilder url(String url,@Nullable Map<String,String>map){
        this.mUrl = url;
        HEADERS.putAll(map);
        return this;
    }

    public final RestClientBuilder params(Map<String, Object> params) {
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
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RestClientBuilder success(SuccessListener iSuccess) {
        this.mISuccess = iSuccess;
        return this;
    }

    public final RestClientBuilder onRequest(RequestListener iRequest) {
        this.mIRequest = iRequest;
        return this;
    }

    public final RestClientBuilder failure(FailureListener iFailure) {
        this.mIFailure = iFailure;
        return this;
    }

    public final RestClientBuilder error(ErrorListener iError) {
        this.mIError = iError;
        return this;
    }

    private Map<String, Object> checkParams() {
        if (PARAMS == null) {
            return new WeakHashMap<>();
        }
        return PARAMS;
    }

    public final RestClientBuilder loader(Context context, LoaderStyle style) {
        this.mContext = context;
        this.mLoaderStyle = style;
        return this;
    }

    public final RestClientBuilder loader(Context context) {
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallClipRotatePulseIndicator;
        return this;
    }

    public final RequestClient build() {
        return new RequestClient(mUrl, PARAMS,
                mDownloadDir, mExtension, mName,
                mIRequest, mISuccess, mIFailure,
                mIError, mBody, mFile, mContext,
                mLoaderStyle,HEADERS);
    }
}
