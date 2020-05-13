package com.e.nikeurbanapp.extensions

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("dateDisplay")
fun TextView.dateDisplay(date: String) {
    text = date.formatDate()
}