package com.example.countriesinfoapp.api

import android.util.Log
import com.example.countriesinfoapp.recycler.CountryAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CountryFetcher {

    private val retrofit = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val countriesApi = retrofit.create(CountriesApi::class.java)

    fun fetchAll(onSuccess: (ArrayList<Country>) -> Unit, onError: (String) -> Unit) {
        val call = countriesApi.getCountryList()
        call.enqueue(object : Callback<ArrayList<Country>> {
            override fun onFailure(call: Call<ArrayList<Country>>, t: Throwable) {
                onError(t.message ?: "Unknown error")
            }

            override fun onResponse(call: Call<ArrayList<Country>>, response: Response<ArrayList<Country>>) {
                if (response.isSuccessful) {
                    val countryList = response.body() ?: return onError("Something went wrong")
                    onSuccess(countryList)
                    CountryAdapter.itemsFull = ArrayList(countryList)
                } else {
                    onError(response.message())
                }
            }
        })
    }
    companion object {
        const val API_BASE_URL = "https://restcountries.eu/"
    }
}