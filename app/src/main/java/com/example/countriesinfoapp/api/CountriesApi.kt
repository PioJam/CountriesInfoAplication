package com.example.countriesinfoapp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CountriesApi {

    @GET("/rest/v2/all")
    fun getCountryList(): Call<ArrayList<Country>>

    @GET("/rest/v2/alpha/{countryCode}")
    fun getCountryDetails(@Path("countryCode")countryCode:String): Call<Country>
}