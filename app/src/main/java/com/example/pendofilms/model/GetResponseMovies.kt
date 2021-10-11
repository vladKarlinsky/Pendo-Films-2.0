package com.example.pendofilms.model

data class GetResponseMovies(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)