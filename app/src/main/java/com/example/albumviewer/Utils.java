package com.example.albumviewer;

import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Utils {

    private static final String TAG = Utils.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private static MyApplication mInstance;
    public static synchronized  MyApplication getInstance(){
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        if (mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(null);
        }
        return mRequestQueue;
    }

    public <T> void addRequestQueue(Request<T> request, String tag){
        request.setTag(TextUtils.isEmpty(tag)? "TAG": tag);
        getRequestQueue().add(request);
    }
}
