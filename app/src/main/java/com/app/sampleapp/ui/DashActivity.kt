package com.app.sampleapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.coroutineScope
import com.app.sampleapp.R
import com.app.sampleapp.adapter.WeatherAdapter
import com.app.sampleapp.utils.NetworkResult
import com.app.sampleapp.databinding.ActivityDashBinding
import com.app.sampleapp.utils.displayToast
import com.app.sampleapp.viewmodel.AuthViewModel
import com.app.sampleapp.viewmodel.WeatherViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

import javax.inject.Inject


@AndroidEntryPoint
class DashActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashBinding

    private val authViewModel: AuthViewModel by viewModels()

    private val weatherViewModel: WeatherViewModel by viewModels()
    @Inject
    lateinit var Adapter: WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dash)

        authViewModel.getData().observe(this, {
            if (it!=null)
            {
                binding.progressCircular.visibility = View.GONE
                binding.mainContainer.visibility = View.VISIBLE
                binding.name.text = it.username
                binding.email.text = it.email
                binding.shortbio.text = it.shortbio
                Glide
                    .with(this@DashActivity)
                    .load(it.image)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(binding.profileImage);
            }

        })

        binding.weatherlist.adapter = Adapter
        weatherViewModel.weatherResponse.observe(this) {
            when(it) {
                is NetworkResult.Loading -> {
                    binding.progressCircular.visibility = View.VISIBLE
                }

                is NetworkResult.Failure -> {
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
                    binding.progressCircular.visibility = View.GONE
                }

                is  NetworkResult.Success -> {
                    Adapter.updateweather(it.data)
                    binding.progressCircular.visibility = View.GONE
                }
            }
        }

    }
}