package com.example.apipractice.view.fragments.login

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.apipractice.application.AppConstant
import com.example.apipractice.R
import com.example.apipractice.application.MyApplication
import com.example.apipractice.data.LoginModel
import com.example.apipractice.databinding.FragmentLoginBinding
import com.example.apipractice.networkcall.AuthListener
import com.example.apipractice.util.StorePreferences
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginFragment : Fragment(), AuthListener {

    lateinit var binding: FragmentLoginBinding
    lateinit var viewModel: LoginVM
    val app = MyApplication()
    lateinit var storePreferences: StorePreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false
        )
        viewModel = ViewModelProvider(this).get(LoginVM::class.java)
        binding.viewModel = viewModel
        viewModel.authListener = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storePreferences = StorePreferences(requireContext())
        when (arguments?.getString(AppConstant.AUTHENTICATION_KEYS.KEY)) {
            AppConstant.AUTHENTICATION_KEYS.LOGOUT ->
                Snackbar.make(
                    requireContext(),
                    binding.mainLayout,
                    resources.getString(R.string.logged_out),
                    Snackbar.LENGTH_SHORT
                ).show()
        }

        /* Set CLick Listener*/
        setClickListener()
    }

    /** Set Click Listeners */
    private fun setClickListener() {
        binding.loginButton.setOnClickListener {
            viewModel.setLogin()
        }
    }

    /* Get API Success Response */
    override fun onSuccess(loginResponse: LiveData<LoginModel>) {
        loginResponse.observe(this, {

            //TODO Use Coroutines in ViewModel

            /** Store TOKEN in DataStore*/
            GlobalScope.launch {
                it.data?.token?.let { it1 ->
                    storePreferences.storeValue(
                        StorePreferences.TOKEN,
                        it1
                    )
                }
                viewModel.app.setToken(it.data?.token)

                /** Store USER_TYPE in DataStore*/
                it.data?.userType?.let { it1 ->
                    storePreferences.storeValue(
                        StorePreferences.USER_TYPE,
                        it1
                    )
                }
            }

            Log.e(TAG, "Response $it")

            /** SnackBar Login Message*/
            Snackbar.make(requireContext(), binding.mainLayout, it.message, Snackbar.LENGTH_SHORT)
                .show()
            /* Navigate to Home Screen */
            if (it.data?.userType == AppConstant.USER_TYPE.PATIENT) {
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        })
    }


}