package com.example.test.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.buttonCollector).setOnClickListener {
            startActivity(Intent(this, CollectorHomeActivity::class.java))
        }

        findViewById<Button>(R.id.buttonVisitor).setOnClickListener {
            startActivity(Intent(this, VisitorHomeActivity::class.java))
        }
    }

}