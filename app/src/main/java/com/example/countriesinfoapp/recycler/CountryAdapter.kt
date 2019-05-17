package com.example.countriesinfoapp.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.countriesinfoapp.R
import com.example.countriesinfoapp.api.Country



class CountryAdapter(
    var items: ArrayList<Country>,
    private val onItemClicked: (Country) -> Unit
): RecyclerView.Adapter<CountryViewHolder>(), Filterable {

    companion object {
        var itemsFull: ArrayList<Country> = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.country_recycler_view_item,parent,false)
        return CountryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bindItem(items.get(position), onItemClicked)
    }

    override fun getFilter(): Filter {
        return filter
    }
    private val filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {

            var filteredList :ArrayList<Country> = ArrayList()

            if (constraint == null || constraint.length == 0) {
                filteredList = ArrayList(itemsFull)
            } else {
                val filterPattern = constraint.toString().toLowerCase().trim { it <= ' ' }

                for (item in itemsFull) {
                    if (item.countryName.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            items.clear()
            items.addAll(results.values as ArrayList<Country>)
            notifyDataSetChanged()
        }
    }

}
