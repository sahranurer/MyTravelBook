package com.sahraer.mytravelbook.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sahraer.mytravelbook.onboarding.Onboarding1Fragment
import com.sahraer.mytravelbook.onboarding.Onboarding2Fragment
import com.sahraer.mytravelbook.onboarding.Onboarding3Fragment

class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager){

    private val fragments = listOf(
        Onboarding1Fragment(),
        Onboarding2Fragment(),
        Onboarding3Fragment()
    )

    override fun getCount(): Int {
       return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }
}