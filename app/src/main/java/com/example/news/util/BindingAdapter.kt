package com.example.news.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.news.R
import java.text.SimpleDateFormat

@BindingAdapter("date")
fun TextView.formatDate(dateStr: String?) {
    dateStr?:return
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'")
    val date = sdf.parse(dateStr)
    sdf.applyPattern("E, dd MMM YYYY hh:mm a")
    text  = sdf.format(date)
}

@BindingAdapter("imageURL")
fun ImageView.loadImage(url: String) {
    Glide
        .with(this)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.ic_broken_image)
        .into(this)


}
