package com.example.test.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.test.databinding.ActivityAlbumDetailBinding
import com.example.test.viewmodel.AlbumDetailViewModel

class AlbumDetailActivity : AppCompatActivity() {

    private var _binding: ActivityAlbumDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AlbumDetailViewModel
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
    }
}