package com.solopov.feature_event_calendar_impl.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.solopov.common.base.BaseFragment
import com.solopov.feature_event_calendar_impl.databinding.FragmentEventCalendarBinding

class EventCalendarFragment : BaseFragment<EventCalendarViewModel>() {

    private lateinit var binding: FragmentEventCalendarBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {
        TODO("Not yet implemented")
    }

    override fun inject() {
        TODO("Not yet implemented")
    }

    override fun subscribe(viewModel: EventCalendarViewModel) {
        TODO("Not yet implemented")
    }
}
