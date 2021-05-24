package com.example.apipractice.view.fragments.profile

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apipractice.application.MyApplication
import com.example.apipractice.data.ProfileData
import com.example.apipractice.data.ProfileModel
import com.example.apipractice.repo.AuthApiService
import com.example.apipractice.ui.DateFormatUtils
import com.example.apipractice.util.StorePreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ProfileVM : ViewModel() {

    /* Application to get context */
    val app = MyApplication.getApplication()

    /* StorePreferences to Store Data */
    var storePreferences = StorePreferences(MyApplication.getApplication())

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
    val responseData = MutableLiveData<String>()
    val profileResponse = MutableLiveData<ProfileModel>()

    /**
     *  Get Profile Data
     */
    fun getProfileData() {
        /** Update User Profile API Response Data */
        AuthApiService().getProfile()
            .enqueue(object : Callback<ProfileModel> {
                override fun onResponse(
                    call: Call<ProfileModel>,
                    response: Response<ProfileModel>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == true && response.body() != null) {
                            Log.e("Status", "Status ${response.body()?.status}")
                            profileResponse.postValue(response.body())

                            viewModelScope.launch(Dispatchers.IO) {

                                /* Set UI data */
                                response.body()?.data?.let { it1 ->
                                    setUIData(it1)

                                    /* Store Profile data in DataStore */
                                    storePreferences.storeValue(
                                        StorePreferences.DEMAND_PROFILE_DATA,
                                        it1
                                    )
                                    app.setProfileData(it1)
                                }

                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ProfileModel>, t: Throwable) {
                    responseData.postValue(t.message.toString())
                }
            })
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