package com.example.popularmovies.adapter

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.example.popularmovies.R
import com.example.popularmovies.databinding.ItemVideoBinding
import com.example.popularmovies.model.Video
import com.squareup.picasso.Picasso

class VideoAdapter (private val mActivity: Activity) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {
    var videos: List<Video> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoAdapter.VideoViewHolder {
        val layoutInflater = LayoutInflater.from(mActivity)
        val videoBinding = ItemVideoBinding.inflate(layoutInflater, parent, false)
        return VideoViewHolder(videoBinding)
    }


    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = videos[position]
        holder.bind(video)
        holder.videoBinding.root.setOnClickListener { v ->
            val context = v.context
            val appIntent = Intent(Intent.ACTION_VIEW,
                    Uri.parse("vnd.youtube:${video.videoUrl}"))

            val webIntent = Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/watch?v=${video.videoUrl}"))
            try {
                context.startActivity(appIntent)
            } catch (ex: ActivityNotFoundException) {
                context.startActivity(webIntent)
            }
        }

        Picasso.get()
                .load("https://img.youtube.com/vi/${video.videoUrl}/hqdefault.jpg")
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.drawable.trailer_place_holder)
                .into(holder.videoBinding.videoImageView)

    }

    override fun getItemCount(): Int {
        return videos.size
    }

    fun setReviews(videos: List<Video>){
        this.videos = videos
        notifyDataSetChanged()

    }


    //A view holder inner class where we get reference to the views in the layout using their ID
    class VideoViewHolder(val videoBinding: ItemVideoBinding) : RecyclerView.ViewHolder(videoBinding.root) {

        fun bind(video: Video){
            videoBinding.viewModel = video
        }

    }
}
