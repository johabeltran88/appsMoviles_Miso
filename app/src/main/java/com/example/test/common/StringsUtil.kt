package com.example.test.common

import java.text.SimpleDateFormat
import java.util.Locale

fun changeDateFormat(dateString: String?): String? {
    try {
        val formatIn = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val date = formatIn.parse(dateString)
        val formatOut = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        return formatOut.format(date)
    }
    catch (ex: Exception) {
        return dateString
    }
}