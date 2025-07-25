package com.solopov.common.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.solopov.common.R
import com.solopov.common.base.model.DialogData
import com.solopov.common.utils.observe
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

abstract class BaseFragment<T : BaseViewModel> : Fragment() {

    @Inject
    open lateinit var viewModel: T
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inject()
        initViews()
        subscribe(viewModel)

    }

    protected fun showAlert(title: String, alertMessage: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(alertMessage)
            .setPositiveButton(R.string.common_ok) { _, _ -> }
            .show()
    }

    protected fun showErrorWithTitle(payload: DialogData) {
        AlertDialog.Builder(requireContext())
            .setTitle(payload.title)
            .setMessage(payload.message)
            .setPositiveButton(android.R.string.ok) { _, _ -> }
            .show()
    }

    protected fun showSnackbar(message: String, duration: Int) {
        Snackbar.make(requireView(), message, duration).show()
    }

    inline fun <T> Flow<T>.observe(crossinline block: (T) -> Unit): Job {
        return observe(fragment = this@BaseFragment, block)
    }


    abstract fun initViews()

    abstract fun inject()

    abstract fun subscribe(viewModel: T)
}
