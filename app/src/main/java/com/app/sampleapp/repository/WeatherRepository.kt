package com.app.sampleapp.repository


import com.app.sampleapp.data.WeatherApis
import com.app.sampleapp.utils.NetworkResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class WeatherRepository  @Inject constructor( private val weatherApis: WeatherApis){

    suspend fun getWeatherList()  = flow {
        emit(NetworkResult.Loading(true))
        val response = weatherApis.getWeatherLIst("28.6","77.2")
        emit(NetworkResult.Success(response.data))
    }.catch { e ->
        emit(NetworkResult.Failure(e.message ?: "Unknown Error"))
    }



}