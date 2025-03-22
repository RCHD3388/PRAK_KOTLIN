
package com.example.m3_project_praktikum

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.m3_project_praktikum.databinding.ActivityAddCinemaBinding
import com.example.m3_project_praktikum.databinding.ActivityMovieBinding

class MovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieBinding
    private lateinit var movieAdapter: MovieMainAdapter
    lateinit var rvMovies: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        rvMovies = binding.rvAllMovie
        binding.btnBack.setOnClickListener { finish() }
        binding.rbIcons.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                setupRecycleViewMovies(1)
            }else{
                setupRecycleViewMovies(0)
            }
        }
        binding.rbName.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                DataRepository.movieList.sortBy { it.title.lowercase() }
            }else{
                DataRepository.movieList.sortByDescending { it.rating }
            }
            movieAdapter.notifyDataSetChanged()
        }


        setupRecycleViewMovies(0)
        initCond()
    }
    private fun initCond(){
        DataRepository.movieList.sortBy { it.title.lowercase() }
        movieAdapter.notifyDataSetChanged()
    }
    private fun setupRecycleViewMovies(type: Int){
        var layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        var layout:Int = R.layout.movie_card

        if(type == 1){
            layoutManager = GridLayoutManager(this, 2)
            layout = R.layout.movie_icon
        }

        movieAdapter = MovieMainAdapter(DataRepository.movieList, layout, {movie: Movie ->
            val intent = Intent(this, MovieDetailActivity::class.java)
            DataRepository.currentMovie = movie
            startActivity(intent)
        })

        rvMovies.apply {
            this.layoutManager = layoutManager
            this.adapter = movieAdapter
        }
    }
}