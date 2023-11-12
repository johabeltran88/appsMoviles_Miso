package com.example.test.ui.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.model.Album

class AlbumAdapter(private val albums: List<Album>) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    // ViewHolder class that holds references to the UI components for each list item
    class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.album_name)
        val coverImageView: ImageView = view.findViewById(R.id.album_cover)
        val releaseDateTextView: TextView = view.findViewById(R.id.album_release_date)
        val descriptionTextView: TextView = view.findViewById(R.id.album_description)
        val genreTextView: TextView = view.findViewById(R.id.album_genre)
        val recordLabelTextView: TextView = view.findViewById(R.id.album_record_label)
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
        holder.nameTextView.text = album.name ?: "Unknown"
        holder.releaseDateTextView.text = album.releaseDate ?: "Unknown"
        holder.descriptionTextView.text = album.description ?: "No description available"
        holder.genreTextView.text = album.genre ?: "Unknown"
        holder.recordLabelTextView.text = album.recordLabel ?: "Unknown"


    }

    // Returns the size of the dataset
    override fun getItemCount() = albums.size
}
