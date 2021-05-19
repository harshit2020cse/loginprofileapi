package com.example.apipractice.view.fragments.profile

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apipractice.data.ProfileData
import com.example.apipractice.data.ProfileModel
import com.example.apipractice.repo.AuthApiService
import com.example.apipractice.ui.DateFormatUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

    val loginResponse = MutableLiveData<ProfileModel>()

    fun getProfileData() {
        progressBarVisible.set(true)
        AuthApiService().getProfile()
            .enqueue(object : Callback<ProfileModel> {
                override fun onResponse(
                    call: Call<ProfileModel>,
                    response: Response<ProfileModel>
                ) {
                    if (response.isSuccessful) {
                        loginResponse.value = response.body()
                        response.body()?.data?.let { setUIData(it) }
                        Log.e("ProfileData", "profile ${response.body()}")

                    }
                }

                override fun onFailure(call: Call<ProfileModel>, t: Throwable) {

                }

            })
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