package com.klinovvlad.testtaskklinov.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.klinovvlad.testtaskklinov.databinding.ActivityGifBinding

class GifActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGifBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGifBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.getStringExtra("image")
        Glide.with(this)
            .load(url)
            .into(binding.imageView2)
    }
}