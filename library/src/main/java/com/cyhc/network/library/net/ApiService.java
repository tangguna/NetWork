package com.cyhc.network.library.net;

import com.google.gson.JsonElement;

import java.util.WeakHashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 网络请求
 */
public interface ApiService {

    /**
     * 表单
     * @param url
     * @param params
     * @return
     */
    @GET
    Call<String> get(@Url String url, @QueryMap WeakHashMap<String, Object> params);

    @Streaming
    @GET
    Call<ResponseBody> download(@Url String url, @QueryMap WeakHashMap<String, Object> params);


    @FormUrlEncoded
    @POST
    Call<String> post(@Url String url, @FieldMap WeakHashMap<String, Object> params);


    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST
    Call<String> post(@Url String url,@Body RequestBody body);

    /**
     * JSON字符串
     * @param url
     * @param body
     * @return
     */
    @POST
    Call<String> postRaw(@Url String url, @Body RequestBody body);

    @Multipart
    @POST
    Call<String> upload(@Url String url, @Part MultipartBody.Part file);

    @POST
    Call<String> putRaw(@Url String url, @Body RequestBody body);

    @FormUrlEncoded
    @PUT
    Call<String> put(@Url String url, @QueryMap WeakHashMap<String, Object> params);

    @DELETE
    Call<String> delete(@Url String url, @QueryMap WeakHashMap<String, Object> params);

    /**
     * 下载固定路径文件
     * @return
     */
    @GET
    Call<String> downloadFileWithFixedUrl();

    /**
     * 下载文件与动态Url同步 下载大文件
     * @param fileUrl
     *         文件路径
     * @return
     */
    @Streaming
    @GET
    Call<String> downloadFileWithDynamicUrlSync(@Url String fileUrl);

}
