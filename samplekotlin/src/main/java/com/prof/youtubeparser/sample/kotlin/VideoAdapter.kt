package com.prof.youtubeparser.sample.kotlin;

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prof.youtubeparser.models.videos.Video
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_video.view.*

class VideoAdapter(var items: MutableList<Video>, val activity: MainActivity) : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Video) {
            val pubDateString = item.date
            val videoTitle = item.title

            //retrieve video link
            val videoId = item.videoId
            val link = "https://www.youtube.com/watch?v=" + videoId!!

            itemView.title.text = item.title
            itemView.pubDate.text = pubDateString

            Picasso.get()
                    .load(item.coverLink)
                    .placeholder(R.drawable.placeholder)
                    .fit()
                    .centerCrop()
                    .into(itemView.image)

            //show statistic of the selected video
            itemView.setOnClickListener { activity.getVideoStats(videoId) }

            //open the video on Youtube
            itemView.setOnLongClickListener {
                val intent1 = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                activity.startActivity(intent1)
                false
            }
        }
    }
}
