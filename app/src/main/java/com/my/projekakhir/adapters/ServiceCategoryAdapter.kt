package com.my.projekakhir.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.my.projekakhir.R
import com.my.projekakhir.models.ServiceCategory

class ServiceCategoryAdapter(
    private var list: List<ServiceCategory>,
    private val onClick: (ServiceCategory) -> Unit
) : RecyclerView.Adapter<ServiceCategoryAdapter.ViewHolder>() {

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: ServiceCategory) {
            view.findViewById<TextView>(R.id.tvName).text = item.name
            view.findViewById<ImageView>(R.id.ivIcon).setImageResource(item.icon)
            view.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_service_category, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    fun updateData(newList: List<ServiceCategory>) {
        list = newList
        notifyDataSetChanged()
    }
}