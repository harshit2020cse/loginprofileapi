package com.example.apipractice.view.fragments.profile.editprofile

import android.content.ContentValues.TAG
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apipractice.application.MyApplication
import com.example.apipractice.data.ProfileData
import com.example.apipractice.data.ProfileModel
import com.example.apipractice.repo.AuthApiService
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileVM : ViewModel() {

    /* Application to get context */
    val app = MyApplication.getApplication()

    /* Ui Fields */
    val firstNameField = ObservableField("")
    val lastNameField = ObservableField("")
    val dobField = ObservableField("")
    val alternatePhoneFirstNumberField = ObservableField("")
    val alternatePhoneSecondNumberField = ObservableField("")
    val responseData = MutableLiveData<String>()
    val profileResponse = MutableLiveData<ProfileModel>()

    /* Data Members */
    private var profileData: ProfileData? = null

    /**
     * Update Profile Data
     * */
    fun updateProfileData() {

        /* POST Request Body Parameters */
        val jsonObject = JsonObject()
        val firstNameJsonObject = JsonObject()
        firstNameJsonObject.addProperty(
            "en",
            firstNameField.get()?.trim() ?: ""
        )
        firstNameJsonObject.addProperty("hi", firstNameField.get()?.trim() ?: "")
        jsonObject.add(
            "firstName",
            firstNameJsonObject
        )

        val lastNameJsonObject = JsonObject()
        lastNameJsonObject.addProperty(
            "en",
            lastNameField.get()?.trim() ?: ""
        )
        lastNameJsonObject.addProperty("hi", lastNameField.get()?.trim() ?: "")
        jsonObject.add(
            "lastName",
            lastNameJsonObject
        )

        if (!alternatePhoneFirstNumberField.get().isNullOrEmpty()) {
            jsonObject.addProperty(
                "alternateNumber",
                alternatePhoneFirstNumberField.get()
            )
        }

        if (!alternatePhoneSecondNumberField.get().isNullOrEmpty()) {
            jsonObject.addProperty(
                "alternateNumber2",
                alternatePhoneSecondNumberField.get()
            )
        }
        Log.e(TAG, "response $jsonObject")

        /* Get API Response */
        AuthApiService().updateUserProfile(jsonObject)
            .enqueue(object : Callback<ProfileModel> {
                override fun onResponse(
                    call: Call<ProfileModel>,
                    response: Response<ProfileModel>
                ) {
                    if (response.body()?.status == true && response.body() != null) {
                        Log.e("Status", "Status ${response.body()?.status}")
                        profileResponse.postValue(response.body())

                        viewModelScope.launch(Dispatchers.IO) {

                            app.getProfileData()?.let {
                                setUiData(it)
                                Log.e("ProfileData", "ProfileData ${app.getProfileData()}")
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
     * Set Profile Data
     * */
    fun setUiData(data: ProfileData) {

        profileData = data

        /* Reset Fields Data */
        firstNameField.set("")
        lastNameField.set("")
        alternatePhoneFirstNumberField.set("")
        alternatePhoneSecondNumberField.set("")

        data.firstName?.en?.let {
            firstNameField.set(it.trim())
        }

        data.lastName?.en?.let {
            lastNameField.set(it.trim())
        }

        data.dob?.let {
            dobField.set(it)
        }

        data.alternateNumber?.let {
            alternatePhoneFirstNumberField.set(it)
        }

        data.alternateNumber2?.let {
            alternatePhoneSecondNumberField.set(it)
        }
    }
}