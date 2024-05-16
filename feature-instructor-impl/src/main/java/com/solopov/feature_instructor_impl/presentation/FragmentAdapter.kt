package com.solopov.feature_instructor_impl.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter(manager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(manager, lifecycle) {

    override fun getItemCount(): Int = 5



    override fun createFragment(position: Int): Fragment {
        return OneSportInstructorsFragment.newInstance(position)
    }
}

