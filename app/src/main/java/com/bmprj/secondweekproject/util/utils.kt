package com.bmprj.secondweekproject.util

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bmprj.secondweekproject.R

fun ImageView.setDrawable(isActive: Boolean) {
    this.setImageResource(
        if (isActive) {
            R.drawable.icon_star
        } else {
            R.drawable.icon_not_star
        }
    )
}

fun TextView.setVisibility(isEmpty: Boolean) {
    this.visibility = if (isEmpty) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}

fun getDrawableIdFromName(context: Context, imageName: String): Int {
    return context.resources.getIdentifier(imageName, "drawable", context.packageName)
}