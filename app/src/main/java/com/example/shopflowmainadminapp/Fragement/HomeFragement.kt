package com.example.shopflowmainadminapp.Fragement

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.shopflowmainadminapp.Activity.AllOrderActivity
import com.example.shopflowmainadminapp.R
import com.example.shopflowmainadminapp.databinding.FragmentHomeFragementBinding

class HomeFragement : Fragment() {

    private lateinit var binding: FragmentHomeFragementBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding= FragmentHomeFragementBinding.inflate(layoutInflater)
        clickListenerMethod();
        return binding.root

    }

    private fun clickListenerMethod() {
//        from HomeFragement to Category Fragement
        binding.btnHomeFragementAddCategory.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragement_to_categoryFragement)

        }

//        from HomeFragement To Product Fragement
        binding.btnHomeFragementProduct.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragement_to_productFragement)
        }

//        from HomeFragemnt to SliderFragement
        binding.btnHomeFragementSlider.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragement_to_sliderFragement)

        }

//        from HomeFragement to AllOrderActivity
        binding.btnHomeFragementAllOderDetails.setOnClickListener{
            startActivity(Intent(requireContext(),AllOrderActivity::class.java))

        }

    }

}