package com.sahraer.mytravelbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sahraer.mytravelbook.adapter.ViewPagerAdapter
import com.sahraer.mytravelbook.databinding.ActivityMainBinding
import com.sahraer.mytravelbook.databinding.ActivityOnboardingHomeBinding

class Onboarding_home : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.viewpager.adapter =ViewPagerAdapter(supportFragmentManager)

    }
}