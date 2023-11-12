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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.common.validateDate
import com.example.test.common.validateImage
import com.example.test.common.validateSpinner
import com.example.test.common.validateValue
import com.example.test.databinding.ActivityCollectorAddAlbumBinding
import com.example.test.ui.adapters.AlbumAdapter
import com.example.test.viewmodel.CollectorAddAlbumViewModel

class CollectorListAlbums : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityCollectorListAlbumsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCollectorListAlbumsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        // Setup RecyclerView with the LayoutManager and the Adapter
        binding.recyclerViewAlbums.layoutManager = LinearLayoutManager(this)
        // Assuming AlbumAdapter is your RecyclerView adapter
        binding.recyclerViewAlbums.adapter = AlbumAdapter()

        val navController = findNavController(R.id.nav_host_fragment_content_collector_list_albums)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_collector_list_albums)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}