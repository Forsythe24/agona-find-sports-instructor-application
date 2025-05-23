package com.solopov.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solopov.common.base.model.Message
import com.solopov.common.base.model.MessageAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel() {
    private val _message: MutableSharedFlow<Message> = MutableSharedFlow()
    val message = _message.asSharedFlow()

    private var viewModelJob = Job()
    private var isActive = true

    protected fun showMessage(
        message: String,
        action: MessageAction? = null
    ) {
        viewModelScope.launch {
            _message.emit(
                Message(
                    text = message,
                    action = action
                )
            )
        }
    }


    fun <P> doRepeatWork(delay: Long, doOnAsyncBlock: suspend CoroutineScope.() -> P) {
        isActive = true
        viewModelScope.launch {
            while (this@BaseViewModel.isActive) {
                withContext(Dispatchers.IO) {
                    doOnAsyncBlock.invoke(this)
                }
                if (this@BaseViewModel.isActive) {
                    delay(delay)
                }
            }
        }
    }

    private inline fun <P> doCoroutineWork(
        crossinline doOnAsyncBlock: suspend CoroutineScope.() -> P,
        coroutineScope: CoroutineScope,
        context: CoroutineContext,
    ) {
        coroutineScope.launch {
            withContext(context) {
                doOnAsyncBlock.invoke(this)
            }
        }
    }

    fun stopRepeatWork() {
        isActive = false
    }

    override fun onCleared() {
        super.onCleared()
        isActive = false
        viewModelJob.cancel()
    }
}
