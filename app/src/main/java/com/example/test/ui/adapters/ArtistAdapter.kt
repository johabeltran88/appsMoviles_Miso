package com.example.test.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.model.Artist

class ArtistAdapter(var artists: List<Artist>) :
    RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>() {


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
            onItemClick(position)
        }
        holder.nameTextView.text = artist.name ?: "Unknown"
    }

    private var onItemClickListener: ((Int) -> Unit)? = null

    private fun onItemClick(position: Int) {
        onItemClickListener?.invoke(position)
    }

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateArtists(newArtists: List<Artist>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallback(artists, newArtists))
        artists = newArtists
        diffResult.dispatchUpdatesTo(this)
    }

    // Returns the size of the dataset
    override fun getItemCount() = artists.size

    private class DiffCallback(
        private val oldArtist: List<Artist>,
        private val newArtist: List<Artist>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldArtist.size
        override fun getNewListSize() = newArtist.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldArtist[oldItemPosition].id == newArtist[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldArtist[oldItemPosition] == newArtist[newItemPosition]
        }
    }

}
