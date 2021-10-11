package com.example.pendofilms.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pendofilms.MainActivity
import com.example.pendofilms.R
import com.example.pendofilms.model.Suggestion



/**
 * Adapter for the [RecyclerView] in [SearchActivity]. Displays [Suggestion] data object.
 */


class SuggestionAdapter(
    private val Suggestions: List<Suggestion>
): RecyclerView.Adapter<SuggestionAdapter.ItemViewHolder>() {

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(sugg: Suggestion){
            val suggView: TextView = view.findViewById(R.id.movie_suggestion)
            suggView.text = sugg.name
        }

    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_suggestion, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(Suggestions[position])
        holder.view.setOnClickListener {
            val context = holder.view.context
            // Create an intent with a destination of MainActivity
            val intent = Intent(context, MainActivity::class.java)
            // Send Suggestion details to MainActivity
            intent.putExtra("suggestion query", Suggestions[position].name)
            context.startActivity(intent)
        }



    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount(): Int {
        return Suggestions.size
    }
}