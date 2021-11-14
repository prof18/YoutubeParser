package com.prof.youtubeparser.sample.kotlin

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.prof.youtubeparser.models.videos.Video
import com.squareup.picasso.Picasso

class VideoAdapter(var items: List<Video>, val activity: MainActivity) :
    RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title = itemView.findViewById<TextView>(R.id.title)
        private val pubDate = itemView.findViewById<TextView>(R.id.pubDate)
        private val image = itemView.findViewById<ImageView>(R.id.image)

        fun bind(item: Video) {
            val pubDateString = item.date

            //retrieve video link
            val videoId = item.videoId
            val link = "https://www.youtube.com/watch?v=" + videoId!!

            title.text = item.title
            pubDate.text = pubDateString

            Picasso.get()
                .load(item.coverLink)
                .placeholder(R.drawable.placeholder)
                .fit()
                .centerCrop()
                .into(image)

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
