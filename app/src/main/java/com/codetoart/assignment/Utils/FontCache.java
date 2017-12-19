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
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DELL on 12/9/2017.
 */

public class FontCache {
    private static Map<String, Typeface> fontMap = new HashMap<String, Typeface>();

    public static Typeface getFont(Context context, String fontName) {
        if (fontMap.containsKey(fontName)) {
            return fontMap.get(fontName);
        } else {
            Typeface tf = Typeface.createFromAsset(context.getAssets(), fontName);
            fontMap.put(fontName, tf);
            return tf;
        }
    }
}
