package com.example.test.ui

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.test.common.changeDateFormat
import com.example.test.databinding.ActivityAlbumDetailBinding
import com.example.test.ui.adapters.CommentAdapter
import com.example.test.viewmodel.AlbumDetailViewModel
import java.net.URL

class AlbumDetailActivity : AppCompatActivity() {

    private var _binding: ActivityAlbumDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AlbumDetailViewModel
    lateinit var commentAdapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAlbumDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(
            this, AlbumDetailViewModel.Factory(this.application)
        )[AlbumDetailViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.btnAddComment.setOnClickListener {
            val intent = Intent(this, AddCommentToAlbumActivity::class.java)
            intent.putExtra("albumId", this.intent.getIntExtra("albumId", 0))
            startActivity(intent)
        }

        commentAdapter = CommentAdapter(emptyList())
        binding.recyclerViewComments.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewComments.adapter = commentAdapter

        viewModel.album.observe(this) { album ->
            binding.name.text = binding.name.text.toString().plus(album.name)
            binding.releaseDate.text =
                binding.releaseDate.text.toString().plus(changeDateFormat(album.releaseDate))
            binding.recordLabel.text = binding.recordLabel.text.toString().plus(album.recordLabel)
            binding.genre.text = binding.genre.text.toString().plus(album.genre)
            binding.description.text = binding.description.text.toString().plus(album.description)
            Glide.with(this)
                .load(album.cover)
                .into(binding.image)
        }

        viewModel.comments.observe(this) { comments ->
            commentAdapter.updateAlbums(comments)
        }

        viewModel.fetchAllComments(this.intent.getIntExtra("albumId", 0))
        viewModel.fetchAlbum(this.intent.getIntExtra("albumId", 0))
    }

    override fun onRestart() {
        super.onRestart()
        viewModel.fetchAllComments(this.intent.getIntExtra("albumId", 0))
    }
}