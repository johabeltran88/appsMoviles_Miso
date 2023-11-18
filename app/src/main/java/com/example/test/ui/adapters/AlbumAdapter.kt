package com.example.test.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.calculateDiff
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.model.Album

class AlbumAdapter(var albums: List<Album>) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {


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
            onItemClick(position)
        }
        holder.nameTextView.text = album.name ?: "Unknown"
    }

    private var onItemClickListener: ((Int) -> Unit)? = null

    private fun onItemClick(position: Int) {
        onItemClickListener?.invoke(position)
    }

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    fun updateAlbums(newAlbums: List<Album>) {
        val diffResult = calculateDiff(DiffCallback(albums, newAlbums))
        albums = newAlbums
        diffResult.dispatchUpdatesTo(this)
    }

    // Returns the size of the dataset
    override fun getItemCount() = albums.size

    private class DiffCallback(
        private val oldAlbums: List<Album>,
        private val newAlbums: List<Album>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldAlbums.size
        override fun getNewListSize() = newAlbums.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldAlbums[oldItemPosition].id == newAlbums[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldAlbums[oldItemPosition] == newAlbums[newItemPosition]
        }
    }

}
