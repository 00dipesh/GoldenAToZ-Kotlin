package com.goldendigitech.goldenatoz.Util

import java.text.SimpleDateFormat
import java.util.Locale

class FormatterUtil {

    fun formatDate(dateString: String?): String {
        return try {
            dateString?.let {
                val inputFormat = SimpleDateFormat("M/d/yyyy h:mm:ss a", Locale.getDefault())
                val outputFormat = SimpleDateFormat("M/d/yyyy", Locale.getDefault())
                val date = inputFormat.parse(it)
                outputFormat.format(date)
            } ?: "N/A"
        } catch (e: Exception) {
            e.printStackTrace()
            "N/A"
        }
    }

    fun formatFullName(firstName: String?, middleName: String?, lastName: String?): String {
        return "${firstName.orEmpty()} ${middleName.orEmpty()} ${lastName.orEmpty()}".trim()
    }


    object StringUtils {
        fun defaultIfNull(value: String?): String {
            return value ?: "N/A"
        }
    }
}