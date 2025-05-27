package com.solopov.feature_event_calendar_impl.presentation

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.solopov.common.base.BaseFragment
import com.solopov.common.di.FeatureUtils
import com.solopov.common.ui.ProgressButton
import com.solopov.common.utils.ParamsKey
import com.solopov.feature_event_calendar_api.di.EventCalendarFeatureApi
import com.solopov.feature_event_calendar_impl.R
import com.solopov.feature_event_calendar_impl.databinding.FragmentEventCalendarBinding
import com.solopov.feature_event_calendar_impl.di.EventCalendarFeatureComponent
import com.solopov.feature_event_calendar_impl.presentation.model.EventItem
import com.solopov.feature_event_calendar_impl.presentation.recycler_view.EventAdapter
import nl.joery.timerangepicker.TimeRangePicker
import java.util.Calendar
import java.util.Date

class EventCalendarFragment : BaseFragment<EventCalendarViewModel>() {

    private lateinit var binding: FragmentEventCalendarBinding
    private lateinit var dialog: Dialog
    private lateinit var scheduleButton: ProgressButton
    private lateinit var placeTextInput: TextInputLayout
    private lateinit var placeEt: TextInputEditText
    private lateinit var activityTextInput: TextInputLayout
    private lateinit var activityEt: TextInputEditText
    private lateinit var partnerAutoCompleteTextView: AutoCompleteTextView
    private lateinit var picker: TimeRangePicker
    private lateinit var timeRangeTextView: TextView

    private var pickedYear: Int = 0
    private var pickedMonth: Int = 0
    private var pickedDay: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentEventCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCurrentUserId()
        val date = getDate(pickedYear, pickedMonth, pickedDay)
        viewModel.getAllEventsByDate(date)
        viewModel.deleteAllEventsThreeOrMoreDaysAgo(date)

        setPartnerNameIfArgumentPresent()
    }

    override fun initViews() {
        binding.eventRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        initDialogViews()

        binding.addBtn.setOnClickListener {
            showEventAddingDialog()
        }

        setSaveEventButtonOnClickListener()
        setOnDateChangeListener()
        setUpOnItemTouchHelper()
    }

    override fun subscribe(viewModel: EventCalendarViewModel) {
        with(viewModel) {

            eventListState.observe { eventList ->
                eventList?.let {
                    updateEventList(it)
                }
            }

            loadingState.observe { isLoading ->
                scheduleButton.setLoading(isLoading)
            }

            currentUserIdState.observe {
                it?.let {
                    getAllPossiblePartnersNamesByUserId(it)
                }
            }

            possiblePartnersNameListState.observe {
                it?.let {
                    setPartnerDropDownMenuAdapter(it)
                }
            }

            currentEventState.observe {
            }

            message.observe { message ->
                Snackbar.make(binding.root, message.text, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun setPartnerNameIfArgumentPresent() {
        val partnerName = arguments?.getString(ParamsKey.PARTNER_NAME_KEY)

        if (!partnerName.isNullOrEmpty()) {
            partnerAutoCompleteTextView.setText(partnerName)

            viewModel.possiblePartnersNameListState.value?.let {
                setPartnerDropDownMenuAdapter(it)
            }

            showEventAddingDialog()
        }
    }

    private fun onEventDeleted() {
        viewModel.getAllEventsByDate(getDate(pickedYear, pickedMonth, pickedDay))
    }

    private fun setUpOnItemTouchHelper() {
        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(
                    ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder,
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    viewModel.eventListState.value?.let {
                        viewModel.deleteEvent(
                            it[viewHolder.bindingAdapterPosition],
                            ::onEventDeleted
                        )
                    }

                }
            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.eventRv)
    }

    private fun updateEventList(events: List<EventItem>) {
        with(binding) {
            if (eventRv.adapter == null) {
                eventRv.adapter = EventAdapter(::onEventItemClicked)
            }
            (eventRv.adapter as EventAdapter).submitList(events)
        }
    }

    private fun setOnDateChangeListener() {
        with(binding.calendarView) {
            setCurrentDateAsPickedDate(Date(date))

            setOnDateChangeListener { _, year, month, day ->
                viewModel.getAllEventsByDate(getDate(year, month, day))
                pickedYear = year
                pickedMonth = month
                pickedDay = day
            }
        }
    }

    private fun setSaveEventButtonOnClickListener() {
        scheduleButton.setOnClickListener {
            if (activityEt.text.isNullOrEmpty()) {
                activityTextInput.error = getString(R.string.activity_field_must_not_be_empty)
            } else {
                // if id is present, that means we are just editing an event saved earlier
                val id = viewModel.currentEventState.value?.id ?: 0L
                viewModel.saveEvent(
                    EventItem(
                        id,
                        activityEt.text.toString(),
                        partnerAutoCompleteTextView.text.toString(),
                        getDate(pickedYear, pickedMonth, pickedDay),
                        picker.startTime.totalMinutes,
                        picker.endTime.totalMinutes,
                        placeEt.text.toString()
                    ),
                    ::onEventSaved
                )
            }
        }
    }

    private fun onEventItemClicked(event: EventItem) {
        showEventAddingDialog()
        setDialogViewsToEventEditingMode(event)
        viewModel.setCurrentEvent(event)
    }

    private fun setDialogViewsToEventEditingMode(event: EventItem) {
        partnerAutoCompleteTextView.setText(event.personName)
        viewModel.possiblePartnersNameListState.value?.let {
            setPartnerDropDownMenuAdapter(it)
        }
        activityEt.setText(event.name)
        placeEt.setText(event.place)

        setTimeRange(event.startTime, event.endTime)

        viewModel.currentEventState.value?.id = event.id
    }

    private fun onEventSaved() {
        viewModel.getAllEventsByDate(getDate(pickedYear, pickedMonth, pickedDay))

        dialog.hide()
        Snackbar.make(binding.root, getString(R.string.saved_event), Snackbar.LENGTH_SHORT)
            .show()
        clearDialogViews()

        binding.eventRv.smoothScrollToPosition(0)
    }

    private fun setPartnerDropDownMenuAdapter(possiblePartnerList: List<String>) {
        val possiblePartnerListWithDefaultValue = mutableListOf<String>()
        possiblePartnerListWithDefaultValue.add(getString(R.string.no_partner))
        possiblePartnerListWithDefaultValue.addAll(possiblePartnerList)

        val arrayAdapter = ArrayAdapter(
            requireContext(),
            R.layout.possible_partner_dropdown_item,
            possiblePartnerListWithDefaultValue
        )
        partnerAutoCompleteTextView = dialog.findViewById(R.id.partner_autocomplete_tv)
        partnerAutoCompleteTextView.setAdapter(arrayAdapter)
    }

    private fun initDialogViews() {
        dialog = Dialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_event_adding)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        partnerAutoCompleteTextView = dialog.findViewById(R.id.partner_autocomplete_tv)
        scheduleButton = dialog.findViewById(R.id.save_btn)
        placeTextInput = dialog.findViewById(R.id.place_text_input)
        placeEt = dialog.findViewById(R.id.place_et)
        activityTextInput = dialog.findViewById(R.id.activity_text_input)
        activityEt = dialog.findViewById(R.id.activity_et)

        picker = dialog.findViewById(R.id.picker)
        timeRangeTextView = dialog.findViewById(R.id.time_range_tv)

        partnerAutoCompleteTextView.setText(getString(R.string.no_partner))

        setUpTimeRangePicker()

        dialog.window?.setLayout(
            LinearLayoutCompat.LayoutParams.MATCH_PARENT,
            LinearLayoutCompat.LayoutParams.MATCH_PARENT
        )

        dialog.setOnCancelListener {
            clearDialogViews()
        }

        activityEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (text.isNullOrEmpty()) {
                    activityTextInput.error =
                        getString(R.string.activity_field_must_not_be_empty)
                } else {
                    activityTextInput.error = ""
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        }
        )
    }

    private fun clearDialogViews() {
        placeEt.setText("")
        activityEt.setText("")
        activityTextInput.error = ""

        setTimeRange(TWELVE_O_CLOCK_IN_MINUTES, TWO_O_CLOCK_IN_MINUTES)
    }

    private fun setTimeRange(startTime: Int, endTime: Int) {
        picker.startTimeMinutes = startTime
        picker.endTimeMinutes = endTime

        timeRangeTextView.text = getTimeRangeString(startTime, endTime)
    }

    private fun showEventAddingDialog() {
        dialog.show()
    }

    private fun setUpTimeRangePicker() {

        var currentTimeRange = timeRangeTextView.text
        picker.setOnTimeChangeListener(object : TimeRangePicker.OnTimeChangeListener {
            override fun onStartTimeChange(startTime: TimeRangePicker.Time) {
                currentTimeRange = currentTimeRange.replaceRange(
                    0,
                    currentTimeRange.indexOf('-'),
                    startTime.toString()
                )
                timeRangeTextView.text = currentTimeRange
            }

            override fun onEndTimeChange(endTime: TimeRangePicker.Time) {
                currentTimeRange = currentTimeRange.replaceRange(
                    currentTimeRange.indexOf('-') + 1,
                    currentTimeRange.length,
                    endTime.toString()
                )
                timeRangeTextView.text = currentTimeRange
            }

            override fun onDurationChange(duration: TimeRangePicker.TimeDuration) {
            }
        })
    }

    private fun getDate(year: Int, month: Int, day: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)



        return calendar.time

    }

    private fun getTimeRangeString(startTime: Int, endTime: Int): String {
        return String.format(
            getString(R.string.time_range_template),
            convertMinutesToHHmm(startTime),
            convertMinutesToHHmm(endTime)
        )
    }

    private fun convertMinutesToHHmm(minutesTotalNumber: Int): String {
        val hours = minutesTotalNumber / 60
        val minutes = minutesTotalNumber % 60
        return String.format(getString(R.string.time_template), hours, minutes)
    }


    private fun setCurrentDateAsPickedDate(date: Date) {
        val calendar = Calendar.getInstance()
        calendar.time = date

        pickedYear = calendar.get(Calendar.YEAR)
        pickedMonth = calendar.get(Calendar.MONTH)
        pickedDay = calendar.get(Calendar.DAY_OF_MONTH)
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

    companion object {
        const val TWELVE_O_CLOCK_IN_MINUTES = 720
        const val TWO_O_CLOCK_IN_MINUTES = 840
    }
}
