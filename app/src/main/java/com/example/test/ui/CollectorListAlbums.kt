package com.example.test.ui

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.test.R
import com.example.test.databinding.ActivityCollectorListAlbumsBinding
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.common.validateDate
import com.example.test.common.validateImage
import com.example.test.common.validateSpinner
import com.example.test.common.validateValue
import com.example.test.databinding.ActivityCollectorAddAlbumBinding
import com.example.test.ui.adapters.AlbumAdapter
import com.example.test.viewmodel.CollectorAddAlbumViewModel
import com.example.test.viewmodel.CollectorListAlbumViewModel

class CollectorListAlbums : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
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


        val navController = findNavController(R.id.nav_host_fragment_content_collector_list_albums)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_collector_list_albums)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}