package com.example.test.webservice

import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.test.common.postRequest
import com.example.test.model.Collector
import com.google.gson.Gson
import org.json.JSONObject

class CollectorWebService {

    companion object {
        const val RESOURCE = "/collectors"
    }

    fun create(
        collector: Collector,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ): JsonObjectRequest {
        return postRequest(RESOURCE, toJSONObject(collector), responseListener, errorListener)
    }

    private fun toJSONObject(collector: Collector): JSONObject {
        val gson = Gson()
        val jsonString = gson.toJson(collector)
        return JSONObject(jsonString)
    }

}