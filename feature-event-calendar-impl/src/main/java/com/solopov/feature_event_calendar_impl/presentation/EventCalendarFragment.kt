package com.solopov.feature_event_calendar_impl.presentation

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import com.solopov.common.base.BaseFragment
import com.solopov.common.di.FeatureUtils
import com.solopov.feature_event_calendar_api.di.EventCalendarFeatureApi
import com.solopov.feature_event_calendar_impl.R
import com.solopov.feature_event_calendar_impl.databinding.FragmentEventCalendarBinding
import com.solopov.feature_event_calendar_impl.di.EventCalendarFeatureComponent
import nl.joery.timerangepicker.TimeRangePicker
import java.util.Calendar
import java.util.Date

class EventCalendarFragment : BaseFragment<EventCalendarViewModel>() {

    private lateinit var binding: FragmentEventCalendarBinding

    private lateinit var dialog: Dialog


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
        viewModel.getCurrentUserId()
    }

    override fun initViews() {
        initDialogViews()

        binding.addBtn.setOnClickListener {
            showEventAddingDialog()
        }

    }

    override fun inject() {
        FeatureUtils.getFeature<EventCalendarFeatureComponent>(
            this,
            EventCalendarFeatureApi::class.java
        )
            .eventCalendarComponentFactory()
            .create(this)
            .inject(this)
    }

    override fun subscribe(viewModel: EventCalendarViewModel) {
        with(viewModel) {
            eventListFlow.observe { eventList ->

                binding.calendarView.setOnDateChangeListener { calendarView, year, month, day ->
                    getAllEventsByDate(getDate(year, month, day))
                }

            }

            currentUserIdFlow.observe {
                it?.let {
                    getAllPossiblePartnersNamesByUserId(it)
                }
            }

            possiblePartnersNameListFlow.observe {
                it?.let {
                    setPartnerDropDownMenuAdapter(it)
                }
            }
        }
    }

    private fun setPartnerDropDownMenuAdapter(possiblePartnerList: List<String>) {
        val arrayAdapter = ArrayAdapter(
            requireContext(),
            R.layout.possible_partner_dropdown_item,
            possiblePartnerList
        )
        dialog.findViewById<AutoCompleteTextView>(R.id.partner_autocomplete_tv)
            .setAdapter(arrayAdapter)
    }

    private fun initDialogViews() {
        dialog = Dialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_event_adding)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    }

    private fun showEventAddingDialog() {
        dialog.show()

        initTimeRangePicker()
    }

    private fun initTimeRangePicker() {
        val picker: TimeRangePicker = dialog.findViewById(R.id.picker)
        val timeRangeTv: TextView = dialog.findViewById(R.id.time_range_tv)

        var currentTimeRange = timeRangeTv.text
        picker.setOnTimeChangeListener(object : TimeRangePicker.OnTimeChangeListener {
            override fun onStartTimeChange(startTime: TimeRangePicker.Time) {
                currentTimeRange = currentTimeRange.replaceRange(
                    0,
                    currentTimeRange.indexOf('-'),
                    startTime.toString()
                )
                timeRangeTv.text = currentTimeRange
            }

            override fun onEndTimeChange(endTime: TimeRangePicker.Time) {
                currentTimeRange = currentTimeRange.replaceRange(
                    currentTimeRange.indexOf('-') + 1,
                    currentTimeRange.length,
                    endTime.toString()
                )
                timeRangeTv.text = currentTimeRange
            }

            override fun onDurationChange(duration: TimeRangePicker.TimeDuration) {
            }
        })
    }

    fun getDate(year: Int, month: Int, day: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month - 1) // Note: Months in Calendar class are 0-based
        calendar.set(Calendar.DAY_OF_MONTH, day)

        return calendar.time
    }

}
