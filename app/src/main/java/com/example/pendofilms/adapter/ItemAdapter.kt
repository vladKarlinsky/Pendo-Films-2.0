package com.example.pendofilms.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.pendofilms.MovieActivity
import com.example.pendofilms.R
import com.example.pendofilms.model.Movie
import com.google.android.material.card.MaterialCardView


/**
 * Adapter for the [RecyclerView] in [MainActivity]. Displays [Movie] data object.
 */

private const val IMAGE_BASE_URL =
    "https://image.tmdb.org/t/p/original"

class ItemAdapter(
    private val Movies: List<Movie>
): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val cardView: MaterialCardView = view.findViewById(R.id.item_card)

        fun bind(movie: Movie){
            val title: TextView = view.findViewById(R.id.item_title)
            val rating: TextView = view.findViewById(R.id.item_rating)
            val imageView: ImageView = view.findViewById(R.id.item_image)

            title.text = movie.title
            rating.text = movie.vote_average.toString()

            // taking care of loading animation
            val circularProgressDrawable = CircularProgressDrawable(view.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            Glide.with(view.context)
                .load(IMAGE_BASE_URL + movie.backdrop_path)
                .placeholder(circularProgressDrawable)
                .into(imageView)

        }

    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(Movies[position])
        holder.cardView.setOnClickListener {
            val context = holder.view.context
            // Create an intent with a destination of MovieActivity
            val intent = Intent(context, MovieActivity::class.java)
            // Send Movie details to MovieActivity
            intent.putExtra("title", Movies[position].original_title)
            intent.putExtra("poster", IMAGE_BASE_URL + Movies[position].backdrop_path)
            intent.putExtra("rating", Movies[position].vote_average.toString())
            intent.putExtra("release date", Movies[position].release_date)
            intent.putExtra("description", Movies[position].overview)
            context.startActivity(intent)
        }



    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount(): Int {
        return Movies.size
    }
}