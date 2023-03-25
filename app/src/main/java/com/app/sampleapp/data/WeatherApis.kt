package com.app.sampleapp.data


import com.app.sampleapp.data.model.WeatherModel.WeatherResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApis {

    @Headers(
        "X-RapidAPI-Key: 99e0060833msh8d8f97aa63161edp1ad3e8jsn3296c085afdd",
        "X-RapidAPI-Host: weatherbit-v1-mashape.p.rapidapi.com")
    @GET("3hourly?")
    suspend fun getWeatherLIst(@Query("lat") lat : String ,@Query("lon") lon : String): WeatherResponseModel
}