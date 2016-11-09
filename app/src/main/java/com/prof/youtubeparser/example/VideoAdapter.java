/*
*   Copyright 2016 Marco Gomiero
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*   
*/

package com.prof.youtubeparser.example;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.prof.youtubeparser.models.Video;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by marco on 6/15/16.
 *
 * Adapter for the recycler view
 *
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private ArrayList<Video> videos;
    private int rowLayout;
    private Context mContext;


    public VideoAdapter(ArrayList<Video> list, int rowLayout, Context context) {

        this.videos = list;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }

    public void clearData(){

        if (videos != null)
            videos.clear();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position)  {

        final Video currentVideo = videos.get(position);

        String pubDateString = currentVideo.getDate();

        //retrive video link
        final String videoId = currentVideo.getVideoId();
        final String link = "https://www.youtube.com/watch?v=" + videoId;

        viewHolder.title.setText(currentVideo.getTitle());
        viewHolder.pubDate.setText(pubDateString);

        Picasso.with(mContext)
                .load(currentVideo.getCoverLink())
                .placeholder(R.drawable.placeholder)
                .fit().centerCrop()
                .into(viewHolder.image);

       //open the video on Youtube
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                mContext.startActivity(intent1);
            }
        });
    }

    @Override
    public int getItemCount() {

        return videos == null ? 0 : videos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView pubDate;
        ImageView image;

        public ViewHolder(View itemView) {

            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            pubDate = (TextView) itemView.findViewById(R.id.pubDate);
            image = (ImageView)itemView.findViewById(R.id.image);
        }
    }
}