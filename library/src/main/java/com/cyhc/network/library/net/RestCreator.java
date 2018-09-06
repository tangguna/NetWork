package com.cyhc.network.library.net;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.annotations.Nullable;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;

public class RestCreator {

    public static final class ParamsHolder{
        public static final WeakHashMap<String,Object> PARAMS = new WeakHashMap<>();
    }

    public static final class HeadersHolder{
        public static final Map<String,String> MAPS = new HashMap<>();
    }

    public static WeakHashMap<String,Object> getParams(){
        return ParamsHolder.PARAMS;
    }

    public static Map<String,String> getHeaders(){
        return HeadersHolder.MAPS;
    }

    public static ApiService getApiService(){
        return ApiServiceHolder.API_SERVICE;
    }

    private static final class ApiHolder{
        private static final String BASE_URL = RequestClient.baseUrl;
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static final class OKHttpHolder {
        private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        Request request = original.newBuilder()
                                .headers(setHeaders(RequestClient.header))
                                .method(original.method(), original.body())
                                .build();
                        return chain.proceed(request);
                    }
                });


        private static final int TIME_OUT = 30;
        private static final OkHttpClient OK_HTTP_CLIENT = httpClient
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT,TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT,TimeUnit.SECONDS)
                .build();
    }

    private static final class  ApiServiceHolder{
        private static final ApiService API_SERVICE = ApiHolder.RETROFIT_CLIENT.create(ApiService.class);
    }

    /**
     * 设置请求头
     * @param headerParams
     * @return
     */
    private static Headers setHeaders(Map<String,String> headerParams){

        Headers headers = null;

        Headers.Builder builder = new Headers.Builder();
        if (headerParams != null){
            Iterator<String> iterator = headerParams.keySet().iterator();
            String key = "";
            while(iterator.hasNext()){
                key = iterator.next().toString();
                builder.add(key,headerParams.get(key));
            }
        }
        headers = builder.build();

        return headers;

    }
}
