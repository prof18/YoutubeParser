package com.prof.youtubeparser.example;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
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
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private Activity mActivity;
    private ArrayList<Video> videos;
    private static LayoutInflater inflater;

    private int rowLayout;
    private Context mContext;


    public VideoAdapter(ArrayList<Video> list, int rowLayout, Context context) {

        this.videos = list;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }


    @Override
    public long getItemId(int item) {
        // TODO Auto-generated method stub
        return item;
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

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                Toast.makeText(mContext, "Item Clicked", Toast.LENGTH_SHORT).show();

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