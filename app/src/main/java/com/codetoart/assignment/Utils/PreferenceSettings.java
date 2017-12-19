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

package com.codetoart.assignment.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by DELL on 12/9/2017.
 * <p>
 * A Common class to manage all preferences of app.
 */


public class PreferenceSettings {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private Context mContext;
    private int PRIVATE_MODE = 0;

    private static final String PREFERENCE_NAME = "upcomingmovies";

    private static final String KEY_USER_ID = "userid";
    private static final String KEY_COLOR = "color";

    public PreferenceSettings(Context context) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(PREFERENCE_NAME, PRIVATE_MODE);
        mEditor = mSharedPreferences.edit();
    }

    public void setPermission(String KEY_PERMISSION, boolean login) {
        mEditor.putBoolean(KEY_PERMISSION, login).commit();
    }

    public boolean getPermission(String KEY_PERMISSION) {
        return mSharedPreferences.getBoolean(KEY_PERMISSION, false);
    }

    public void setColor(String color) {
        mEditor.putString(KEY_COLOR, color).commit();
    }

    public String getColor() {
        return mSharedPreferences.getString(KEY_COLOR, null);
    }


    public void createUserSession() {

    }

    public void clearUserSession() {

    }

}
