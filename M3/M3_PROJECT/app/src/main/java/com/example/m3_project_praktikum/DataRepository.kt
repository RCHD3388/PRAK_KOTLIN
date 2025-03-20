package com.example.m3_project_praktikum

class DataRepository {
    companion object{
        var categoryList: ArrayList<Category> = arrayListOf<Category>(
            Category(R.drawable.movies, "Movies"),
            Category(R.drawable.mfood, "m.food"),
            Category(R.drawable.cinemas, "Cinema"),
        )

        var movieList: ArrayList<Movie> = arrayListOf<Movie>(
            Movie("When the Phone Rings", "Lorem ipsum dolor sit amet, consectetur " +
                    "adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna " +
                    "aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi " +
                    "ut aliquip ex ea commodo consequat.", "2024", "MBC", "Romance", "R13+", "2", "48", 4.5f, R.drawable.poster_1),
            Movie("Goblin", "Lorem ipsum dolor sit amet, consectetur " +
                    "adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna " +
                    "aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi " +
                    "ut aliquip ex ea commodo consequat.", "2024", "MBC", "Romance", "R13+", "2", "48", 4.0f, R.drawable.poster_2),
            Movie("Vincenzo", "Lorem ipsum dolor sit amet, consectetur " +
                    "adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna " +
                    "aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi " +
                    "ut aliquip ex ea commodo consequat.", "2024", "MBC", "Romance", "R13+", "2", "48", 3.2f, R.drawable.poster_3),
            Movie("Happiness", "Lorem ipsum dolor sit amet, consectetur " +
                    "adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna " +
                    "aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi " +
                    "ut aliquip ex ea commodo consequat.", "2024", "MBC", "Romance", "R13+", "2", "48", 4.7f, R.drawable.poster_4),
            Movie("Doom At Your Service", "Lorem ipsum dolor sit amet, consectetur " +
                    "adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna " +
                    "aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi " +
                    "ut aliquip ex ea commodo consequat.", "2024", "MBC", "Romance", "R13+", "2", "48", 5f, R.drawable.poster_5),
        )

        var cinemaList: ArrayList<Cinema> = arrayListOf<Cinema>(
            Cinema("Ciputra World", "Gatau lupa juga", "081236754", "10", arrayListOf("Premiere", "IMAX")),
            Cinema("Galaxy Mall", "Gatau lupa juga", "081236754", "10", arrayListOf("Premiere")),
            Cinema("Tunjungan Plaza", "Gatau lupa juga", "081236754", "10", arrayListOf("Cinema XXI", "IMAX")),
            Cinema("Pakuwon Mall", "Gatau lupa juga", "081236754", "10", arrayListOf("Cinema XXI", "Premiere")),
        )

        val moviePosterList: ArrayList<MoviePoster> = arrayListOf<MoviePoster>(
            MoviePoster(R.drawable.poster_1, true, "Gambar 1"),
            MoviePoster(R.drawable.poster_2, false, "Gambar 2"),
            MoviePoster(R.drawable.poster_3, false, "Gambar 3"),
            MoviePoster(R.drawable.poster_4, false, "Gambar 4"),
            MoviePoster(R.drawable.poster_5, false, "Gambar 5"),
        )

        var currentCinema: Cinema? = null
        var currentMovie: Movie? = null
    }
}