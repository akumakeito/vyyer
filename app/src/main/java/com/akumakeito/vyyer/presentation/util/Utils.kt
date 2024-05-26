package com.akumakeito.vyyer.presentation.util

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Locale

fun showToast(context: Context, message: String) {
    Toast.makeText(
        context,
        message,
        Toast.LENGTH_SHORT
    )
        .show()

}

fun EditText.addTextChangedListener(onTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            onTextChanged(s.toString())
        }
    })
}

fun convertDateFormat(dateString : String) : String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd.MM.yyyy hh:mm a", Locale.getDefault())
    val date = inputFormat.parse(dateString) ?: return dateString
    return outputFormat.format(date)
}
