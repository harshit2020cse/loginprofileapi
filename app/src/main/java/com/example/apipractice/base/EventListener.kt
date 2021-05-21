package com.example.apipractice.base


import android.location.Location
import androidx.lifecycle.MutableLiveData

class EventListener {

    /* Current Location */
    val currentLocationLD = MutableLiveData<Location?>()
    private var currentLocation: Location? = null

    /* For Activity UI Components */
    private val showHideBottomNavigationBarLD = MutableLiveData<Boolean>()
    private val showHideToolBarLD = MutableLiveData<BaseModel>()


    fun reset() {
        /*showHideBottomNavigationBarLD.value = null
        showHideToolBarLD.value = null*/
        currentLocationLD.value = null
        currentLocation = null
    }

    /**
     * Store Current Location
     * */
    fun setCurrentLocation(location: Location) {
        currentLocation = location
        currentLocationLD.postValue(location)
    }

    /**
     * Return Current Location
     * */
    fun getCurrentLocation(): Location? = currentLocation

    /**
     * Notify to Update Bottom Navigation Bar Visibility Status
     * */
    fun showBottomNavigationBar(show: Boolean) {
        showHideBottomNavigationBarLD.postValue(show)
    }

    /**
     * @return Bottom Navigation Bar Visibility Live Data
     * */
    fun getBottomNavigationVisibilityLD() = showHideBottomNavigationBarLD

    /**
     * Notify to Update Tool Bar Visibility Status
     * */
    fun showToolBar(show: BaseModel) {
        showHideToolBarLD.postValue(show)
    }


    /**
     * Notify to Update Tool Bar Visibility Status
     * */
    fun hideToolBar(show: BaseModel) {

    }

    /**
     * @return Tool Bar Visibility Live Data
     * */
    fun getToolBarVisibilityLD() = showHideToolBarLD

}