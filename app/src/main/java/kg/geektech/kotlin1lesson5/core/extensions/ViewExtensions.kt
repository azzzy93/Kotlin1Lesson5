package kg.geektech.kotlin1lesson5.core.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.load(url: String) {
    Glide.with(this).load(url).into(this)
}