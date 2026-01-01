package com.my.projekakhir.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.my.projekakhir.databinding.ItemBookingBinding
import com.my.projekakhir.models.Booking

class BookingAdapter(
    private val onClick: (Booking) -> Unit
) : RecyclerView.Adapter<BookingAdapter.ViewHolder>() {

    private val items = mutableListOf<Booking>()

    fun setData(data: List<Booking>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemBookingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Booking) {
            binding.tvLayanan.text = item.layanan
            binding.tvTanggal.text = "${item.tanggal} • ${item.jam}"
            binding.tvTotal.text = item.total

            binding.root.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemBookingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount() = items.size
}