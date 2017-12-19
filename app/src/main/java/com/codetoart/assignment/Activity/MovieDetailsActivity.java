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

package com.codetoart.assignment.Activity;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ViewTreeObserver;
import android.widget.RatingBar;
import android.widget.TextView;


import com.codetoart.assignment.Adapter.SliderAdapter;
import com.codetoart.assignment.Pojjo.Movie;
import com.codetoart.assignment.R;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class MovieDetailsActivity extends AppCompatActivity {

    private Toolbar mTbMovieDetails;
    private TextView mTvTitle, mTvMovieTitle, mTvOverview;
    private RatingBar mRbMovie;
    private Movie mMovie;
    int[] images = {R.drawable.download, R.drawable.downloada, R.drawable.downloadb, R.drawable.downloadc, R.drawable.downloadd};
    private static ViewPager mPager;
    private static int currentPage = 0;
    private ArrayList<Integer> mImageArray = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        mMovie = getIntent().getParcelableExtra("movie");
        initialize();
    }

    private void initialize() {
        mTbMovieDetails = (Toolbar) findViewById(R.id.tb_movie_details);
        setSupportActionBar(mTbMovieDetails);

        mTvTitle = (TextView) mTbMovieDetails.findViewById(R.id.txt_title);
        mTvOverview = (TextView) findViewById(R.id.txt_overview);
        mTvMovieTitle = (TextView) findViewById(R.id.txt_title_md);
        mRbMovie = (RatingBar) findViewById(R.id.rb_movie);

        setDetails();
        startSlider();
    }

    private void setDetails() {
        mTvTitle.setText(mMovie.getTitle());
        mTvMovieTitle.setText(mMovie.getTitle());
        mTvOverview.setText(mMovie.getOverview());
        float rate = (float) (mMovie.getPopularity() / 100);
        mRbMovie.setRating(rate);

        ViewTreeObserver vto = this.mTvOverview.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                ViewTreeObserver obs = mTvOverview.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (mTvOverview.getLineCount() > 5) {
                    int lineEndIndex = mTvOverview.getLayout().getLineEnd(4);
                    String text = mTvOverview.getText().subSequence(0, lineEndIndex - 5) + "...";
                    mTvOverview.setText(text);
                }
            }
        });
    }

    private void startSlider() {
        for (int i = 0; i < images.length; i++)
            mImageArray.add(images[i]);

        mPager = (ViewPager) findViewById(R.id.slider_pager);
        mPager.setAdapter(new SliderAdapter(MovieDetailsActivity.this, mImageArray));
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                if (currentPage == images.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
//        Timer swipeTimer = new Timer();
//        swipeTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, 2500, 2500);
    }

}
