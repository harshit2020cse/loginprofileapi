package com.example.apipractice.view.fragments.profile.editprofile

import android.content.ContentValues
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.apipractice.application.MyApplication
import com.example.apipractice.data.ProfileData
import com.example.apipractice.networkcall.ProfileListener
import com.example.apipractice.repo.AuthRepository
import com.google.gson.JsonObject

class EditProfileVM : ViewModel() {

    var profileData: ProfileData? = null
    val app = MyApplication.getApplication()

    /* Ui Fields */
    val firstNameField = ObservableField("")
    val lastNameField = ObservableField("")
    val dobField = ObservableField("")
    val alternatePhoneFirstNumberField = ObservableField("")
    val alternatePhoneSecondNumberField = ObservableField("")

    /* Data Members */
    var profileListener: ProfileListener? = null

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
        Log.e(ContentValues.TAG, "response $jsonObject")

        /* Get API Response */
        val profileResponse = AuthRepository().updateUserProfile(jsonObject)
        profileListener?.onSuccess(profileResponse)
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