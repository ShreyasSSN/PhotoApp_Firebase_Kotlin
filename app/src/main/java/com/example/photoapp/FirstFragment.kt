package com.example.photoapp

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.photoapp.databinding.FragmentFirstBinding
import com.google.firebase.storage.FirebaseStorage
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    lateinit var binding: FragmentFirstBinding
    lateinit var ImageUri: Uri
    lateinit var ImageOg: Uri


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFirstBinding.inflate(inflater, container, false)

        binding.ImageUpload.setOnClickListener {
            selectImage()
        }
        binding.selectImage.setOnClickListener {
            selectImage()
        }
        binding.uploadImage.setOnClickListener {
            uploadImage()
        }

        return binding.root
    }

    @SuppressLint("ResourceType")
    private fun uploadImage() {
        ImageOg = Uri.parse("android.resource://C:/Users/Ingenico.Auto/Desktop/Kotlin/IMPROV_WEEK/PhotoApp/app/src/main/res/drawable/images.png")
        val formatter = SimpleDateFormat("YYYY_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val imageName = formatter.format(now)
        val storageReference = FirebaseStorage.getInstance().getReference("Images/$imageName")
        storageReference.putFile(ImageUri).
                addOnSuccessListener {
                    Toast.makeText(context, "Uploaded Successfully", Toast.LENGTH_LONG).show()
                    binding.ImageUpload.setImageURI(ImageOg)

                }.addOnFailureListener {
                    Toast.makeText(context, "Upload Failed", Toast.LENGTH_LONG).show()
                    binding.ImageUpload.setImageURI(ImageUri)
        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == 100 || resultCode == RESULT_OK){

            ImageUri = data?.data!!
            binding.ImageUpload.setImageURI(ImageUri)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.DownloadButton.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }
}

