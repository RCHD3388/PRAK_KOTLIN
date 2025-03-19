package com.example.m3_project_praktikum

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.m3_project_praktikum.databinding.ActivityAddCinemaBinding

class AddCinemaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddCinemaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddCinemaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnSave.setOnClickListener {
            handleSave()
        }
    }

    fun handleSave(){
        var title = binding.etTitle.text.toString()
        var address = binding.etAddress.text.toString()
        var telp = binding.etTelp.text.toString()
        var distance = binding.etDistance.text.toString()
        var premiere = binding.cbPremiere.isChecked
        var imax = binding.cbImax.isChecked
        var cinemaXXI = binding.cbCinemaXXI.isChecked

        if(!title.isEmpty() && !address.isEmpty() && !telp.isEmpty() && !distance.isEmpty() && (premiere || imax || cinemaXXI)){
            var types = arrayListOf<String>();
            if(premiere) types.add("Premiere")
            if(imax) types.add("IMAX")
            if(cinemaXXI) types.add("Cinema XXI")
            DataRepository.cinemaList.add(Cinema(title, address, telp, distance, types))
            finish()
        }else{
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
        }
    }
}