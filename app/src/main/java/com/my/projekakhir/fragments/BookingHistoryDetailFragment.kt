package com.my.projekakhir.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

        binding.tvNama.text = arguments?.getString("nama")
        binding.tvHp.text = arguments?.getString("hp")
        binding.tvMobil.text = arguments?.getString("mobil")
        binding.tvPlat.text = arguments?.getString("plat")
        binding.tvLayanan.text = arguments?.getString("layanan")
        binding.tvTanggal.text = arguments?.getString("tanggal")
        binding.tvJam.text = arguments?.getString("jam")
        binding.tvTotal.text = arguments?.getString("total")
        binding.tvCatatan.text =
            arguments?.getString("catatan")?.ifEmpty { "-" }

        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}