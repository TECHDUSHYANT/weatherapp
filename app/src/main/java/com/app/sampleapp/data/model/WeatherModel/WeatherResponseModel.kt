package com.app.sampleapp.data.model.WeatherModel

import com.google.gson.annotations.SerializedName


data class WeatherResponseModel (

  @SerializedName("data") var data : List<Data>

)

