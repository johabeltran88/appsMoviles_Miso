package com.example.test.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.example.test.databinding.ActivityCollectorListAlbumsBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.ui.adapters.AlbumAdapter
import com.example.test.viewmodel.CollectorListAlbumViewModel

class CollectorListAlbums : AppCompatActivity() {

    private lateinit var binding: ActivityCollectorListAlbumsBinding
    private lateinit var albumAdapter: AlbumAdapter // Declare the adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCollectorListAlbumsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        // Initialize the adapter with an empty list
        albumAdapter = AlbumAdapter(emptyList()) // Initialize with an empty list

        // Setup RecyclerView with the LayoutManager and the Adapter
        binding.recyclerViewAlbums.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewAlbums.adapter = albumAdapter // Set the adapter

        // Initialize your ViewModel here (assuming you have a ViewModel set up)
        val viewModel = ViewModelProvider(this).get(CollectorListAlbumViewModel::class.java)
        // Observe the album data from the ViewModel
        viewModel.albums.observe(this, Observer { albums ->
            // When album data changes, update the adapter's dataset
            albumAdapter.updateAlbums(albums)
        })

        // Fetch the albums
        viewModel.fetchAllAlbums()




    }

}