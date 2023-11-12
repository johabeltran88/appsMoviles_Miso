package com.example.test.repository

import android.app.Application
import com.android.volley.VolleyError
import com.example.test.model.Collector
import com.example.test.network.NetworkAdapterService

class CollectorRepository(private val application: Application) {

    fun create(
        collector: Collector,
        onComplete: (resp: Collector) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        NetworkAdapterService.getInstance(application)
            .createCollector(collector, onComplete, onError)
    }

}