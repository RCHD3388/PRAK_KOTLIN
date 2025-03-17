package com.example.m3_project_praktikum

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.m3_project_praktikum.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var moviesAdapter: MoviesAdapter
    lateinit var rvCategory: RecyclerView
    lateinit var rvMovies: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rvCategory = binding.rvCategory
        rvMovies = binding.rvMovies

        setupRecycleViewCategory()
        setupRecycleViewMovies()
    }

    private fun setupRecycleViewCategory(){
        var layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        var layout:Int = R.layout.category_item

        categoryAdapter = CategoryAdapter(DataRepository.categoryList, layout)

        rvCategory.apply {
            this.layoutManager = layoutManager
            this.adapter = categoryAdapter
        }
    }
    private fun setupRecycleViewMovies(){
        var layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        var layout:Int = R.layout.movie_item

        moviesAdapter = MoviesAdapter(DataRepository.movieList, layout)

        rvMovies.apply {
            this.layoutManager = layoutManager
            this.adapter = moviesAdapter
        }
    }
}