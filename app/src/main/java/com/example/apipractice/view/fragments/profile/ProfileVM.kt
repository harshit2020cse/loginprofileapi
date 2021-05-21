package com.example.apipractice.view.fragments.profile

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.apipractice.application.MyApplication
import com.example.apipractice.data.ProfileData
import com.example.apipractice.networkcall.ProfileListener
import com.example.apipractice.repo.AuthRepository
import com.example.apipractice.ui.DateFormatUtils
import java.util.*

class ProfileVM : ViewModel() {

    /* Ui Fields */
    val progressBarVisible = ObservableBoolean(false)
    val profilePictureField = ObservableField("")
    val medoPlusIdField = ObservableField("")
    val firstNameField = ObservableField("")
    val lastNameField = ObservableField("")
    val dobField = ObservableField("")
    val addressFirstField = ObservableField("")
    val phoneNumberField = ObservableField("")
    val firstAlternatePhoneNumberField = ObservableField("")
    val secondAlternatePhoneNumberField = ObservableField("")
    val bloodGroup = ObservableField("")
    var profileListener: ProfileListener? = null
    val app = MyApplication.getApplication()

    /**
     *  Get Profile Data
     */
    fun getProfileData() {

        /* Notify Loading */
        progressBarVisible.set(true)

        /* Get API Response */
        val loginResponse = AuthRepository().getProfile()
        profileListener?.onSuccess(loginResponse)

        /* Notify Loading */
        progressBarVisible.set(false)

    }

    /**
     * Set Ui Data
     * */
    fun setUIData(data: ProfileData) {

        medoPlusIdField.set(data.medoplusId ?: "")

        data.firstName?.en?.let {
            firstNameField.set(it.trim().capitalize(Locale.ROOT))
        }

        data.lastName?.en?.let {
            lastNameField.set(it.trim().capitalize(Locale.ROOT))
        }

        data.dob?.let {
            dobField.set(
                DateFormatUtils().getDateByFormatCustomUTC(
                    it,
                    true,
                    DateFormatUtils.DATE_TIME_FORMAT.MONOGO_DB_UTC, false,
                    DateFormatUtils.DATE_TIME_FORMAT.dd_MM_yyyy
                )
            )
        }

        data.address?.line1?.en?.let {
            addressFirstField.set(it.trim())
        }

        data.number?.let {
            phoneNumberField.set(it)
        }

        data.bloodGroup?.let {
            bloodGroup.set(it)
        }

        data.pictures?.let {
            if (it.isNotEmpty()) {
                profilePictureField.set(it[0].preview)
            }
        }

        data.alternateNumber?.let {
            firstAlternatePhoneNumberField.set(it)
        }

        data.alternateNumber2?.let {
            secondAlternatePhoneNumberField.set(it)
        }
    }
}