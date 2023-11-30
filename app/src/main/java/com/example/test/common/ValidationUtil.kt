package com.example.test.common

import android.content.Context
import android.util.Patterns
import java.text.SimpleDateFormat
import java.util.Locale
import com.example.test.R

fun validateValue(field: String?, maxLength: Int, context: Context): String {
    if (field.isNullOrBlank())
        return context.getString(R.string.error1)
    if (field.length > maxLength)
        return context.getString(R.string.error2, maxLength)
    return ""
}

fun validateImage(field: String?, context: Context): String {
    if (field.isNullOrBlank())
        return context.getString(R.string.error1)
    if (!isURLValid(field))
        return context.getString(R.string.error4)
    return ""
}

fun validateDate(field: String?, context: Context): String {
    if (field.isNullOrBlank())
        return context.getString(R.string.error1)
    if (!isDateValid(field))
        return context.getString(R.string.error3)
    return ""
}

fun validateSpinner(field: String?, placeHolder: String, context: Context): String {
    if (field.isNullOrBlank() || field == placeHolder)
        return context.getString(R.string.error5)
    return ""
}

fun validateEmail(field: String?, context: Context): String {
    if (field.isNullOrBlank()) {
        return context.getString(R.string.error1)
    } else {
        // Expresión regular para validar un correo electrónico
        val emailRegex = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")

        // Verificar si el campo coincide con la expresión regular
        if (!emailRegex.matches(field))
            return context.getString(R.string.error6)
        return ""
    }
}

fun isURLValid(url: String?): Boolean {
    return Patterns.WEB_URL.matcher(url).matches()
}

fun isDateValid(date: String?): Boolean {
    val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    dateFormat.isLenient = false
    return try {
        val date = dateFormat.parse(date)
        date != null
    } catch (e: Exception) {
        false
    }
}