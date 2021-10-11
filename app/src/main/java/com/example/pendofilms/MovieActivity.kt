package com.example.pendofilms

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide



class MovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        // get movie details
        val titleMovie = intent?.extras?.getString("title")
        val poster = intent?.extras?.getString("poster")
        val rating = intent?.extras?.getString("rating")
        val date = intent?.extras?.getString("release date")
        val desc = intent?.extras?.getString("description")


        val ratingView: TextView = findViewById(R.id.movie_rating)
        val dateView: TextView = findViewById(R.id.movie_date)
        val descView: TextView = findViewById(R.id.movie_desc)
        val posterView: ImageView = findViewById(R.id.movie_poster)

        // set the appropriate details

        // set toolbar as support action bar
        setSupportActionBar(findViewById(R.id.toolbar))

        supportActionBar?.apply {
            title = titleMovie

            // show back button on toolbar
            // on back button press, it will navigate to main activity
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        ratingView.text = rating
        dateView.text = date
        descView.text = desc

        // taking care of loading animation
        val circularProgressDrawable = CircularProgressDrawable(this)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide.with(this)
            .load(poster)
            .placeholder(circularProgressDrawable)
            .into(posterView)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}