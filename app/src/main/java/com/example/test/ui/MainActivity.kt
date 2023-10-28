package com.example.test.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goToCollectorHome(view: View) {
        val intent = Intent(this, CollectorHomeActivity::class.java)
        startActivity(intent)
    }

    fun goToVisitorHome(view: View) {
    }

}