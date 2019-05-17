package com.example.countriesinfoapp.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.countriesinfoapp.api.Country
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.country_recycler_view_item.view.*

class CountryViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    private val nameView = itemView.mainCountryName
    private val imageView = itemView.imageView

    fun bindItem(countryItem: Country, onItemClicked: (Country) -> Unit) {
        nameView.text = countryItem.countryName
        Picasso.get()
            .load(countryItem.flag)
            .into(imageView)
        itemView.setOnClickListener { onItemClicked(countryItem) }
    }
}