package com.example.shopflowmainadminapp.Fragement

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.shopflowmainadminapp.R
import com.example.shopflowmainadminapp.databinding.FragmentSliderFragementBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class SliderFragement : Fragment() {

    private lateinit var dialog:Dialog
    private var imageUrl:Uri?=null
    lateinit var binding: FragmentSliderFragementBinding
    private  var launchGallryActivity=registerForActivityResult(
        ActivityResultContracts.StartActivityForResult())
    {
        if(it.resultCode==Activity.RESULT_OK)
        {
            imageUrl= it.data?.data
            binding.imgUploadSliderImg.setImageURI(imageUrl)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding=FragmentSliderFragementBinding.inflate(layoutInflater)
        applyClickListerToViews();

        dialog=Dialog(requireContext())
        dialog.setContentView(R.layout.progress_layout);
        dialog.setCancelable(false)

        return binding.root

    }

    private fun applyClickListerToViews() {
//        select image from Gallary

        binding.apply {
            binding.imgUploadSliderImg.setOnClickListener{
                val intent=Intent("android.intent.action.GET_CONTENT")
                intent.type="image/*"
                launchGallryActivity.launch(intent)

            }


//        Upload Image to the Slider
            binding.btnSendImageToSlider.setOnClickListener{
                if(imageUrl!=null)
                {
                    uploadImage(imageUrl!!)
                    Log.d("Error Status","btn Click ")
                }
                else{
                    Toast.makeText(requireContext(),"Please Upload Image",Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun uploadImage(uri: Uri) {

        dialog.show()
        Log.d("Error Status","Upload Image Methode Call")


        val filename=UUID.randomUUID().toString()+".jpg"

        val refStorage=FirebaseStorage.getInstance().reference.child("SliderImage/$filename")
        refStorage.putFile(uri)
            .addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener { image->
                    storeData(image.toString())
                    Log.d("Error Status","On Success called")
                    dialog.dismiss()
                }
            }
            .addOnFailureListener{
                Log.d("Error Status","On Failure Called ")
                dialog.dismiss()

            }
    }

    private fun storeData(image: String) {
        dialog.dismiss()
        Log.d("Error Status","Stored Data Called ")

        val db=Firebase.firestore

        val data= hashMapOf<String,Any>(
            "slider_image" to image
        )
        db.collection("slider").document("item").set(data)
            .addOnSuccessListener {
                Toast.makeText(requireContext(),"Slider Saved To Server",Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                Log.d("Error Status","On Stored Data OnSuccess Called ")

            }
            .addOnSuccessListener {
                //Toast.makeText(requireContext(),"Something went Wrong",Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                Log.d("Error Status","On Stored Data OnFailure Called ")

            }
    }
}