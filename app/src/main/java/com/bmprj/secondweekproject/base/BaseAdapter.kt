package com.bmprj.secondweekproject.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<VB: ViewBinding, T:Any>(
    open var list:ArrayList<T> = arrayListOf(),
    private val inflate : (LayoutInflater, ViewGroup, Boolean) -> VB
): RecyclerView.Adapter<BaseViewHolder<VB>>() {

    abstract fun bind(binding: ViewBinding,item:T)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        val binder = inflate(LayoutInflater.from(parent.context),parent,false)

        return BaseViewHolder(binder)
    }

    override fun getItemCount(): Int  = list.size

    override fun onBindViewHolder(holder: BaseViewHolder<VB>, position: Int) {
        bind(holder.binder, list[position])
    }
}