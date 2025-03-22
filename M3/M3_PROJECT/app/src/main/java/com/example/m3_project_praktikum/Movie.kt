package com.example.m3_project_praktikum

class Movie (
    var title: String,
    var synopsis: String,
    var year: String,
    var production: String,
    var genre: String,
    var agerating: String,
    var hour: String,
    var minute: String,
    var rating: Float,
    var poster: Int,
)

class MoviePoster (
    var poster: Int,
    var checked: Boolean,
    var label: String
)