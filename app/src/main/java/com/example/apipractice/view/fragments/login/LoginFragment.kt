package com.example.apipractice.view.fragments.login

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.apipractice.R
import com.example.apipractice.application.AppConstant
import com.example.apipractice.databinding.FragmentLoginBinding
import com.example.apipractice.util.StorePreferences
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {

    /* Binding Layout View */
    lateinit var binding: FragmentLoginBinding

    /* Login ViewModel */
    lateinit var viewModel: LoginVM

    /* StorePreferences to Store Data */
    lateinit var storePreferences: StorePreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false
        )
        viewModel = ViewModelProvider(this).get(LoginVM::class.java)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* Initialize */
        initView()

        /* Set Observers */
        bindObservers()

        /* Set CLick Listener*/
        setClickListener()
    }

    /**
     * Initialize
     * */
    private fun initView() {
        storePreferences = StorePreferences(requireContext())
    }

    /** Set Observers to capture actions */
    private fun bindObservers() {
        when (arguments?.getString(AppConstant.AUTHENTICATION_KEYS.KEY)) {
            AppConstant.AUTHENTICATION_KEYS.LOGOUT ->
                Snackbar.make(
                    requireContext(),
                    binding.mainLayout,
                    resources.getString(R.string.logged_out),
                    Snackbar.LENGTH_SHORT
                ).show()
        }

        viewModel.loginResponse.observe(viewLifecycleOwner, {

            Log.e(TAG, "Response $it")

            /* SnackBar Login Message*/
            Snackbar.make(requireContext(), binding.mainLayout, it.message, Snackbar.LENGTH_SHORT)
                .show()
            /* Navigate to Home Screen */
            if (it.data?.userType == AppConstant.USER_TYPE.PATIENT) {
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        })


    }

    /** Set Click Listeners */
    private fun setClickListener() {
        binding.loginButton.setOnClickListener {
            viewModel.setLogin()
        }
    }


}