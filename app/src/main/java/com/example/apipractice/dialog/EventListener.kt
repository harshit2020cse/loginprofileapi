package com.example.apipractice.dialog

import android.view.Gravity
import androidx.lifecycle.MutableLiveData
import com.example.apipractice.R
import com.example.apipractice.di.ResourceProvider
import com.example.apipractice.dialog.viewmodel.DialogViewModel
import com.example.quizee.view.dialog.common.LoadingUiConfig

class EventListener constructor(
    private val resourceProvider: ResourceProvider
) {
    var snackBarMessage = MutableLiveData<String>()
    val isDialogVisible = MutableLiveData<Boolean>()
    val loadingDialogConfig = MutableLiveData<LoadingUiConfig>()
    val errorDialogConfig = MutableLiveData<DialogUiConfig>()
    var errorDialogViewModel = MutableLiveData<DialogViewModel>()


    /**
     * Show Message Dialog as Requirement
     * */
    fun showMessageDialog(
        message: String? = null,
        title: String? = null,
        positiveClickTitle: String? = null,
        negativeClickTitle: String? = null,
        positiveClick: (() -> Unit)? = { dismissMessageDialog() },
        negativeClick: (() -> Unit)? = { dismissMessageDialog() },
        gravity: Int? = null/* Like Gravity.CENTER */,
        cancelable: Boolean? = null
    ) {
        errorDialogConfig.postValue(
            DialogUiConfig(
                title ?: resourceProvider.getString(R.string.error_title),
                message ?: resourceProvider.getString(R.string.error_message),
                positiveClickTitle ?: resourceProvider.getString(R.string.ok),
                negativeClickTitle ?: "",
                gravity ?: Gravity.CENTER,
                cancelable ?: false
            )
        )

        errorDialogViewModel.postValue(DialogViewModel(positiveClick, negativeClick))
        isDialogVisible.postValue(true)
    }

    /**
     * Dismiss Message Dialog
     * */
    fun dismissMessageDialog() {
        isDialogVisible.postValue(false)
    }


}