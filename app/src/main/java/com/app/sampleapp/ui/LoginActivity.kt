package com.app.sampleapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.coroutineScope
import com.app.sampleapp.R
import com.app.sampleapp.databinding.ActivityLoginBinding
import com.app.sampleapp.utils.Resource
import com.app.sampleapp.utils.displayToast
import com.app.sampleapp.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val authViewModel: AuthViewModel by viewModels()

    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)


        binding.authContainer.btnSignIn.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignInActivity::class.java))
        }

        binding.authContainer.btnSignUp.setOnClickListener {
            with(binding.authContainer) {
                with(binding.authContainer) {
                    email = edtEmailID.text.toString()
                    password = edtPassword.text.toString()
                    authViewModel.signInUser(email, password)
                }

            }
        }

        authViewModel.userSignUpStatus.observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.authContainer.progressCircular.isVisible = true
                }
                is Resource.Success -> {
                    binding.authContainer.progressCircular.isVisible = false
                    startActivity(Intent(this@LoginActivity, DashActivity::class.java))

                }
                is Resource.Error -> {
                    binding.authContainer.progressCircular.isVisible = false
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        })
    }


}