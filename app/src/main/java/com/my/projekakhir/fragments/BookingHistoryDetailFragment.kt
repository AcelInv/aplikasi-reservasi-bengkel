package com.my.projekakhir.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.my.projekakhir.DetailKendaraanFragment
import com.my.projekakhir.R
import com.my.projekakhir.databinding.FragmentBookingHistoryDetailBinding

class BookingHistoryDetailFragment : Fragment() {

    private var _binding: FragmentBookingHistoryDetailBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookingHistoryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments

        binding.tvNama.text = args?.getString("nama") ?: "-"
        binding.tvHp.text = args?.getString("hp") ?: "-"
        binding.tvMobil.text = args?.getString("mobil") ?: "-"
        binding.tvPlat.text = args?.getString("plat") ?: "-"
        binding.tvLayanan.text = args?.getString("layanan") ?: "-"
        binding.tvTanggal.text = args?.getString("tanggal") ?: "-"
        binding.tvJam.text = args?.getString("jam") ?: "-"
        binding.tvTotal.text = args?.getString("total") ?: "-"

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, detailFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

