package com.example.m2_prak_project

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.m2_prak_project.databinding.ActivityHighscoreBinding

class HighscoreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHighscoreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_highscore)
        binding = ActivityHighscoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var leaderboard = DataRepository.leaderboard;
        var leaderboardLength = leaderboard.size

        var index = 0;
        while(index <= 2 && leaderboardLength-1 >= index){
            var leaderBoardValue = leaderboard[index].toString()
            if(index == 0){
                binding.tvRank1.setText(leaderBoardValue)
            }else if(index == 1){
                binding.tvRank2.setText(leaderBoardValue)
            }else if(index == 2){
                binding.tvRank3.setText(leaderBoardValue)
            }
            index++
        }


        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}