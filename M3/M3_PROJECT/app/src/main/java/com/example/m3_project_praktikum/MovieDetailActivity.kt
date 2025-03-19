package com.example.m3_project_praktikum

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.m3_project_praktikum.databinding.ActivityMovieDetailBinding

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnBack2.setOnClickListener {
            finish()
        }

        var current = DataRepository.currentMovie!!

        binding.tvTitle.setText(current.title)
        binding.ivPoster.setImageResource(current.poster)
        binding.tvDesc.setText(current.synopsis)
        binding.bannerRating.setText("${current.rating}/5 ⭐")
        binding.bannerAge.setText(current.agerating)
        binding.bannerDuration.setText("${current.hour}h ${current.minute}m")
        binding.tvGenre.setText("Genre : ${current.genre}")
        binding.tvProdHouse.setText("Production House : ${current.production}")
        binding.tvYear.setText("Year of Release : ${current.year}")
    }
}