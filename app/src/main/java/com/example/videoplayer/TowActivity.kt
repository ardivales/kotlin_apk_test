package com.example.videoplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.videoplayer.databinding.ActivityMainBinding
import com.example.videoplayer.databinding.ActivityTowBinding

class TowActivity : AppCompatActivity() {

    private lateinit var binding:ActivityTowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tow)
        binding = ActivityTowBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val passedData : String = intent.getStringExtra("data").toString()
        binding.tvSecond.text = passedData
    }
}