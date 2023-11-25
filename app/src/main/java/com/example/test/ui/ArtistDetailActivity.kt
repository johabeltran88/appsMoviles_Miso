package com.example.test.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.test.common.changeDateFormat
import com.example.test.databinding.ActivityArtistDetailBinding
import com.example.test.ui.adapters.AlbumAdapter
import com.example.test.viewmodel.ArtistDetailViewModel

class ArtistDetailActivity : AppCompatActivity() {

    private var _binding: ActivityArtistDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ArtistDetailViewModel
    private lateinit var albumAdapter: AlbumAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityArtistDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(
            this, ArtistDetailViewModel.Factory(this.application)
        )[ArtistDetailViewModel::class.java]

        albumAdapter = AlbumAdapter(emptyList())
        binding.recyclerViewAlbums.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewAlbums.adapter = albumAdapter

        viewModel.artist.observe(this) { artist ->
            binding.name.text = binding.name.text.toString().plus(artist.name)
            binding.birthDate.text =
                binding.birthDate.text.toString().plus(changeDateFormat(artist.birthDate))
            binding.description.text = binding.description.text.toString().plus(artist.description)
            Glide.with(this)
                .load(artist.image)
                .into(binding.image)
        }

        viewModel.albums.observe(this) { albums ->
            albumAdapter.updateAlbums(albums)
        }

        viewModel.fetchAllAlbums(this.intent.getIntExtra("artistId", 0))
        viewModel.fetchArtist(this.intent.getIntExtra("artistId", 0))

    }

    override fun onRestart() {
        super.onRestart()
        viewModel.fetchAllAlbums(this.intent.getIntExtra("artistId", 0))
    }
}