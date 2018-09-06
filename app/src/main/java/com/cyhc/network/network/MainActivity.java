package com.cyhc.network.network;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.cyhc.network.library.callback.ErrorListener;
import com.cyhc.network.library.callback.FailureListener;
import com.cyhc.network.library.callback.SuccessListener;
import com.cyhc.network.library.net.GsonUtils;
import com.cyhc.network.library.net.RequestClient;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test();
    }

    private void test(){
        RequestClient.builder()
                .url("weekly")
                .params("apikey","0b2bdeda43b5688921839c8ecb20399b")
                .params("city","北京")
                .params("client","")
                .params("udid","")
                .success(new SuccessListener() {
                    @Override
                    public void success(String s) {
                        Log.d("输出",s.toString());
                    }
                })
                .failure(new FailureListener() {
                    @Override
                    public void failure() {
                        Log.d("输出","错误");
                    }
                })
                .error(new ErrorListener() {
                    @Override
                    public void error(int i, String s) {
                        Log.d("输出",s.toString());
                    }
                }).build().get();
    }

}
