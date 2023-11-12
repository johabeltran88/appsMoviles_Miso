package com.example.test.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.model.Album

class AlbumAdapter(private var albums: List<Album>) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {



    // ViewHolder class that holds references to the UI components for each list item
    class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.album_title)
    }

    // Inflates the item layout and returns a ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_album, parent, false)
        return AlbumViewHolder(view)
    }

    // Binds data to the views in the ViewHolder
    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albums[position]
        holder.itemView.setOnClickListener {

        }
        holder.nameTextView.text = album.name ?: "Unknown"
    }

    fun updateAlbums(newAlbums: List<Album>) {
        albums = newAlbums
        notifyDataSetChanged() // Notify the adapter to refresh the views
    }

    // Returns the size of the dataset
    override fun getItemCount() = albums.size
}
