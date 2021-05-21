package com.example.apipractice.view.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.apipractice.R
import com.example.apipractice.application.MyApplication
import com.example.apipractice.databinding.ActivityMainBinding
import com.example.apipractice.util.StorePreferences
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    lateinit var storePreferences: StorePreferences
    lateinit var viewModel: MainActivityVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        storePreferences = StorePreferences(this)
        viewModel = ViewModelProvider(this).get(MainActivityVM::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        observeData()

        /**
         * To Hide or Show from Bottom navigation View for specific fragments
         **/
        navController = Navigation.findNavController(this, R.id.fragment_container)

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        /**
         * BottomNavigation visibility set
         */
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment, R.id.splashFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                    binding.appBarLayout.visibility = View.GONE
                }
                R.id.homeFragment -> {
                    binding.bottomNavigationView.visibility = View.VISIBLE
                    binding.appBarLayout.visibility = View.VISIBLE
                    setToolBarTitle(viewModel.resourceProvider.getString(R.string.home))
                }
                R.id.profileFragment -> {
                    binding.bottomNavigationView.visibility = View.VISIBLE
                    binding.appBarLayout.visibility = View.VISIBLE
                    setToolBarTitle(viewModel.resourceProvider.getString(R.string.my_profile))
                }
                R.id.editProfileFragment -> {
                    binding.bottomNavigationView.visibility = View.VISIBLE
                    binding.appBarLayout.visibility = View.VISIBLE
                    setToolBarTitle(viewModel.resourceProvider.getString(R.string.edit_profile))
                }
            }
        }

        /**
         * Bottom navigation back stack handling
         **/
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(navListener)

    }

    /** Set Observer to capture actions */
    private fun observeData() {

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
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment ->
                    if (navController.currentDestination?.id != R.id.homeFragment) {
                        navController.navigate(R.id.homeFragment)
                    }
                R.id.profileFragment ->
                    if (navController.currentDestination?.id != R.id.profileFragment) {
                        navController.navigate(R.id.profileFragment)
                    }
            }
            true
        }

    /**
     * Change Toolbar Title
     * */
    private fun setToolBarTitle(title: String) {
        viewModel.toolBarTitle.set(title.trim().capitalize(Locale.ROOT))
    }


}