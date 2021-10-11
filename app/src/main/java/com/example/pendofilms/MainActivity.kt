package com.example.pendofilms

import android.content.Intent
import com.example.pendofilms.SearchActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.pendofilms.adapter.ItemAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pendofilms.model.GetResponseMovies
import com.example.pendofilms.model.Movie
import com.example.pendofilms.network.MovieApi
import retrofit2.Call
import retrofit2.Callback
import android.view.View
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView


class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var manager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find the toolbar view inside the activity layout
        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar)

        manager = GridLayoutManager(this,2)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = manager
        // create empty recycle view as placeholder
        val placeholder = ItemAdapter(listOf<Movie>())
        recyclerView.adapter = placeholder


        // load recycle view with default movies
        if (intent.extras?.getString("suggestion query") == null) {
            loadDefaultMovies()
        } else {
            // load recycle view with movies using search query sent from search screen
            loadMoviesSearch(intent.extras?.getString("suggestion query")!!)
        }


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    private fun loadDefaultMovies(){
        MovieApi.retrofitService.getDefaultMovies().enqueue(object: Callback<GetResponseMovies> {

            override fun onResponse(call: Call<GetResponseMovies>, response: retrofit2.Response<GetResponseMovies>) {
                if (response.isSuccessful) {
                    recyclerView.adapter = ItemAdapter(response.body()?.results!!)
                }
            }

            override fun onFailure(call: Call<GetResponseMovies>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun loadMoviesSearch(query: String){
        MovieApi.retrofitService.getMoviesSearch(query).enqueue(object: Callback<GetResponseMovies> {

            override fun onResponse(call: Call<GetResponseMovies>, response: retrofit2.Response<GetResponseMovies>) {
                if (response.isSuccessful) {
                    recyclerView.adapter = ItemAdapter(response.body()?.results!!)
                }
            }

            override fun onFailure(call: Call<GetResponseMovies>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    // taking care of navigation to search screen
    override fun onOptionsItemSelected(item : MenuItem): Boolean {
            val intent = Intent(applicationContext, SearchActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            applicationContext.startActivity(intent)
            return true
        }
}