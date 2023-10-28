package com.example.test.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

        binding.btnAddArtist.setOnClickListener{
            val intent = Intent(this, CollectorAddArtistActivity::class.java)
            startActivity(intent)
        }
    }

}