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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Cache;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.codetoart.assignment.App.MyApplication;
import com.codetoart.assignment.Pojjo.Movie;
import com.codetoart.assignment.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by DELL on 12/9/2017.
 *
 * A file to declare commonly used utility functions
 *
 */

public class CommonUtils {


    /* checks whether device is connected by internet service. */
    public static boolean isNetworkConnected(Context context)
    {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni == null) {
                // There are no active networks.
                return false;
            }
            else {
                return true;
            }

    }



    public static void setToast(Context mContext, String message, boolean isLong)
    {
        PreferenceSettings mPreferenceSettings = MyApplication.getInstance().getPreferenceSettings();

        Toast toasta;
        if (isLong)
        {
            toasta = Toast.makeText(mContext, "", Toast.LENGTH_LONG);
        }
        else
        {
            toasta = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
        }

        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();

        View view = toasta.getView();
        view.setAlpha((float) 0.9);

            view.setBackgroundResource(R.drawable.round_toast);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setElevation(2);
        }

        TextView text = (TextView) view.findViewById(android.R.id.message);
        text.setText("   " + message + "   ");
        text.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
        text.setTextColor(mContext.getResources().getColor(R.color.PrimaryTextColor));

//        toasta.setMargin(0,100);
        toasta.show();

        /*if (isLong)
        {
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }*/
    }

    /* Makes all views disable */
    public static void disableAllView(ViewGroup layout) {
        layout.setEnabled(false);
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (child instanceof ViewGroup) {
                disableAllView((ViewGroup) child);
            } else {
                child.setEnabled(false);
            }
        }
    }

    /* enables all views */
    public static void enableAllView(ViewGroup layout) {
        layout.setEnabled(true);
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (child instanceof ViewGroup) {
                enableAllView((ViewGroup) child);
            } else {
                child.setEnabled(true);
            }
        }
    }


  /*  public static void loadimageWithloader(Context context, String url, ImageView imageView) {
        try {
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                    .build();

            final ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.init(config);

            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.mipmap.user) // resource or drawable
                    .showImageForEmptyUri(R.mipmap.user) // resource or drawable
                    .showImageOnFail(R.mipmap.user) // resource or drawable
                    .resetViewBeforeLoading(true)  // default
                    .cacheInMemory(true) // default => false
                    .cacheOnDisk(true) // default => false
                    .build();


            imageLoader.displayImage(url, imageView, options, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                    imageLoader.destroy();

                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


   /* public static void setImage2(Context context, String url, ImageView imageView) {
        Picasso.with(context)
                .load(url)
                .placeholder(context.getResources().getDrawable(R.mipmap.user))
                .error(context.getResources().getDrawable(R.mipmap.user))
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                    }
                });

    }
*/

   /* Loads image into imageview */
   public static void loadImage(String url, final ImageView imageView, final Context mContext)
   {

       try {
           ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();

           // If you are using normal ImageView

           imageLoader.get(url, new ImageLoader.ImageListener() {
               @Override
               public void onErrorResponse(VolleyError error) {
                   Log.e("Dashboard  VOLLEY", "Image Load Error: " + error.getMessage());
                   imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_poster));


               }

               @Override
               public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                   if (response.getBitmap() != null) {
                       // load image into imageview
                       imageView.setImageBitmap(response.getBitmap());
                       //Log.e("image", " loaded");

                   }
               }
           });


           Cache cache = MyApplication.getInstance().getRequestQueue().getCache();
           Cache.Entry entry = cache.get(url);
           if (entry != null) {
               try {
                   String data = new String(entry.data, "UTF-8");
                   // handle data, like converting it to xml, json, bitmap etc.,
               } catch (Exception e) {
                   e.printStackTrace();
               }
           } else {
               // cached response doesn't exists. Make a network call here
           }

       } catch (Exception e) {
           e.printStackTrace();

       }
   }

    /* Parse json response of web service */
    public static ArrayList<Movie> parseMovieList(JSONObject movieList)
    {
        ArrayList<Movie> list = new ArrayList<>();

        try {
            JSONArray results = movieList.getJSONArray("results");
            Movie movie;
            Gson gson = new Gson();

            for (int i = 0; i < results.length(); i++)
            {

                movie = new Movie();
                movie.setVote_count(results.getJSONObject(i).getInt("vote_count"));
                movie.setId(results.getJSONObject(i).getInt("id"));
                movie.setVideo(results.getJSONObject(i).getBoolean("video"));
                movie.setVote_average(results.getJSONObject(i).getDouble("vote_average"));
                movie.setTitle(results.getJSONObject(i).getString("title"));
                movie.setPopularity(results.getJSONObject(i).getDouble("popularity"));
                movie.setPoster_path(results.getJSONObject(i).getString("poster_path"));
                movie.setOriginal_language(results.getJSONObject(i).getString("original_language"));
                movie.setOriginal_title(results.getJSONObject(i).getString("original_title"));

                JSONArray jsonArray = results.getJSONObject(i).getJSONArray("genre_ids");

                int[] ints2 = gson.fromJson(String.valueOf(jsonArray), int[].class);
                movie.setGenre_ids(ints2);

                movie.setBackdrop_path(results.getJSONObject(i).getString("backdrop_path"));
                movie.setAdult(results.getJSONObject(i).getBoolean("adult"));
                movie.setOverview(results.getJSONObject(i).getString("overview"));
                movie.setRelease_date(results.getJSONObject(i).getString("release_date"));

                list.add(movie);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }




}

/*

Movie movie = moviesList.get(position);

        if(movie.getTitle() != null && movie.getTitle().length() > 0) {
            holder.tvTitle.setText(movie.getTitle());
        }
        else
        {
            holder.tvTitle.setText("");
        }

        if(movie.getRelease_date() != null && movie.getRelease_date().length() > 0) {
            holder.tvDate.setText(movie.getRelease_date());
        }
        else
        {
            holder.tvDate.setText("");
        }

        if(movie.isAdult())
        {
            holder.tvAdult.setText(mContext.getResources().getString(R.string.adult));
        }
        else
        {
            holder.tvAdult.setText(mContext.getResources().getString(R.string.non_adult));
        }


        if (movie.getPoster_path() != null && movie.getPoster_path().length() > 0)
        {

            CommonUtils.loadImage(movie.getPoster_path(), holder.ivPoster, mContext);
        }
        else
        {
            holder.ivPoster.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_poster));
        }

*/