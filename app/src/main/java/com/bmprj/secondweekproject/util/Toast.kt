package com.bmprj.secondweekproject.util

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment


fun Fragment.toast(message:String) = toastt(requireContext(),message)
fun Fragment.toastLong(message: String) = toastt(requireContext(),message, Toast.LENGTH_LONG)



private fun toastt(context: Context,message: String,duration:Int = Toast.LENGTH_SHORT) = Toast.makeText(context,message,duration).show()

