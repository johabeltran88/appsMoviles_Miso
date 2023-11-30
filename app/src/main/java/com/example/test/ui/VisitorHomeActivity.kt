package com.example.test.ui

import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.test.R
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

        val upArrow: Drawable? = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
        upArrow?.setColorFilter(resources.getColor(R.color.your_color), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)

        binding.btnListArtist.setOnClickListener{
            startActivity(Intent(this, VisitorListArtistActivity::class.java))
        }

        //Get Albums Activity start
        binding.btnListAlbum.setOnClickListener {
            startActivity(Intent(this, VisitorListAlbumsActivity::class.java))
        }
    }
}