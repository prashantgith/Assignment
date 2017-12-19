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

package com.codetoart.assignment.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.codetoart.assignment.R;

import java.util.ArrayList;

/**
 * Created by DELL on 12/9/2017.
 * <p>
 * An adapter class for image slider.
 */

public class SliderAdapter extends PagerAdapter {

    private ArrayList<Integer> mImages;
    private LayoutInflater mInflater;
    private Context mContext;

    public SliderAdapter(Context context, ArrayList<Integer> images) {
        this.mContext = context;
        this.mImages = images;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout =
                mInflater.inflate(R.layout.slider_view, view, false);
        ImageView myImage =
                (ImageView) myImageLayout.findViewById(R.id.gl_image);
        myImage.setImageResource(mImages.get(position));
        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
