package com.sahraer.mytravelbook.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sahraer.mytravelbook.R
import com.sahraer.mytravelbook.databinding.FragmentOnboarding3Binding
import com.sahraer.mytravelbook.view.MainActivity

class Onboarding3Fragment : Fragment() {

     var _binding: FragmentOnboarding3Binding? = null

     val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentOnboarding3Binding.inflate(inflater, container, false)
        val view = binding.root
        return view
/*
*
        _binding?.imageView?.setOnClickListener {
            val intent = Intent(context,MainActivity::class.java)
            startActivity(intent)
        }
*
*
* */

    }



}