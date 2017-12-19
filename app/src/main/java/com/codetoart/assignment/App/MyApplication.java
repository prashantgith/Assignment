/*
 * Copyright 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codetoart.assignment.App;

import android.app.Application;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.codetoart.assignment.Utils.LruBitmapCache;
import com.codetoart.assignment.Utils.PreferenceSettings;


/**
 * Created by DELL on 12/9/2017.
 * <p>
 * An application class of project.
 */

public class MyApplication extends Application {
    public static final String TAG = MyApplication.class
            .getSimpleName();
    private static MyApplication sInstance;
    private PreferenceSettings mPreferenceSettings;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;


    }

    public MyApplication() {

    }

    public static synchronized MyApplication getInstance() {
        if (sInstance == null) {
            sInstance = new MyApplication();
        }

        return sInstance;
    }


    public PreferenceSettings getPreferenceSettings() {
        if (mPreferenceSettings == null) {
            mPreferenceSettings = new PreferenceSettings(getApplicationContext());
        }
        return mPreferenceSettings;
    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
