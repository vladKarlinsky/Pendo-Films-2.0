package com.example.pendofilms

import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pendofilms.adapter.ItemAdapter
import com.example.pendofilms.adapter.SuggestionAdapter
import com.example.pendofilms.model.GetResponseMovies
import com.example.pendofilms.model.GetSearchSuggestions
import com.example.pendofilms.model.Movie
import com.example.pendofilms.model.Suggestion
import com.example.pendofilms.network.MovieApi
import retrofit2.Call
import retrofit2.Callback

class SearchActivity: AppCompatActivity() {

    lateinit var suggestions: List<Suggestion>
    lateinit var search: SearchView
    lateinit var context: Context
    lateinit var recyclerView: RecyclerView
    lateinit var manager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(findViewById(R.id.toolbar))

        supportActionBar?.apply {
            title = "Search"

            // show back button on toolbar
            // on back button press, it will navigate to main activity
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        context = this
        manager = LinearLayoutManager(context)
        recyclerView = findViewById(R.id.list_suggestions)
        recyclerView.layoutManager = manager
        // placeholder suggestions
        suggestions = listOf()
        recyclerView.adapter = SuggestionAdapter(suggestions)


        search = findViewById(R.id.search)

        search.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null) {
                    // load recycle view with suggestions
                    loadSuggestions(p0)
                }
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 != null) {
                    // load recycle view with suggestions
                    loadSuggestions(p0)
                }
                return false
            }
        })

    }

    private fun loadSuggestions(query: String){
        MovieApi.retrofitService.getSuggestions(query).enqueue(object: Callback<GetSearchSuggestions> {

            override fun onResponse(call: Call<GetSearchSuggestions>, response: retrofit2.Response<GetSearchSuggestions>) {
                if (response.isSuccessful) {
                    suggestions = response.body()?.results!!
                    recyclerView.adapter = SuggestionAdapter(suggestions)
                }
            }

            override fun onFailure(call: Call<GetSearchSuggestions>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}