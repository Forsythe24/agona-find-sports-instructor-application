package com.solopov.common.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {

    data class BaseDialogData(
        val title: String,
        val message: String
    )

    protected fun showAlert(errorText: String) {
    }

    protected fun showErrorDialog(dialogData: BaseDialogData) {
    }
}
