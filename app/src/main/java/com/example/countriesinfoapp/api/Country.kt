package com.example.countriesinfoapp.api

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("name")
    val countryName: String,
    @SerializedName("alpha3Code")
    val countryCode: String,
    @SerializedName("latlng")
    val countryCoordinates: ArrayList<String>,
    val flag: String,
    val capital: String,
    val region: String,
    val population: String,
    val area: String
)