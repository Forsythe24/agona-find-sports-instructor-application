package com.solopov.feature_user_profile_impl.presentation.user_profile

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.solopov.com.solopov.feature_user_profile_impl.R
import com.solopov.feature_user_profile_impl.UserProfileRouter
import com.solopov.feature_user_profile_impl.di.UserProfileFeatureComponent
import com.solopov.com.solopov.feature_user_profile_impl.databinding.FragmentUserProfileBinding
import com.solopov.common.base.BaseFragment
import com.solopov.common.di.FeatureUtils
import com.solopov.common.model.ChatCommon
import com.solopov.common.model.UserCommon
import com.solopov.common.utils.ParamsKey
import com.solopov.feature_user_profile_api.di.UserProfileFeatureApi
import com.solopov.feature_user_profile_impl.data.mappers.UserMappers
import com.solopov.feature_user_profile_impl.presentation.user_profile.model.RatingUi
import com.solopov.feature_user_profile_impl.presentation.user_profile.model.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserProfileFragment : BaseFragment<UserProfileViewModel>() {

    private lateinit var binding: FragmentUserProfileBinding

    @Inject
    lateinit var router: UserProfileRouter

    @Inject
    lateinit var userMappers: UserMappers


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUsers()
        initListeners()
    }

    private fun initUsers() {

        val user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable(ParamsKey.USER, UserCommon::class.java)
        } else {
            arguments?.getSerializable(ParamsKey.USER) as UserCommon?
        }

        val chat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable(ParamsKey.CHAT, ChatCommon::class.java)
        } else {
            arguments?.getSerializable(ParamsKey.CHAT) as ChatCommon?
        }

        if (user != null || chat != null) {
            // we can get here either from instructors list, or from chat
            val chatCommon: ChatCommon = if (user != null) {
                viewModel.setUserProfile(user)
                ChatCommon(
                    user.id,
                    user.name,
                    user.photo
                )

            } else {
                viewModel.getUserByUid(chat!!.userId)
                chat
            }

            binding.sendMessageBtn.setOnClickListener {
                router.openChat(
                    chatCommon
                )
            }

            hideProfileEditingViews()

            viewModel.setCurrentUser()
        } else {
            //if it's the current user's profile
            viewModel.setCurrentUserProfile()
            hideOtherUserSpecificViews()
        }
    }

    private fun initListeners() {
        with(binding) {
            imageSearchIv.setOnClickListener {
                selectImage()
            }

            instructorRatingBar.onRatingBarChangeListener =
                OnRatingBarChangeListener { _, rating, _ ->
                    sendMessageBtn.visibility = GONE
                    showRateButtons()

                    cancelBtn.setOnClickListener {
                        instructorRatingBar.rating = 0f
                        sendMessageBtn.visibility = VISIBLE
                        hideRateButtons()
                    }
                    rateBtn.setOnClickListener {

                        addRating(rating)

                        sendMessageBtn.visibility = VISIBLE
                        hideRateButtons()
                    }
                }
        }
    }

    override fun initViews() {
    }


    override fun inject() {
        FeatureUtils.getFeature<UserProfileFeatureComponent>(
            this,
            UserProfileFeatureApi::class.java
        )
            .userProfileComponentFactory()
            .create(this)
            .inject(this)
    }


    override fun subscribe(viewModel: UserProfileViewModel) {
        with(viewModel) {
            userProfileFlow.observe {
                it?.let { user ->
                    binding.editBtn.setOnClickListener {
                        router.goToEditingProfile(user)
                    }

                    if (user.isInstructor.not()) {

                        hideInstructorSpecificViews()

                        binding.instructBtn.setOnClickListener {
                            router.goToInstructApplication(user)
                        }

                    } else {
                        hideRegularUserSpecificViews()
                    }

                    setUserDetails(user)
                }
            }

            currentUserFlow.observe { user ->
                user?.let {
                    if (user.id == viewModel.userProfileFlow.value?.id) {
                        hideOtherUserSpecificViews()
                    }
                }
            }

            ratingsFlow.observe { ratings ->
                ratings?.let {
                    updateRating()
                }
            }

            lifecycleScope.launch {
                errorsChannel.consumeEach { error ->
                    val errorMessage = error.message ?: getString(R.string.unknown_error)

                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
                }
            }

        }
    }

    private fun addRating(currentRating: Float) {
        with(viewModel) {
            userProfileFlow.value?.let { otherUser ->
                currentUserFlow.value?.let { currentUser ->

                    viewModel.addRating(
                        RatingUi(
                            null,
                            otherUser.id,
                            currentUser.id,
                            currentRating
                        )
                    )
                }
            }
        }
    }

    private fun updateRating() {

        val allRatings = viewModel.ratingsFlow.value

        if (allRatings != null) {
            val ratingsSum = allRatings.map { ratingUi ->
                ratingUi.rating
            }.sum()

            val newNumberOfRatings = allRatings.size
            val newRating = ratingsSum / if (allRatings.isEmpty()) 1 else newNumberOfRatings

            viewModel.userProfileFlow.value?.let {
                val newUserProfile = with(it) {
                    UserProfile(
                        id = id,
                        email = email,
                        password = password,
                        name = name,
                        age = age,
                        gender = gender,
                        sport = sport,
                        photo = photo,
                        experience = experience,
                        description = description,
                        rating = newRating,
                        numberOfRatings = newNumberOfRatings,
                        hourlyRate = hourlyRate,
                        isInstructor = isInstructor
                    )
                }

                viewModel.updateUserRating(newUserProfile)
                viewModel.setUserProfile(userMappers.mapUserProfileToUserCommon(newUserProfile))
            }
        }
    }

    private fun setUserDetails(userProfile: UserProfile) {
        with(binding) {
            with(userProfile) {
                val fullGender = if (gender == "M") getString(R.string.male) else getString(
                    R.string.female
                )
                userInfoTv.text =
                    resources.getString(R.string.instructor_info_template).format(fullGender, age)
                nameTv.text = name
                if (photo.isNullOrEmpty()) {
                    userIv.setImageResource(R.drawable.no_profile_photo)
                } else {
                    showImage(photo!!, userIv)
                }
                rating?.let { rating ->
                    ratingTv.text = resources.getString(R.string.rating_template).format(rating)
                }
                hourlyRate?.let { hourlyRate ->
                    hourlyRateTv.text =
                        resources.getString(R.string.hourly_rate_template).format(hourlyRate)
                }
                description?.let { description ->
                    descriptionTv.text = description
                }
                experience?.let { experience ->
                    experienceTv.text = experience
                }

                numberOfRatings?.let { numberOfRatings ->
                    numberOfRatingsTv.text = numberOfRatings.toString()

                }
            }
        }
    }

    private fun hideInstructorSpecificViews() {
        with(binding) {
            hourlyRateTv.visibility = GONE
            experienceTv.visibility = GONE
            descriptionTv.visibility = GONE
            numberOfRatingsTv.visibility = GONE
            ratingTv.visibility = GONE

            instructorRatingBar.visibility = GONE
        }
    }

    private fun hideRateButtons() {
        with(binding) {
            rateBtn.visibility = GONE
            cancelBtn.visibility = GONE
        }
    }

    private fun showRateButtons() {
        with(binding) {
            rateBtn.visibility = VISIBLE
            cancelBtn.visibility = VISIBLE
        }
    }


    private fun hideOtherUserSpecificViews() {
        with(binding) {
            sendMessageBtn.visibility = GONE
            instructorRatingBar.visibility = GONE
        }
    }

    private fun hideRegularUserSpecificViews() {
        with(binding) {
            instructBtn.visibility = GONE
            instructTv.visibility = GONE
        }
    }

    private fun hideProfileEditingViews() {
        //since it's not the current user's profile
        with(binding) {
            editBtn.visibility = GONE
            imageSearchIv.visibility = GONE
        }

    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            with(result) {
                if (resultCode == Activity.RESULT_OK && data != null && data!!.data != null) {
                    val imageUri = data!!.data!!

                    viewModel.uploadProfileImage(imageUri)
                    showImage(imageUri.toString(), binding.userIv)
                } else {
                    showAlert(
                        getString(R.string.file_uploading_error),
                        getString(R.string.uploading_went_wrong)
                    )
                }
            }
        }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        resultLauncher.launch(intent)

    }

    private fun showImage(uri: String, imageView: ImageView) {
        Glide.with(requireContext())
            .load(uri)
            .into(imageView)

    }
}
