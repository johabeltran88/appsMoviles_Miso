package com.example.test.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.databinding.ActivityCollectorListAlbumsBinding
import com.example.test.ui.adapters.AlbumAdapter
import com.example.test.viewmodel.CollectorListAlbumViewModel

class CollectorListAlbums : AppCompatActivity() {

    private lateinit var binding: ActivityCollectorListAlbumsBinding
    lateinit var albumAdapter: AlbumAdapter // Declare the adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCollectorListAlbumsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize the adapter with an empty list
        albumAdapter = AlbumAdapter(emptyList()) // Initialize with an empty list


        // Setup RecyclerView with the LayoutManager and the Adapter
        binding.recyclerViewAlbums.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewAlbums.adapter = albumAdapter // Set the adapter

        // Initialize your ViewModel here (assuming you have a ViewModel set up)
        val viewModel = ViewModelProvider(
            this, CollectorListAlbumViewModel.Factory(this.application)
        )[CollectorListAlbumViewModel::class.java]
        // Observe the album data from the ViewModel
        viewModel.albums.observe(this) { albums ->
            // When album data changes, update the adapter's dataset
            albumAdapter.updateAlbums(albums)
        }

        albumAdapter.setOnItemClickListener {
            viewModel.albumId.value = viewModel.albums.value?.get(it)?.id
        }

        viewModel.albumId.observe(this) {
            val intent = Intent(this, AlbumDetailActivity::class.java)
            intent.putExtra("albumId", it)
            startActivity(intent)
        }

        // Fetch the albums
        viewModel.fetchAllAlbums()


    }

}