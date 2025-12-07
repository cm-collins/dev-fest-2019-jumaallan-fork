package com.androidstudy.weather.ui.views.ui.views.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.androidstudy.devfest19.core.livedata.nonNull
import com.androidstudy.devfest19.core.livedata.observe
import com.androidstudy.devfest19.core.toast
import com.androidstudy.weather.R
import com.androidstudy.weather.ui.views.models.WeatherResponseModel
import com.androidstudy.weather.ui.views.ui.viewmodels.WeatherViewModel
import com.androidstudy.weather.ui.views.utils.toDate

class WeatherFragment : Fragment(R.layout.fragment_weather) {
    private val weatherViewModel: WeatherViewModel by viewModels()
    
    private lateinit var address: TextView
    private lateinit var updated_at: TextView
    private lateinit var status: TextView
    private lateinit var temp: TextView
    private lateinit var temp_min: TextView
    private lateinit var temp_max: TextView
    private lateinit var wind: TextView
    private lateinit var pressure: TextView
    private lateinit var humidity: TextView
    private lateinit var sunrise: TextView
    private lateinit var sunset: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        address = view.findViewById(R.id.address)
        updated_at = view.findViewById(R.id.updated_at)
        status = view.findViewById(R.id.status)
        temp = view.findViewById(R.id.temp)
        temp_min = view.findViewById(R.id.temp_min)
        temp_max = view.findViewById(R.id.temp_max)
        wind = view.findViewById(R.id.wind)
        pressure = view.findViewById(R.id.pressure)
        humidity = view.findViewById(R.id.humidity)
        sunrise = view.findViewById(R.id.sunrise)
        sunset = view.findViewById(R.id.sunset)

        fetchWeather()
        observeLiveData()
    }

    private fun fetchWeather() {
        weatherViewModel.fetchWeather(
            "Nairobi,ke",
            "metric",
            com.androidstudy.devfest19.BuildConfig.openMapApiKey
        )
    }

    private fun observeLiveData() {
        weatherViewModel.getWeatherResponse().nonNull().observe(this) { weatherResponseModel ->
            setupViews(weatherResponseModel)
        }
        weatherViewModel.getWeatherError().nonNull().observe(this) {
            requireActivity().toast(it)
        }
    }

    private fun setupViews(weatherResponseModel: WeatherResponseModel) {
        address.text = "${weatherResponseModel.name},${weatherResponseModel.sys.country}"
        status.text = weatherResponseModel.weather[0].description
        updated_at.text = "Updated at : ${weatherResponseModel.dt.toDate("dd/MM/yyyy hh:mm a")}"
        sunrise.text = weatherResponseModel.sys.sunrise.toDate("hh:mm a")
        sunset.text = weatherResponseModel.sys.sunset.toDate("hh:mm a")
        temp.text = "${weatherResponseModel.main.temp} °C"
        temp_min.text = "Min Temp: ${weatherResponseModel.main.temp_min}°C"
        temp_max.text = "Max Temp: ${weatherResponseModel.main.temp_max}°C"
        wind.text = weatherResponseModel.wind.speed.toString()
        pressure.text = weatherResponseModel.main.pressure.toString()
        humidity.text = weatherResponseModel.main.humidity.toString()

    }
}