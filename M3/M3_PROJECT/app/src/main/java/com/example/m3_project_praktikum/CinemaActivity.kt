package com.example.m3_project_praktikum

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.m3_project_praktikum.databinding.ActivityCinemaBinding

class CinemaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCinemaBinding;
    lateinit var cinemaAdapter: CinemaAdapter
    lateinit var rvCinema: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCinemaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rvCinema = binding.rvAllCinema

        binding.btnBack.setOnClickListener {
            finish()
        }

        setupRecycleViewCinema()
    }

    private fun setupRecycleViewCinema(){
        var layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        var layout:Int = R.layout.cinema_item

        cinemaAdapter = CinemaAdapter(DataRepository.cinemaList, layout, "nonmain",
            { cinema: Cinema ->
                DataRepository.currentCinema = cinema
                var intent: Intent = Intent(this, CinemaDetailActivity::class.java)
                startActivity(intent)
            }
        )

        rvCinema.apply {
            this.layoutManager = layoutManager
            this.adapter = cinemaAdapter
        }
    }
}