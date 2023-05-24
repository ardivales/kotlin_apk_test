package com.example.videoplayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class VideoAdapter(
    private var videos: List<Video>,
    private val onItemClickListener: (Video) -> Unit
) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {

        val videoIndex = position * 3


        holder.bind(videoIndex, videos.getOrNull(videoIndex))
        holder.bind(videoIndex + 1, videos.getOrNull(videoIndex + 1))
        holder.bind(videoIndex + 2, videos.getOrNull(videoIndex + 2))
    }


    override fun getItemCount(): Int {
        return (videos.size + 2) / 3
    }

    fun updateData(newVideos: List<Video>) {
        videos = newVideos
        notifyDataSetChanged()
    }

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val thumbnailImageViews: Array<ImageView> = arrayOf(
            itemView.findViewById(R.id.thumbnailImageView1),
            itemView.findViewById(R.id.thumbnailImageView2),
            itemView.findViewById(R.id.thumbnailImageView3)
        )

        private val titleTextViews: Array<TextView> = arrayOf(
            itemView.findViewById(R.id.titleTextView1),
            itemView.findViewById(R.id.titleTextView2),
            itemView.findViewById(R.id.titleTextView3)
        )

        fun bind(videoIndex: Int, video: Video?) {
            if (video != null) {
                Picasso.get().load(video.thumbnail).into(thumbnailImageViews[videoIndex])
                titleTextViews[videoIndex].text = video.title
                itemView.setOnClickListener { onItemClickListener(video) }
            } else {
                // Si la vidéo est nulle, masquez la vue correspondante
                thumbnailImageViews[videoIndex].visibility = View.GONE
                titleTextViews[videoIndex].visibility = View.GONE
            }
        }
    }
}
