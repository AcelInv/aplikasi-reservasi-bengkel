package com.my.projekakhir.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.my.projekakhir.R
import com.my.projekakhir.databinding.FragmentAlamatTersimpanBinding

class AlamatTersimpanFragment : Fragment() {

    private var _binding: FragmentAlamatTersimpanBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlamatTersimpanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnTambahAlamat.setOnClickListener {
            showTambahAlamatDialog()
        }
    }

    private fun showTambahAlamatDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_tambah_alamat, null)

        val etLabel = dialogView.findViewById<EditText>(R.id.etLabel)
        val etAlamat = dialogView.findViewById<EditText>(R.id.etAlamat)

        AlertDialog.Builder(requireContext())
            .setTitle("Tambah Alamat")
            .setView(dialogView)
            .setPositiveButton("Simpan") { _, _ ->
                val label = etLabel.text.toString()
                val alamat = etAlamat.text.toString()

                if (label.isNotEmpty() && alamat.isNotEmpty()) {
                    tambahAlamatKeLayout(label, alamat)
                } else {
                    Toast.makeText(requireContext(), "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun tambahAlamatKeLayout(label: String, alamat: String) {
        val tvLabel = TextView(requireContext()).apply {
            text = "🏠 $label"
            textSize = 16f
            setTypeface(null, android.graphics.Typeface.BOLD)
        }

        val tvAlamat = TextView(requireContext()).apply {
            text = alamat
            setPadding(0, 4, 0, 16)
        }

        binding.layoutAlamat.addView(tvLabel)
        binding.layoutAlamat.addView(tvAlamat)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
