package com.example.pendofilms.model

data class GetSearchSuggestions(
    val page: Int,
    val results: List<Suggestion>,
    val total_pages: Int,
    val total_results: Int
)