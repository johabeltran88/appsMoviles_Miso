package com.example.test.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
        val viewModel = ViewModelProvider(
            this, VisitorListArtistViewModel.Factory(this.application)
        )[VisitorListArtistViewModel::class.java]
        // Observe the album data from the ViewModel
        viewModel.artists.observe(this) { artists ->
            // When album data changes, update the adapter's dataset
            artistAdapter.updateArtists(artists)
        }

        artistAdapter.setOnItemClickListener {
            viewModel.artistId.value = viewModel.artists.value?.get(it)?.id
        }

        viewModel.artistId.observe(this) {
            val intent = Intent(this, ArtistDetailActivity::class.java)
            intent.putExtra("artistId", it)
            startActivity(intent)
        }

        // Fetch the albums
        viewModel.fetchAllArtists()
    }
}