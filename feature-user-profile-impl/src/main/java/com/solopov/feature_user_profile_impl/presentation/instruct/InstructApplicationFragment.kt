package com.solopov.feature_user_profile_impl.presentation.instruct

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.solopov.com.solopov.feature_user_profile_impl.R
import com.solopov.com.solopov.feature_user_profile_impl.databinding.FragmentInstructApplicationBinding
import com.solopov.common.base.BaseFragment
import com.solopov.common.di.FeatureUtils
import com.solopov.common.model.UserCommon
import com.solopov.common.utils.ParamsKey
import com.solopov.feature_user_profile_api.di.UserProfileFeatureApi
import com.solopov.feature_user_profile_api.domain.model.User
import com.solopov.feature_user_profile_impl.di.UserProfileFeatureComponent
import com.solopov.feature_user_profile_impl.presentation.user_profile.model.UserProfile


class InstructApplicationFragment: BaseFragment<InstructApplicationViewModel>(){
    private lateinit var binding: FragmentInstructApplicationBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentInstructApplicationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
    }
    override fun initViews() {
        setOnSportsTypeSelectedListener()

        val sportsTypes = resources.getStringArray(R.array.sports_types)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.sports_type_dropdown_item, sportsTypes)

        with(binding) {
            sportsTypeAutocompleteTv.setAdapter(arrayAdapter)

            applyBtn.setOnClickListener {

                val userProfile = extractUserArgument()
                println(userProfile)

                userProfile?.let {
                    it.description = descriptionEt.text.toString()
                    it.experience = experienceEt.text.toString()
                    it.sport = sportsTypeAutocompleteTv.text.toString()
                    it.hourlyRate = hourlyRateSb.progress.toFloat()
                    it.isInstructor = true

                    println(sportsTypeAutocompleteTv.text.toString())
                    println(hourlyRateSb.progress.toFloat())

                    viewModel.updateUser(it)
                }
            }

        }
    }

    private fun extractUserArgument(): UserProfile? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getSerializable(ParamsKey.USER, UserProfile::class.java)
        } else {
            requireArguments().getSerializable(ParamsKey.USER) as UserProfile
        }
    }

    private fun setOnSportsTypeSelectedListener() {
        with(binding) {
            sportsTypeAutocompleteTv.onItemClickListener =
                OnItemClickListener { _, _, id, _ ->
                    val resourceId = when (id) {
                        0 -> R.drawable.baseline_sports_soccer_24
                        1 -> R.drawable.baseline_sports_basketball_24
                        2 -> R.drawable.baseline_sports_hockey_24
                        3 -> R.drawable.baseline_sports_volleyball_24
                        else -> R.drawable.baseline_sports_handball_24
                    }
                    specialtyTextInput.setStartIconDrawable(resourceId)
                }
        }
    }


    override fun inject() {
        FeatureUtils.getFeature<UserProfileFeatureComponent>(this, UserProfileFeatureApi::class.java)
            .instructApplicationComponentFactory()
            .create(this)
            .inject(this)
    }

    override fun subscribe(viewModel: InstructApplicationViewModel) {
    }

}
