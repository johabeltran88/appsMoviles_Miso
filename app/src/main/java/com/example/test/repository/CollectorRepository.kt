package com.example.test.repository

import android.app.Application
import com.example.test.model.Collector
import com.example.test.network.NetworkAdapterService

class CollectorRepository(private val application: Application) {

    suspend fun create(collector: Collector): Collector {
        return NetworkAdapterService.getInstance(application).createCollector(collector)
    }

}