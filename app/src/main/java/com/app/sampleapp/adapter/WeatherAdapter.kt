package com.app.sampleapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.sampleapp.data.model.WeatherModel.Data
import com.app.sampleapp.databinding.RowWeatherBinding
import javax.inject.Inject

class WeatherAdapter @Inject constructor() : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    var weather = mutableListOf<Data>()
    private var clickInterface: ClickInterface<Data>? = null

    fun updateweather(weather: List<Data>) {
        this.weather = weather.toMutableList()
        notifyItemRangeInserted(0, weather.size)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RowWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val row = weather[position]
        holder.view.date.text = "Current Date : " + row.datetime
        holder.view.temp.text = "Temp : "+ row.appTemp.toString()
        holder.view.windspd.text = "Wind Speed : "+ row.windSpd.toString()

        holder.view.Card.setOnClickListener {
            clickInterface?.onClick(row)
        }
    }

    override fun getItemCount(): Int {
        return weather.size
    }

    fun setItemClick(clickInterface: ClickInterface<Data>) {
        this.clickInterface = clickInterface
    }

    class ViewHolder(val view: RowWeatherBinding) : RecyclerView.ViewHolder(view.root)
}

interface ClickInterface<T> {
    fun onClick(data: T)
}