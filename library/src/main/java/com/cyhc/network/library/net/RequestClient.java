package com.cyhc.network.library.net;

import android.content.Context;

import com.cyhc.network.library.callback.ErrorListener;
import com.cyhc.network.library.callback.FailureListener;
import com.cyhc.network.library.callback.RequestCallBack;
import com.cyhc.network.library.callback.RequestListener;
import com.cyhc.network.library.callback.SuccessListener;
import com.cyhc.network.library.download.DownloadHandler;
import com.cyhc.network.library.ui.LatteLoader;
import com.cyhc.network.library.ui.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import io.reactivex.annotations.Nullable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.PUT;

public class RequestClient {

    private final String URL;
    private static final WeakHashMap<String,Object> PARAMS = RestCreator.getParams();
    private final RequestListener REQUEST;
    private final SuccessListener SUCCESS;
    private final FailureListener FAILURE;
    private final ErrorListener ERROR;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final RequestBody BODY;
    private final File FILE;
    private final LoaderStyle LOADER_STYLE;
    private final Context CONTEXT;

    public static Context appContext;
    public static String baseUrl;

    public static Map<String,String>header;

    /**
     * 初始化网络请求
     * @param context
     *
     * @param url
     *         基地址
     * @param map
     *         请求头
     */
    public static void init(Context context, String url, @Nullable Map<String,String> map){
        appContext = context;
        baseUrl = url;
        header = map;
    }


    public RequestClient(String url, WeakHashMap<String, Object> params, String downloadDir, String extension, String name, RequestListener request, SuccessListener success,
                         FailureListener failure, ErrorListener error, RequestBody body, File file, Context context, LoaderStyle loaderStyle,Map<String,String>header) {
            this.URL = url;
            PARAMS.putAll(params);
            this.DOWNLOAD_DIR = downloadDir;
            this.EXTENSION = extension;
            this.NAME = name;
            this.REQUEST = request;
            this.SUCCESS = success;
            this.FAILURE = failure;
            this.ERROR = error;
            this.BODY = body;
            this.FILE = file;
            this.CONTEXT = context;
            this.LOADER_STYLE = loaderStyle;
            this.header = header;
    }

    public static RestClientBuilder builder(){
        return new RestClientBuilder();
    }

    private void request(HttpMethod method){
        final ApiService service = RestCreator.getApiServece();
        Call<String> call = null;

        if (REQUEST != null){
            REQUEST.onRequestStart();
        }
        if (LOADER_STYLE != null){
            LatteLoader.showLoading(CONTEXT,LOADER_STYLE);
        }

        switch (method){
            case GET:
                call = service.get(URL,PARAMS);
                break;
            case POST:
                call = service.post(URL,PARAMS);
                break;
            case POST_RAW:
                call = service.postRaw(URL,BODY);
                break;
            case DELETE:
                call = service.delete(URL,PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body = MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                call = RestCreator.getApiServece().upload(URL, body);
                break;
            case PUT:
                call = service.put(URL,PARAMS);
                break;
            case PUT_RAW:
                call = service.putRaw(URL,BODY);
                break;
            case POST_HEADER:
                call = service.post(URL,BODY);
                break;
                default:
                    break;
        }
        if (call != null) {
            call.enqueue(getRequestCallBack());
        }
    }

    private Callback<String> getRequestCallBack(){
        return new RequestCallBack(SUCCESS,FAILURE,ERROR,REQUEST,LOADER_STYLE);
    }

    public final void get(){
        request(HttpMethod.GET);
    }
    public final void post(){
        if (BODY == null){
            request(HttpMethod.POST);
        }else {
            request(HttpMethod.POST_HEADER);
        }
    }

    public final void postRaw(){
        if (BODY != null){
            request(HttpMethod.POST_RAW);
        }
    }

    public final void put(){
        if (BODY == null){
            request(HttpMethod.PUT);
        }else {
            if (!PARAMS.isEmpty()){
                throw new RuntimeException("params must be null");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    public final void delete(){
        request(HttpMethod.DELETE);
    }


    public final void upload(){
        request(HttpMethod.UPLOAD);
    }

    public final void download() {
        new DownloadHandler(URL, REQUEST, DOWNLOAD_DIR, EXTENSION, NAME,
                SUCCESS, FAILURE, ERROR)
                .handleDownload();
    }





}
