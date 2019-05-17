package com.example.countriesinfoapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.countriesinfoapp.api.Country
import com.example.countriesinfoapp.api.CountryFetcher

class CountriesViewModel:ViewModel() {
    private val countryFetcher = CountryFetcher()

    private val countryLiveData = MutableLiveData<ArrayList<Country>>()
    private val errorLiveData = MutableLiveData<String>()

    fun countryList(): LiveData<ArrayList<Country>> = countryLiveData
    fun error(): LiveData<String> = errorLiveData

    fun refresh() {
        countryFetcher.fetchAll({
            countryLiveData.value = it
        }, {
            errorLiveData.setValue(it)
        })
    }
}