package com.example.test.common

import android.util.Patterns
import java.text.SimpleDateFormat
import java.util.Locale

fun validateDate(field: String?): String {
    if (field.isNullOrBlank())
        return "El campo es requerido y no debe estar vacio"
    if (!isDateValid(field))
        return "El formato de la fecha ingresada debe ser mm/dd/aaaa"
    return ""
}

fun validateSpinner(field: String?, placeHolder: String): String {
    if (field.isNullOrBlank() || field == placeHolder)
        return "Debe seleccionar un elemento de la lista"
    return ""
}

fun validateValue(field: String?, maxLength: Int): String {
    if (field.isNullOrBlank())
        return "El campo es requerido y no debe estar vacio"
    if (field.length > maxLength)
        return "El campo es de maximo de $maxLength caracteres"
    return ""
}

fun validateEmailValue(field: String?): String {
    if (!isValidEmail(field))
        return "Verifique el correo electronico"
    return ""
}

fun validateImage(field: String?): String {
    if (field.isNullOrBlank())
        return "La imagen es requerida y no debe estar vacia"
    if (!isURLValid(field))
        return "La url ingresada no es valida"
    return ""
}

fun isURLValid(url: String?): Boolean {
    return Patterns.WEB_URL.matcher(url).matches()
}

fun isValidEmail(email: String?): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
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