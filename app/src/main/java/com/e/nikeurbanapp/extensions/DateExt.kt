package com.e.nikeurbanapp.extensions

import java.text.SimpleDateFormat
import java.util.*

const val DEFAULT_INPUT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
const val DEFAULT_OUTPUT_FORMAT = "MM-dd-yyyy"

fun String.formatDate(
    inputFormat: String = DEFAULT_INPUT_FORMAT,
    outputFormat: String = DEFAULT_OUTPUT_FORMAT
): String =
    SimpleDateFormat(inputFormat, Locale.getDefault()).parse(this)?.let {
        SimpleDateFormat(outputFormat, Locale.getDefault()).format(it)
    } ?: this