package com.app.sampleapp.data.model.WeatherModel

import com.google.gson.annotations.SerializedName


data class Weather (

  @SerializedName("description" ) var description : String? = null,
  @SerializedName("code"        ) var code        : Int?    = null,
  @SerializedName("icon"        ) var icon        : String? = null

)