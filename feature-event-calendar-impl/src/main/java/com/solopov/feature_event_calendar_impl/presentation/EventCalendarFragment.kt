package com.solopov.feature_event_calendar_impl.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.solopov.common.base.BaseFragment
import com.solopov.common.di.FeatureUtils
import com.solopov.feature_event_calendar_api.di.EventCalendarFeatureApi
import com.solopov.feature_event_calendar_impl.databinding.FragmentEventCalendarBinding
import com.solopov.feature_event_calendar_impl.di.EventCalendarFeatureComponent

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

    }

    override fun inject() {
        FeatureUtils.getFeature<EventCalendarFeatureComponent>(this, EventCalendarFeatureApi::class.java)
            .eventCalendarComponentFactory()
            .create(this)
            .inject(this)
    }

    override fun subscribe(viewModel: EventCalendarViewModel) {

    }
}
