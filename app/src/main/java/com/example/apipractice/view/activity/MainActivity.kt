package com.example.apipractice.view.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.apipractice.R
import com.example.apipractice.application.MyApplication
import com.example.apipractice.databinding.ActivityMainBinding
import com.example.apipractice.util.StorePreferences
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    lateinit var storePreferences: StorePreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        storePreferences = StorePreferences(this)
        observeData()

//        navController = Navigation.findNavController(this, R.id.fragment_container)

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
                R.id.homeFragment, R.id.profileFragment -> {
                    binding.bottomNavigationView.visibility = View.VISIBLE
                }
                R.id.loginFragment, R.id.splashFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                }
            }
        }

        /*
         * Bottom navigation back stack handling
         **/
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(navListener)


    }

    fun observeData() {

        val app = MyApplication.getApplication()
        storePreferences.readValue(StorePreferences.TOKEN).asLiveData().observe(this,
            {
                app.setToken(it)
            }
        )
        storePreferences.readValue(StorePreferences.USER_TYPE).asLiveData().observe(this,
            {
                app.setUserType(it)
            }
        )
        storePreferences.readValue(StorePreferences.DEMAND_PROFILE_DATA).asLiveData()
            .observe(this,
                {
                    if (it != null) {
                        app.setProfileData(it)
                    }
                }
            )
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