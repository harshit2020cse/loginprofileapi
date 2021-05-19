package com.example.apipractice.view.activity

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.apipractice.R
import com.example.apipractice.application.MyApplication
import com.example.apipractice.databinding.ActivityMainBinding
import com.example.apipractice.util.StorePreferences
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    lateinit var storePreferences: StorePreferences

    /** Auth Token - Used in API Authorization */
    private var authToken: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        storePreferences = StorePreferences(this)
        observeData()

        /**
         * To Hide or Show from Bottom navigation View for specific fragments
         **/
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController


        binding.bottomNavigationView.setupWithNavController(navController)

        /**
         * BottomNavigation visibility set
         */
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> {
                    binding.bottomNavigationView.visibility = View.VISIBLE
                }
                R.id.profileFragment -> {
                    binding.bottomNavigationView.visibility = View.VISIBLE
                }
                R.id.loginFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                }
            }
        }

        /*
         * Bottom navigation back stack handling
         **/
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(navListener)


    }

    private fun observeData() {
        val app = MyApplication()
        app.getApplication()
        storePreferences.getToken.asLiveData().observe(this, {
            app.setToken(it)
            authToken = it
        })
        storePreferences.getUser.asLiveData().observe(this, {
            app.setUserType(it)
            Log.e("Get User", "profile ${app.getUserType()}")
        })
    }


    /**
     * Bottom navigation back stack handling
     **/
    private val navListener: BottomNavigationView.OnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {

                when (item.itemId) {
                    R.id.findFragment ->
                        if (navController.currentDestination?.id != R.id.findFragment) {
                            navController.navigate(R.id.findFragment)
                            return true
                        }
                    R.id.homeFragment ->
                        if (navController.currentDestination?.id != R.id.homeFragment) {
                            navController.navigate(R.id.homeFragment)
                        }
                    R.id.profileFragment ->
                        if (navController.currentDestination?.id != R.id.profileFragment) {
                            navController.navigate(R.id.profileFragment)
                        }
                }
                return true
            }
        }


}