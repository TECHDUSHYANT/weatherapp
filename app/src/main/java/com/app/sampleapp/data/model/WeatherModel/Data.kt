package com.app.sampleapp.data.model.WeatherModel

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("app_temp"        ) var appTemp        : Double?  = null,
  @SerializedName("pop"             ) var pop            : Int?     = null,
  @SerializedName("ozone"           ) var ozone          : Double?  = null,
  @SerializedName("dni"             ) var dni            : Double?  = null,
  @SerializedName("clouds_low"      ) var cloudsLow      : Int?     = null,
  @SerializedName("clouds_mid"      ) var cloudsMid      : Int?     = null,
  @SerializedName("snow_depth"      ) var snowDepth      : Int?     = null,
  @SerializedName("wind_cdir"       ) var windCdir       : String?  = null,
  @SerializedName("rh"              ) var rh             : Int?     = null,
  @SerializedName("pod"             ) var pod            : String?  = null,
  @SerializedName("pres"            ) var pres           : Double?     = null,
  @SerializedName("clouds"          ) var clouds         : Int?     = null,
  @SerializedName("vis"             ) var vis            : Double?  = null,
  @SerializedName("wind_spd"        ) var windSpd        : Double?  = null,
  @SerializedName("wind_cdir_full"  ) var windCdirFull   : String?  = null,
  @SerializedName("slp"             ) var slp            : Double?  = null,
  @SerializedName("datetime"        ) var datetime       : String?  = null,
  @SerializedName("ts"              ) var ts             : Int?     = null,
  @SerializedName("snow"            ) var snow           : Int?     = null,
  @SerializedName("solar_rad"       ) var solarRad       : Double?  = null,
  @SerializedName("timestamp_local" ) var timestampLocal : String?  = null,
  @SerializedName("wind_gust_spd"   ) var windGustSpd    : Double?  = null,
  @SerializedName("uv"              ) var uv             : Double?  = null,
  @SerializedName("wind_dir"        ) var windDir        : Int?     = null,
  @SerializedName("ghi"             ) var ghi            : Double?  = null,
  @SerializedName("dhi"             ) var dhi            : Double?  = null,
  @SerializedName("precip"          ) var precip         : Int?     = null,
  @SerializedName("clouds_hi"       ) var cloudsHi       : Int?     = null,
  @SerializedName("weather"         ) var weather        : Weather? = Weather(),
  @SerializedName("timestamp_utc"   ) var timestampUtc   : String?  = null,
  @SerializedName("temp"            ) var temp           : Double?  = null,
  @SerializedName("dewpt"           ) var dewpt          : Double?  = null

)