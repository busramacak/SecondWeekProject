package com.bmprj.secondweekproject.util

import android.content.Context
import android.widget.ImageView
import com.bmprj.secondweekproject.R

fun ImageView.setDrawable(isActive:Boolean){
    this.setImageResource(
        if(isActive){
            R.drawable.icon_star
        }else{
            R.drawable.icon_not_star
        }
    )
}

fun getDrawableIdFromName(context: Context, imageName: String): Int {
    return context.resources.getIdentifier(imageName, "drawable", context.packageName)
}