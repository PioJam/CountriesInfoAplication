package com.example.countriesinfoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.countriesinfoapp.recycler.CountryAdapter
import kotlinx.android.synthetic.main.activity_main.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countriesinfoapp.api.Country
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private val viewModel: CountriesViewModel by lazy { ViewModelProviders.of(this).get(CountriesViewModel::class.java) }
    private val adapter by lazy { CountryAdapter(ArrayList(emptyList()),this::showCountryActivity) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupPicasso()

        countriesRecyclerView.layoutManager = LinearLayoutManager(this)
        countriesRecyclerView.adapter = adapter

        viewModel.countryList().observe(this, Observer(this::updateCountryList))
        viewModel.error().observe(this, Observer(this::showError))
        viewModel.refresh()

    }
    override fun onRestart() {
        super.onRestart()
        viewModel.refresh()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.imeOptions = EditorInfo.IME_ACTION_DONE

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
        return true
    }

    private fun updateCountryList(countryList: ArrayList<Country>?) {
        if (countryList == null) return
        adapter.items = countryList
        adapter.notifyDataSetChanged()
    }

    private fun showError(message: String?) {
        Log.d("MainActivity","showError: $message")
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }


    private fun showCountryActivity(country: Country) {
        val intent = Intent(this, DetailCountryInfoActivity::class.java)
            .putExtra(DetailCountryInfoActivity.EXTRA_KEY, country.countryCode)
        startActivity(intent)

    }
    private fun setupPicasso() {
        val picasso = Picasso.Builder(this)
            .build()
        Picasso.setSingletonInstance(picasso)
    }
}
