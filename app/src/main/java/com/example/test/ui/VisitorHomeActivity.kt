package com.example.test.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test.databinding.ActivityVisitorHomeBinding

class VisitorHomeActivity : AppCompatActivity() {
    private var _binding: ActivityVisitorHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityVisitorHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnListArtist.setOnClickListener{
            startActivity(Intent(this, VisitorListArtistActivity::class.java))
        }

        //Get Albums Activity start
        binding.btnListAlbum.setOnClickListener {
            startActivity(Intent(this, CollectorListAlbums::class.java))
        }
    }
}