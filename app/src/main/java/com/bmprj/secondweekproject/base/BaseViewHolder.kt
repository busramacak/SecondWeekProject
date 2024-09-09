package com.bmprj.secondweekproject.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class BaseViewHolder<VB: ViewBinding>(val binder:VB) : RecyclerView.ViewHolder(binder.root) {
}