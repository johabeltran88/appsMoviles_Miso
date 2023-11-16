package com.example.test.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.databinding.ActivityVisitorListArtistBinding
import com.example.test.ui.adapters.ArtistAdapter
import com.example.test.viewmodel.VisitorListArtistViewModel

class VisitorListArtistActivity : AppCompatActivity() {

    private var _binding: ActivityVisitorListArtistBinding? = null
    lateinit var artistAdapter: ArtistAdapter // Declare the adapter
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityVisitorListArtistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize the adapter with an empty list
        artistAdapter = ArtistAdapter(emptyList()) // Initialize with an empty list

        // Setup RecyclerView with the LayoutManager and the Adapter
        binding.recyclerViewArtist.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewArtist.adapter = artistAdapter // Set the adapter

        // Initialize your ViewModel here (assuming you have a ViewModel set up)
        val viewModel = ViewModelProvider(this).get(VisitorListArtistViewModel::class.java)
        // Observe the album data from the ViewModel
        viewModel.artists.observe(this, Observer { artists ->
            // When album data changes, update the adapter's dataset
            artistAdapter.updateArtists(artists)
        })

        // Fetch the albums
        viewModel.fetchAllArtists()
    }
}