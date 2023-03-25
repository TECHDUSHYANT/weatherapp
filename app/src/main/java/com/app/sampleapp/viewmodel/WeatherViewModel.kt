package com.app.sampleapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.sampleapp.data.model.WeatherModel.Data
import com.app.sampleapp.repository.WeatherRepository
import com.app.sampleapp.utils.NetworkResult

import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject
constructor(
    private var weatherRepository: WeatherRepository,
) : ViewModel()
{
    private var _weatherResponse = MutableLiveData<NetworkResult<List<Data>>>()
    val weatherResponse: LiveData<NetworkResult<List<Data>>> = _weatherResponse



    init {
        getweatherlist()
    }

    private fun getweatherlist() {
        viewModelScope.launch {
            weatherRepository.getWeatherList().collect {
                _weatherResponse.postValue(it)
            }
        }
    }


}