package kg.geektech.kotlin1lesson5.extensions

import android.content.Context
import android.widget.Toast

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}