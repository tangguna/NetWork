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
    }

}
