package com.example.test.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.model.Comment

class CommentAdapter(var comments: List<Comment>) :
    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val comment: TextView = view.findViewById(R.id.comment_description)
    }

    // Inflates the item layout and returns a ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.comment.text =
            comments[position].description.plus(" - ").plus(comments[position].rating)
    }

    fun updateAlbums(newComments: List<Comment>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallback(comments, newComments))
        comments = newComments
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = comments.size

    private class DiffCallback(
        private val oldComments: List<Comment>,
        private val newComments: List<Comment>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldComments.size
        override fun getNewListSize() = newComments.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldComments[oldItemPosition].id == newComments[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldComments[oldItemPosition] == newComments[newItemPosition]
        }
    }
}