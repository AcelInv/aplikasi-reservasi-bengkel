package com.my.projekakhir.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.my.projekakhir.databinding.FragmentAddCarBinding
import com.my.projekakhir.models.Car
import java.util.*



class AddCarFragment : Fragment() {

    private var _binding: FragmentAddCarBinding? = null
    private val binding get() = _binding!!

    private var imageUri: Uri? = null
    private val PICK_IMAGE_REQUEST = 100

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddCarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tombol back
        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Pilih gambar
        binding.imgCar.setOnClickListener { openGallery() }

        // Simpan mobil
        binding.btnSaveCar.setOnClickListener {
            if (imageUri != null) {
                // Ada foto → upload dulu
                uploadImageToFirebase(imageUri!!)
            } else {
                // Tidak ada foto → tetap simpan mobil
                saveCarToDatabase("")
            }
        }

    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            binding.imgCar.setImageURI(imageUri)
        }
    }

    private fun uploadImageToFirebase(uri: Uri) {
        val storageRef = FirebaseStorage.getInstance().reference.child("car_images/${UUID.randomUUID()}")
        val uploadTask = storageRef.putFile(uri)
        uploadTask.addOnSuccessListener {
            val addOnSuccessListener = storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                saveCarToDatabase(downloadUri.toString())
            }
            addOnSuccessListener
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Gagal upload gambar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveCarToDatabase(imageUrl: String) {
        val car = Car(
            brand = binding.etBrand.text.toString(),
            model = binding.etModel.text.toString(),
            year = binding.etYear.text.toString(),
            plateNumber = binding.etPlate.text.toString(),
            imageName = imageUrl
        )

        val ref = FirebaseDatabase.getInstance("https://pmob-d4529-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("cars")

        ref.push().setValue(car)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Mobil ditambahkan", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Gagal menambah mobil", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
