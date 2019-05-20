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

package com.prof.youtubeparser.sample.java;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.prof.youtubeparser.VideoStats;
import com.prof.youtubeparser.models.stats.Statistics;
import com.prof.youtubeparser.models.videos.Video;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private List<Video> videos;
    private MainActivity mActivity;

    public VideoAdapter(List<Video> list, MainActivity activity) {
        this.videos = list;
        this.mActivity = activity;
    }

    public void clearData() {
        if (videos != null)
            videos.clear();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_video, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        final Video currentVideo = videos.get(position);

        String pubDateString = currentVideo.getDate();
        final String videoTitle = currentVideo.getTitle();

        //retrieve video link
        final String videoId = currentVideo.getVideoId();
        final String link = "https://www.youtube.com/watch?v=" + videoId;

        viewHolder.title.setText(currentVideo.getTitle());
        viewHolder.pubDate.setText(pubDateString);

        Picasso.get()
                .load(currentVideo.getCoverLink())
                .placeholder(R.drawable.placeholder)
                .fit()
                .centerCrop()
                .into(viewHolder.image);

        //show statistic of the selected video
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.getVideoStats(videoId);
            }
        });

        //open the video on Youtube
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                mActivity.startActivity(intent1);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return videos == null ? 0 : videos.size();
    }

    public List<Video> getList() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView pubDate;
        ImageView image;

        ViewHolder(View itemView) {

            super(itemView);
            title = itemView.findViewById(R.id.title);
            pubDate = itemView.findViewById(R.id.pubDate);
            image = itemView.findViewById(R.id.image);
        }
    }
}
