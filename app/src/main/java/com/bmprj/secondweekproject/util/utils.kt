package com.bmprj.secondweekproject.util

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