package com.example.videoplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainVideo : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: VideoAdapter
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_video)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = VideoAdapter(emptyList()) { video ->
            val intent = Intent(this@MainVideo, VideoPlayerActivity::class.java)
            intent.putExtra("videoUrl", video.url)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/") //
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        fetchVideos()
    }

    private fun fetchVideos() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val videos = apiService.getVideos()
                adapter.updateData(videos)
            } catch (e: Exception) {
                Toast.makeText(this@MainVideo, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}