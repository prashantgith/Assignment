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
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by DELL on 12/9/2017.
 * <p>
 * Custom Textview for different font style
 */

public class CustomNormalEditText extends EditText {

    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public CustomNormalEditText(Context context) {
        super(context);

        applyCustomFont(context, null);
    }

    public CustomNormalEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context, attrs);
    }

    public CustomNormalEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context, attrs);
    }

    private void applyCustomFont(Context context, AttributeSet attrs) {
        int textStyle = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.BOLD);

        Typeface customFont = selectTypeface(context, textStyle);
        setTypeface(customFont);
    }

    private Typeface selectTypeface(Context context, int textStyle) {
        /*
        * information about the TextView textStyle:
        * http://developer.android.com/reference/android/R.styleable.html#TextView_textStyle
        */
        switch (textStyle) {
            case Typeface.BOLD: // bold
                return FontCache.getFont(context, "Calibri.ttf");

//            case Typeface.ITALIC: // italic
//                return FontCache.getFont(context, "fonts/Lato-Italic.ttf");

//            case Typeface.BOLD_ITALIC: // bold italic
//                return FontCache.getFont(context, "fonts/CHARLEVOIXPRO-MEDIUM.OTF");

//            case Typeface.NORMAL: // regular
//                return FontCache.getFont(context, "fonts/CHARLEVOIXPRO-LIGHT.OTF");
            default:
                return FontCache.getFont(context, "Calibri.ttf");
        }
    }
}
