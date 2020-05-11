package com.e.nikeurbanapp.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

fun <T: ViewDataBinding>
        ViewGroup.bind(layoutId: Int, attachToParent: Boolean = false): T =
    DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, this, attachToParent)

@BindingAdapter("showView")
fun View.toggleVisiblity(show: Boolean){
    visibility = when (show) {
        true -> View.VISIBLE
        false -> View.GONE
        else -> View.INVISIBLE
    }
}