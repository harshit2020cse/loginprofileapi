package com.example.apipractice.view.fragments.profile.editprofile

import android.content.ContentValues
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.apipractice.AppConstant
import com.example.apipractice.application.MyApplication
import com.example.apipractice.base.BaseModel
import com.example.apipractice.data.ProfileData
import com.example.apipractice.networkcall.ProfileListener
import com.example.apipractice.repo.AuthRepository
import com.google.gson.JsonObject

class EditProfileVM : ViewModel() {

    /* Selected Sign Up Type */
    var signUpType = ObservableField(AppConstant.USER_TYPE.PATIENT)
    var profileData: ProfileData? = null
    val app = MyApplication.getApplication()

    /**
     * Signup Basic
     * */
    /* Ui Fields */
    val firstNameField = ObservableField("")
    val isValidFirstName = ObservableField(BaseModel(true, ""))
    val lastNameField = ObservableField("")
    val isValidLastName = ObservableField(BaseModel(true, ""))
    val dobField = ObservableField("")
    val alternatePhoneFirstNumberField = ObservableField("")
    val isValidAlternatePhoneNumberFirst = ObservableField(BaseModel(true, ""))
    val alternatePhoneSecondNumberField = ObservableField("")
    val isValidAlternatePhoneNumberSecond = ObservableField(BaseModel(true, ""))

    /* Data Members */
    var profileListener: ProfileListener? = null


    fun updateProfileData() {
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