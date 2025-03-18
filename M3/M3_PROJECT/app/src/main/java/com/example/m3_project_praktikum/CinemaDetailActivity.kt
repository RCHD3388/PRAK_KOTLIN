package com.example.m3_project_praktikum

import android.os.Bundle
import android.provider.ContactsContract.Data
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.m3_project_praktikum.databinding.ActivityCinemaDetailBinding

class CinemaDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCinemaDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCinemaDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnDetailBack.setOnClickListener {
            finish()
        }

        binding.tvTitle.setText(DataRepository.currentCinema!!.title)
        binding.tvLocation.setText(DataRepository.currentCinema!!.address)
        binding.tvPhone.setText(DataRepository.currentCinema!!.telp)
        binding.tvDistance.setText(DataRepository.currentCinema!!.distance)

        var lenght:Int = DataRepository.currentCinema!!.type.size
        var banners: ArrayList<Button> = arrayListOf(binding.bannerCn1,binding.bannerCn2,binding.bannerCn3)
        for(i in 0..2){
            if(i < lenght){
                banners[i].visibility = View.VISIBLE
                banners[i].text = DataRepository.currentCinema!!.type[i].toString()
            }else{
                banners[i].visibility = View.GONE
            }
        }
    }
}