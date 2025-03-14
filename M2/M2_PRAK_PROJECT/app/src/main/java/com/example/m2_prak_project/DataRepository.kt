package com.example.m2_prak_project

import com.google.android.material.color.utilities.Score

class DataRepository {
    companion object {
        var leaderboard: ArrayList<ScoreData> = arrayListOf<ScoreData>()

        fun addNewScore(name: String, score: Int){
            leaderboard.add(ScoreData(name, score))
            leaderboard.sortByDescending { it.score }
        }
    }
}