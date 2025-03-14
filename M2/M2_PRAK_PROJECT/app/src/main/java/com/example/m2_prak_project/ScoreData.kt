package com.example.m2_prak_project

class ScoreData(val name: String, val score: Int) {
    override fun toString(): String {
        return "$name - $score"
    }
}