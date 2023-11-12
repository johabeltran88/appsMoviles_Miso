package com.example.test.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.test.databinding.ActivityCollectorHomeBinding

class CollectorHomeActivity : AppCompatActivity() {

    private var _binding: ActivityCollectorHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCollectorHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set up the buttons
        //Get Albums Activity start
        binding.buttonGetAlbums.setOnClickListener {
            val intent = Intent(this, CollectorListAlbums::class.java)
            startActivity(intent)
        }

        // Add Artist Activity start
        binding.btnAddArtist.setOnClickListener{
            val intent = Intent(this, CollectorAddArtistActivity::class.java)
            startActivity(intent)
        }

        // Add Album Activity start
        binding.btnAddAlbum.setOnClickListener {
            val intent = Intent(this, CollectorAddAlbumActivity::class.java)
            startActivity(intent)
        }
    }

}