package com.example.test.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.model.Artist

class ArtistAdapter(var artists: List<Artist>) : RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>() {



    // ViewHolder class that holds references to the UI components for each list item
    class ArtistViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.artist_title)
    }

    // Inflates the item layout and returns a ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_artist, parent, false)
        return ArtistViewHolder(view)
    }

    // Binds data to the views in the ViewHolder
    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val artist = artists[position]
        holder.itemView.setOnClickListener {

        }
        holder.nameTextView.text = artist.name ?: "Unknown"
    }

    fun updateArtists(newArtists: List<Artist>) {
        artists = newArtists
        notifyDataSetChanged() // Notify the adapter to refresh the views
    }

    // Returns the size of the dataset
    override fun getItemCount() = artists.size
}
