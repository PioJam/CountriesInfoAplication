package com.example.countriesinfoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.countriesinfoapp.api.CountriesApi
import com.example.countriesinfoapp.api.Country
import kotlinx.android.synthetic.main.activity_detail_country_info.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailCountryInfoActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_KEY = "countryCode"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_country_info)

        val countryCode = intent.getStringExtra(EXTRA_KEY)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.eu/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(CountriesApi::class.java)

        api.getCountryDetails(countryCode).enqueue(object : Callback<Country> {
            override fun onFailure(call: Call<Country>, t: Throwable) {
            }
            override fun onResponse(call: Call<Country>, response: Response<Country>) {
                val countryDetails = response.body()
                inflateLayoutWithData(countryDetails)
            }
        })
    }

    private fun inflateLayoutWithData(countryDetails:Country?) {
        if (countryDetails != null) {
            countryNameValue.text = countryDetails.countryName
            capitalNameValue.text = countryDetails.capital
            regionNameValue.text = countryDetails.region
            populationFieldValue.text = countryDetails.population
            areaFieldValue.text = countryDetails.area+getString(R.string.units)
            coordinatesFieldValue.text = countryDetails.countryCoordinates.joinToString { it }
        }
    }

}
