package com.example.m3_project_praktikum

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.m3_project_praktikum.databinding.ActivityAddMovieBinding

class AddMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddMovieBinding
    lateinit var moviePosterAdapter: MoviePosterAdapter
    lateinit var rvPoster: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rvPoster = binding.rvPoster
        binding.btnBack3.setOnClickListener { finish() }

        setupRecycleViewCinema()

        binding.btnSave.setOnClickListener {
            var title = binding.etTitle.text.toString()
            var synopsis = binding.etSynopsis.text.toString()
            var year = binding.etYear.text.toString()
            var production = binding.spProd.selectedItem.toString()

            var genre = ", "
            if (binding.cbRomance.isChecked) genre += "Romance, "
            if (binding.cbThriller.isChecked) genre += "Thriller, "
            if (binding.cbFantasy.isChecked) genre += "Fantasy, "
            if (binding.cbHorror.isChecked) genre += "Horror, "
            if (binding.cbSciFi.isChecked) genre += "Sci-Fi, "
            if (binding.cbMystery.isChecked) genre += "Mystery, "

            genre = genre.dropLast(2)

            var agerating = ""
            if(binding.rbSU.isChecked) agerating = "SU"
            if(binding.rbR13.isChecked) agerating = "R13+"
            if(binding.rbD17.isChecked) agerating = "D17+"

            var hour = binding.etHour.text.toString()
            var minute = binding.etMinutes.text.toString()

            var rating = binding.ratingBar.rating
            var poster = DataRepository.moviePosterList.find { it.checked }?.poster

            if(!title.isEmpty() && !synopsis.isEmpty() && !year.isEmpty() && !production.isEmpty() && !genre.isEmpty() && !agerating.isEmpty() && !hour.isEmpty() && !minute.isEmpty() && poster != null) {
                DataRepository.movieList.add(
                    Movie(
                        title,
                        synopsis,
                        year,
                        production,
                        genre,
                        agerating,
                        hour,
                        minute,
                        rating,
                        poster
                    )
                )
                finish();
            }else{
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecycleViewCinema(){
        var layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        var layout:Int = R.layout.movie_poster_item

        moviePosterAdapter = MoviePosterAdapter(DataRepository.moviePosterList, layout, {
            moviePoster: MoviePoster ->
            DataRepository.moviePosterList.forEach { it.checked = false }
            moviePoster.checked = true
            moviePosterAdapter.notifyDataSetChanged()
        })

        rvPoster.apply {
            this.layoutManager = layoutManager
            this.adapter = moviePosterAdapter
        }
    }
}