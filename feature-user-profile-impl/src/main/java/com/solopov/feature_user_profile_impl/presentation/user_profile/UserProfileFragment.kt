package com.solopov.feature_user_profile_impl.presentation.user_profile

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.solopov.com.solopov.feature_user_profile_impl.R
import com.solopov.com.solopov.feature_user_profile_impl.databinding.FragmentUserProfileBinding
import com.solopov.common.base.BaseFragment
import com.solopov.common.di.FeatureUtils
import com.solopov.common.model.ChatCommon
import com.solopov.common.utils.Constants.READ_EXTERNAL_STORAGE_REQUEST_CODE
import com.solopov.common.utils.Constants.READ_MEDIA_IMAGES_REQUEST_CODE
import com.solopov.common.utils.ParamsKey.FROM_INSTRUCTORS_SCREEN_FLAG_KEY
import com.solopov.common.utils.ParamsKey.USER_ID_KEY
import com.solopov.feature_user_profile_api.di.UserProfileFeatureApi
import com.solopov.feature_user_profile_impl.di.UserProfileFeatureComponent
import com.solopov.feature_user_profile_impl.presentation.user_profile.model.RatingUi
import com.solopov.feature_user_profile_impl.presentation.user_profile.model.UserProfile


class UserProfileFragment : BaseFragment<UserProfileViewModel>() {
    private lateinit var binding: FragmentUserProfileBinding
    private lateinit var dialog: Dialog
    private lateinit var submitButton: MaterialButton
    private lateinit var cancelButton: MaterialButton
    private lateinit var dialogTv: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUsers()
    }

    private fun initUsers() {
        val userId = arguments?.getString(USER_ID_KEY)
        val isFromInstructorsScreen = arguments?.getBoolean(FROM_INSTRUCTORS_SCREEN_FLAG_KEY)
        if (userId != null) {
            viewModel.setCurrentUser(userId, ::onUserSetCallback)

            with(viewModel) {
                setUserProfileByUid(userId)
                binding.sendMessageBtn.setOnClickListener {
                    chatFlow.value?.let {
                        viewModel.openChat(
                            it
                        )
                    }

                }
            }

            hideProfileEditingViews()
            hideRegularUserSpecificViews()
        } else {
            viewModel.setCurrentUserProfile()

            setCurrentUserProfileHeader()
            hideOtherUserSpecificViews()

            if (isFromInstructorsScreen != true) {
                binding.backBtn.visibility = GONE
            }
        }
    }

    override fun initViews() {

        with(binding) {
            editBtn.setOnClickListener {
                viewModel.userProfileFlow.value?.let {
                    viewModel.goToEditingProfile(it)
                }
            }

            deleteProfileBtn.setOnClickListener {
                initDialogViews()
                submitButton.setOnClickListener {
                    viewModel.deleteProfile(::onProfileDeleted)
                }
                dialog.show()
            }

            logOutBtn.setOnClickListener {
                initDialogViews()
                setDialogViewsToLogOutMode()
                dialog.show()
            }

            sendMessageBtn.setOnClickListener {
                viewModel.chatFlow.value?.let {
                    viewModel.openChat(
                        it
                    )
                }
            }

            viewModel.userProfileFlow.value?.let { user ->
                if (user.id == viewModel.userProfileFlow.value?.id) {
                    hideOtherUserSpecificViews()
                }
            }

            instructBtn.setOnClickListener {
                viewModel.userProfileFlow.value?.let {
                    viewModel.goToInstructApplication(it)
                }
            }

            backBtn.setOnClickListener {
                viewModel.goBack()
            }

            imageSearchIv.setOnClickListener {
                if (isPermissionGranted()) {
                    selectImage()
                } else {
                    requestPermissionWithRationale()
                }
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

    override fun inject() {
        FeatureUtils.getFeature<UserProfileFeatureComponent>(
            this, UserProfileFeatureApi::class.java
        ).userProfileComponentFactory().create(this).inject(this)
    }


    override fun subscribe(viewModel: UserProfileViewModel) {
        with(viewModel) {
            userProfileFlow.observe {
                it?.let { user ->
                    setChat(
                        ChatCommon(
                            user.id, user.name, user.photo
                        )
                    )

                    if (user.isInstructor.not()) {

                        hideInstructorSpecificViews()

                    } else {
                        hideRegularUserSpecificViews()
                    }

                    setUserDetails(user)
                }
            }

            ratingsFlow.observe { ratings ->
                ratings?.let {
                    updateRating()
                }
            }

            progressBarFlow.observe { isLoading ->
                binding.progressBar.isVisible = isLoading
            }

            errorMessageChannel.observe { message ->
                showSnackbarLong(message)
            }
        }
    }

    private fun initDialogViews() {
        dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_two_options)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        submitButton = dialog.findViewById(R.id.submit_btn)
        cancelButton = dialog.findViewById(R.id.cancel_button)
        dialogTv = dialog.findViewById(R.id.dialog_question_tv)

        cancelButton.setOnClickListener {
            dialog.hide()
        }
    }

    private fun onProfileDeleted() {
        dialog.hide()
        showSnackbarLong(getString(R.string.profile_deleted_successfully))
    }

    private fun onLoggedOut() {
        dialog.hide()
        showSnackbarLong(getString(R.string.logged_out_success))
    }

    private fun setDialogViewsToLogOutMode() {
        dialogTv.text = getString(R.string.log_out_question)
        submitButton.setOnClickListener {
            viewModel.logOut(::onLoggedOut)
        }
    }

    private fun addRating(currentRating: Float) {
        with(viewModel) {
            userProfileFlow.value?.let { otherUser ->
                currentUserFlow.value?.let { currentUser ->

                    viewModel.addRating(
                        RatingUi(
                            null, otherUser.id, currentUser.id, currentRating
                        )
                    )
                }
            }
        }
    }

    private fun setUserDetails(userProfile: UserProfile) {
        with(binding) {
            with(userProfile) {
                val fullGender = if (gender == getString(R.string.male_gender)) getString(R.string.male) else getString(
                    R.string.female
                )
                userInfoTv.text =
                    resources.getString(R.string.instructor_info_template).format(fullGender, age)
                nameTv.text = name
                if (!photo.isNullOrEmpty()) {
                    showImage(photo!!, userIv)
                }
                rating?.let { rating ->
                    ratingTv.text = getString(R.string.rating_template).format(rating)
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
                    numberOfRatingsTv.text =
                        getString(R.string.number_of_ratings_template).format(numberOfRatings)
                }
            }
        }
    }

    private fun isPermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                requireActivity(), READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ContextCompat.checkSelfPermission(
                    requireActivity(), READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            } else {
                true
            }

        }
    }

    private fun requestPermissionWithRationale() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(), READ_MEDIA_IMAGES
            ) || ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(), READ_EXTERNAL_STORAGE
            )
        ) {
            val message = getString(R.string.grant_message)
            Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).setAction(
                getString(R.string.allow).uppercase()
            ) { requestPerms() }.show()
        } else {
            requestPerms()
        }
    }

    private fun requestPerms() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            requireActivity().requestPermissions(
                arrayOf(READ_EXTERNAL_STORAGE), READ_EXTERNAL_STORAGE_REQUEST_CODE
            )
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireActivity().requestPermissions(
                arrayOf(READ_MEDIA_IMAGES), READ_MEDIA_IMAGES_REQUEST_CODE
            )
        }
        selectImageIfPermissionGranted()
    }

    private fun selectImageIfPermissionGranted() {
        if (isPermissionGranted()) {
            selectImage()
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

    private fun onUserSetCallback(areIdsSame: Boolean) {
        if (areIdsSame) {
            hideOtherUserSpecificViews()
            setCurrentUserProfileHeader()
        }
    }

    private fun setCurrentUserProfileHeader() {
        binding.profileTv.text = getString(R.string.your_profile)
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
            deleteProfileBtn.visibility = GONE
            logOutBtn.visibility = GONE
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

    private fun showSnackbarLong(text: String) {
        Snackbar.make(
            binding.root, text, Snackbar.LENGTH_LONG
        ).show()
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = getString(R.string.image_intent_type)
        intent.action = Intent.ACTION_GET_CONTENT

        resultLauncher.launch(intent)

    }

    private fun showImage(uri: String, imageView: ImageView) {
        Glide.with(requireContext()).load(uri).into(imageView)
    }
}
