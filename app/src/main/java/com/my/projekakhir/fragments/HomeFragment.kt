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
import com.my.projekakhir.MainActivity
import com.my.projekakhir.R
import com.my.projekakhir.adapters.ServiceAdapter
import com.my.projekakhir.adapters.ServiceCategoryAdapter
import com.my.projekakhir.databinding.FragmentHomeBinding
import com.my.projekakhir.models.Service
import com.my.projekakhir.models.ServiceCategory

class HomeFragment : Fragment() {

    private lateinit var categoryAdapter: ServiceCategoryAdapter
    private lateinit var categoryList: List<ServiceCategory>
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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

        setupRecyclerView()
        setupMainButtons()
        setupCategory()
        setupSearch()
    }

    private fun setupRecyclerView() {
        val services = listOf(
            Service(
                id = 1,
                name = "Ganti Oli Mesin",
                status = "Perlu Servis",
                price = "Rp 150.000",
                lastService = "3 bulan lalu",
                statusColor = R.color.red_100,
                statusTextColor = R.color.red_700
            ),
            Service(
                id = 2,
                name = "Cek Rem",
                status = "Disarankan",
                price = "Rp 200.000",
                lastService = "5 bulan lalu",
                statusColor = R.color.orange_100,
                statusTextColor = R.color.orange_700
            ),
            Service(
                id = 3,
                name = "Tune Up",
                status = "Disarankan",
                price = "Rp 350.000",
                lastService = "6 bulan lalu",
                statusColor = R.color.orange_100,
                statusTextColor = R.color.orange_700
            )
        )

        serviceAdapter = ServiceAdapter(services) { service ->
            val serviceName = when (service.name) {
                "Ganti Oli Mesin" -> "Ganti Oli"
                "Cek Rem" -> "Servis Rem"
                "Tune Up" -> "Tune Up"
                else -> null
            }
            navigateToBooking(serviceName)
        }

        binding.recommendedServicesRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = serviceAdapter
        }
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