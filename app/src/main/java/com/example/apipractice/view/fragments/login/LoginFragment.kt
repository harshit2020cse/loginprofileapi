package com.example.apipractice.view.fragments.login

import `in`.sarangal.lib.spantastic.Spantastic
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.apipractice.R
import com.example.apipractice.databinding.FragmentLoginBinding
import com.example.apipractice.util.StorePreferences
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    lateinit var viewModel: LoginViewModel
    lateinit var storePreferences: StorePreferences
    var token = ""
    var user=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_login,
            container,
            false
        )
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storePreferences = StorePreferences(requireContext())
        GlobalScope.launch {
            storePreferences.setToken(viewModel.token.get().toString())
            storePreferences.setUser(viewModel.userType.get().toString())

        }
        storePreferences.getUser.asLiveData().observe(requireActivity(), {
            user = it
            if(it == "PATIENT"){
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
            Log.e(ContentValues.TAG, "store ${it} ")

        })

        observeData()

        setClickListener()

        spanString()
    }

    private fun setClickListener() {

        binding.loginButton.setOnClickListener {
            loginUser()
//            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }

    fun loginUser() {
        /* Check Username */
        if (viewModel.usernameField.get()?.trim().isNullOrEmpty()) {
            /* Notify User */
            binding.uniqueIdEditText.error = "Please enter MedoPlus Id"
            binding.passwordEditText.error = null
            return
        }

        viewModel.usernameField.set(viewModel.usernameField.get()?.trim())

        /* Check Password */
        if (viewModel.passwordField.get().isNullOrEmpty()) {
            /* Notify User */
            binding.passwordEditText.error = "Please enter Password"
            binding.uniqueIdEditText.error = null
            return
        }
        if (viewModel.passwordField.get()?.length!! < 8) {
            binding.passwordEditText.error = "Invalid Password"
            binding.uniqueIdEditText.error = null
            return
        }

        viewModel.usernameField.set(viewModel.usernameField.get()?.trim())

        /* Login Button View Click */

        viewModel.performLogin()

    }

    fun observeData() {
        storePreferences.getToken.asLiveData().observe(requireActivity(), {
            token = it
            Log.e(ContentValues.TAG, "store ${it} ")

        })
        storePreferences.getUser.asLiveData().observe(requireActivity(), {
            user = it
            Log.e(ContentValues.TAG, "store ${it} ")

        })
    }

    /**
     * Set Spannable Texts
     * */
    private fun spanString() {
        binding.termConditionsTextView.let {
            val termConditionKey = resources.getString(R.string.terms_condition_key)
            val privacyPolicyKey = resources.getString(R.string.privacy_policy_key)
            Spantastic.SpantasticBuilder(requireContext(), it, it.text.toString())
                .setSpanList(listOf(termConditionKey, privacyPolicyKey))
                .setSpanColor(resources.getColor(R.color.colorOnBlue))
                .setClickCallback { spanString, _ ->
                    when (spanString) {
                        termConditionKey -> {

                        }
                        privacyPolicyKey -> {

                        }
                    }
                }
                .apply()
        }
    }
}