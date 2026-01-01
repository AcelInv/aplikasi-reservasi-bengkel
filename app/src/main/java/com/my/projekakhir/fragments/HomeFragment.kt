package com.my.projekakhir.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.my.projekakhir.MainActivity
import com.my.projekakhir.R
import com.my.projekakhir.adapters.BookingAdapter
import com.my.projekakhir.adapters.ServiceAdapter
import com.my.projekakhir.adapters.ServiceCategoryAdapter
import com.my.projekakhir.databinding.FragmentHomeBinding
import com.my.projekakhir.models.Booking
import com.my.projekakhir.models.Service
import com.my.projekakhir.models.ServiceCategory

class HomeFragment : Fragment() {

    private lateinit var categoryAdapter: ServiceCategoryAdapter
    private lateinit var categoryList: List<ServiceCategory>
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var bookingAdapter: BookingAdapter
    private val bookingRef =
        FirebaseDatabase.getInstance().getReference("bookings")


    private lateinit var serviceAdapter: ServiceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMainButtons()
        setupCategory()
        setupSearch()
        setupBookingHistory()
    }

    private fun setupMainButtons() {
        binding.btnBookingNow.setOnClickListener {
            navigateToBooking(null)
        }
    }

    private fun setupCategory() {
        categoryList = listOf(
            ServiceCategory("Ganti Oli", R.drawable.ic_wrench),
            ServiceCategory("Servis Ringan", R.drawable.ic_car),
            ServiceCategory("Servis Berkala", R.drawable.ic_calendar),
            ServiceCategory("Servis Rem", R.drawable.ic_calendar),
            ServiceCategory("Tune Up", R.drawable.ic_wrench),
            ServiceCategory("Tambal Ban", R.drawable.ic_car)
        )

        categoryAdapter = ServiceCategoryAdapter(categoryList) { category ->
            navigateToBooking(category.name)
        }

        binding.rvServiceCategory.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = categoryAdapter
        }
    }

    private fun setupSearch() {
        binding.searchService.addTextChangedListener {
            val keyword = it.toString().lowercase()

            val filtered = categoryList.filter { item ->
                item.name.lowercase().contains(keyword)
            }

            categoryAdapter.updateData(filtered)
        }
    }

    private fun openDetailBooking(booking: Booking) {
        val fragment = BookingHistoryDetailFragment().apply {
            arguments = Bundle().apply {
                putString("nama", booking.nama)
                putString("hp", booking.hp)
                putString("mobil", booking.mobil)
                putString("plat", booking.plat)
                putString("layanan", booking.layanan)
                putString("total", booking.total)
                putString("tanggal", booking.tanggal)
                putString("jam", booking.jam)
                putString("catatan", booking.catatan)
            }
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupBookingHistory() {
        bookingAdapter = BookingAdapter { booking ->
            openDetailBooking(booking)
        }

        binding.rvBookingHistory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = bookingAdapter
        }

        bookingRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<Booking>()

                snapshot.children.forEach {
                    val booking = it.getValue(Booking::class.java)
                    booking?.let { list.add(it) }
                }

                bookingAdapter.setData(list.reversed())
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun navigateToBooking(serviceName: String?) {
        val fragment = BookingFragment().apply {
            arguments = Bundle().apply {
                putString("SERVICE_NAME", serviceName)
            }
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()

        (activity as? MainActivity)?.hideBottomNav()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}