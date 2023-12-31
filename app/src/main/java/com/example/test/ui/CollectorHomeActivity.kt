package com.example.test.ui

import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.test.R
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

        val upArrow: Drawable? = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
        upArrow?.setColorFilter(resources.getColor(R.color.your_color), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)


        // Set up the buttons
        //Get Albums Activity start
        binding.buttonGetAlbums.setOnClickListener {
            startActivity(Intent(this, CollectorListAlbums::class.java))
        }

        // Add Artist Activity start
        binding.btnAddArtist.setOnClickListener {
            startActivity(Intent(this, CollectorAddArtistActivity::class.java))
        }

        // Add Album Activity start
        binding.btnAddAlbum.setOnClickListener {
            startActivity(Intent(this, CollectorAddAlbumActivity::class.java))
        }
    }

}