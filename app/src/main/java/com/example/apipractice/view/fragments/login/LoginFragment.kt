package com.example.apipractice.view.fragments.login

import `in`.sarangal.lib.spantastic.Spantastic
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.apipractice.R
import com.example.apipractice.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    lateinit var loginViewModel: LoginViewModel

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.viewModel = loginViewModel
        binding.executePendingBindings()

        setClickListener()

        spanString()
    }

    private fun setClickListener() {

        binding.loginButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
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