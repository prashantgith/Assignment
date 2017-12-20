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

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.codetoart.assignment.Adapter.MoviesAdapter;
import com.codetoart.assignment.App.MyApplication;
import com.codetoart.assignment.Interface.ClickListener;
import com.codetoart.assignment.Pojjo.Movie;
import com.codetoart.assignment.R;
import com.codetoart.assignment.Utils.AppUrl;
import com.codetoart.assignment.Utils.CommonUtils;
import com.codetoart.assignment.Utils.RecyclerTouchListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DELL on 12/9/2017.
 *
 * A file to declare commonly used utility functions
 *
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ArrayList<Movie> mMovieList;
    private RecyclerView mRvMovieList;
    private MoviesAdapter mAdapter;
    private Toolbar mTbMovieList;
    private ImageView mIvInfo;
    private ProgressBar mPbMovieList;
    private FrameLayout mLlMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initialize();
    }

    private void initialize() {
        mTbMovieList = (Toolbar) findViewById(R.id.tb_movie_list);
        setSupportActionBar(mTbMovieList);

        mIvInfo = (ImageView) mTbMovieList.findViewById(R.id.img_info);
        mRvMovieList = (RecyclerView) findViewById(R.id.rv_movie_list);
        mPbMovieList = (ProgressBar) findViewById(R.id.pb_movie_list);
        mLlMain = (FrameLayout) findViewById(R.id.ll_main_movie_list);

        mMovieList = new ArrayList<>();

        mRvMovieList.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRvMovieList.setLayoutManager(mLayoutManager);
//        rvMovieList.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
//        rvMovieList.setItemAnimator(new DefaultItemAnimator());


        mRvMovieList.addOnItemTouchListener(new RecyclerTouchListener(MainActivity.this, mRvMovieList, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(MainActivity.this, MovieDetailsActivity.class);
                i.putExtra("movie", (Parcelable) mMovieList.get(position));
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        mIvInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, InfoActivity.class));
            }
        });

        if (CommonUtils.isNetworkConnected(MainActivity.this)) {
            getMovies();
        } else {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.check_connection), Toast.LENGTH_LONG).show();
        }

    }

    /* Calling web-service to fetch all movie list*/
    private void getMovies() {
        mPbMovieList.setVisibility(View.VISIBLE);
        CommonUtils.disableAllView(mLlMain);
        mLlMain.setAlpha((float) 0.4);

        StringRequest strReq = new StringRequest(Request.Method.POST, AppUrl.GET_MOVIES, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, " response : " + response);
                mPbMovieList.setVisibility(View.GONE);
                CommonUtils.enableAllView(mLlMain);
                mLlMain.setAlpha(1);
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    mMovieList = CommonUtils.parseMovieList(jsonObject);
                    mAdapter = new MoviesAdapter(mMovieList, MainActivity.this);
                    mRvMovieList.setAdapter(mAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Volley Error: " + error.getMessage());
                Toast.makeText(MainActivity.this, "" + getResources().getString(R.string.something_went_wrong)
                        , Toast.LENGTH_SHORT).show();
                mPbMovieList.setVisibility(View.GONE);
                CommonUtils.enableAllView(mLlMain);
                mLlMain.setAlpha(1);

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("api_key", "b7cd3340a794e5a2f35e3abb820b497f");
                Log.e(TAG, "etPgArams initialize params list: " + params.toString());
                return params;
            }

        };

        strReq.setRetryPolicy(new DefaultRetryPolicy(50000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        strReq.setShouldCache(false);
        MyApplication.getInstance().addToRequestQueue(strReq);
    }
}
