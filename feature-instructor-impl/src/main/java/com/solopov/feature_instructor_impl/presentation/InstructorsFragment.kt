package com.solopov.feature_instructor_impl.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.solopov.instructors.R
import com.solopov.instructors.databinding.FragmentInstructorsBinding


class InstructorsFragment : Fragment() {

    private lateinit var binding: FragmentInstructorsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentInstructorsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }



    private fun initViews() {
        with(binding) {
            sportsKindsVp.adapter = FragmentAdapter(childFragmentManager, lifecycle)

            with(sportsKindsTabLayout) {
                addTab(newTab().setText(context.getString(R.string.football)))
                addTab(newTab().setText(context.getString(R.string.basketball)))
                addTab(newTab().setText(context.getString(R.string.hockey)))
                addTab(newTab().setText(context.getString(R.string.volleyball)))
                addTab(newTab().setText(context.getString(R.string.handball)))


                addOnTabSelectedListener(object : OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        if (tab != null) {
                            sportsKindsVp.currentItem = tab.position
                        }
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {
                    }

                    override fun onTabReselected(tab: TabLayout.Tab?) {
                    }

                })

                selectTab(sportsKindsTabLayout.getTabAt(0))
            }

            sportsKindsVp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    sportsKindsTabLayout.selectTab(sportsKindsTabLayout.getTabAt(position))
                }
            })


        }
    }

}
