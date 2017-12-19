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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codetoart.assignment.Pojjo.Movie;
import com.codetoart.assignment.R;
import com.codetoart.assignment.Utils.AppUrl;
import com.codetoart.assignment.Utils.CommonUtils;

import java.util.ArrayList;

/**
 * Created by DELL on 12/9/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private ArrayList<Movie> mMoviesList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivPoster, ivNext;
        public TextView tvTitle, tvDate, tvAdult;


        public MyViewHolder(View view) {
            super(view);

            ivPoster = (ImageView) view.findViewById(R.id.img_poster);
            ivNext = (ImageView) view.findViewById(R.id.img_next);
            tvTitle = (TextView) view.findViewById(R.id.txt_movie_title);
            tvDate = (TextView) view.findViewById(R.id.txt_date);
            tvAdult = (TextView) view.findViewById(R.id.txt_certificate);
        }
    }


    public MoviesAdapter(ArrayList<Movie> moviesList, Context context) {
        this.mMoviesList = moviesList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.row_movie_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie movie = mMoviesList.get(position);

        if (movie.getTitle() != null && movie.getTitle().length() > 0) {
            holder.tvTitle.setText(movie.getTitle());
        } else {
            holder.tvTitle.setText("");
        }

        if (movie.getRelease_date() != null && movie.getRelease_date().length() > 0) {
            holder.tvDate.setText(movie.getRelease_date());
        } else {
            holder.tvDate.setText("");
        }

        if (movie.isAdult()) {
            holder.tvAdult.setText(mContext.getResources().getString(R.string.adult));
        } else {
            holder.tvAdult.setText(mContext.getResources().getString(R.string.non_adult));
        }


        if (movie.getPoster_path() != null && movie.getPoster_path().length() > 0) {
            String s = AppUrl.GET_MOVIES + movie.getPoster_path();
            CommonUtils.loadImage(s, holder.ivPoster, mContext);
        } else {
            holder.ivPoster.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_poster));
        }
    }

    @Override
    public int getItemCount() {
        return this.mMoviesList.size();
    }
}
