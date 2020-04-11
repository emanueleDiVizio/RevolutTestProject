package com.example.revolut_test.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.revolut_test.R
import com.example.revolut_test.data.model.Currency
import com.example.revolut_test.data.model.Rates
import com.example.revolut_test.databinding.ItemCurrencyBinding
import com.example.revolut_test.utils.listenForOnClick


class CurrencyAdapter(private val ratesLiveData: MutableLiveData<Rates>,
                      private val userInputLiveData: MutableLiveData<String>): RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {
    private lateinit var currenciesList: List<Currency>
    private lateinit var onClickListener: (Currency) -> Unit



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCurrencyBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_currency,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currency = currenciesList[position]
        holder.bind(currency, ratesLiveData, userInputLiveData)
//        holder.listenForOnClick { _, _ -> onClickListener.invoke(currency) }
    }

    override fun getItemCount(): Int {
        return if (::currenciesList.isInitialized) currenciesList.size else 0
    }

    fun updateRates( currenciesList: List<Currency>) {
        this.currenciesList = currenciesList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemCurrencyBinding) :  RecyclerView.ViewHolder(binding.root) {
        private val viewModel: CurrencyViewModel = CurrencyViewModel()
        fun bind(currency: Currency, ratesLiveData: MutableLiveData<Rates>,
                 userInputLiveData: MutableLiveData<String>) {
            viewModel.bind(currency, ratesLiveData, userInputLiveData)
            binding.viewModel = viewModel
        }
    }

    fun setOnItemSelectedListener(listener: (Currency) -> Unit){
        onClickListener = listener
    }



}