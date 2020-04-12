package com.example.revolut_test.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.revolut_test.R
import com.example.revolut_test.data.model.CurrencyEntry
import com.example.revolut_test.data.model.RatesBook
import com.example.revolut_test.databinding.ItemCurrencyBinding
import com.example.revolut_test.utils.DEFAULT_BASE_VALUE
import com.example.revolut_test.utils.convertValueWithRate
import com.example.revolut_test.utils.listenForOnClick
import javax.inject.Inject


class CurrencyAdapter @Inject constructor(): RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {

    private lateinit var ratesBook: RatesBook
    private var lastInput: String = DEFAULT_BASE_VALUE
    private var currenciesList: MutableList<CurrencyEntry> = mutableListOf()
    private lateinit var onClickListener: (CurrencyEntry) -> Unit


    fun updateRates(ratesBook: RatesBook) {
        this.ratesBook = ratesBook
        updateDataSet()
    }

    fun setOnItemSelectedListener(listener: (CurrencyEntry) -> Unit) {
        onClickListener = listener
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (holder.adapterPosition == 0) {
            holder.itemView.requestFocus()
        }
    }

    override fun getItemCount(): Int {
        return currenciesList.size
    }


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
        holder.bind(currency)
        holder.listenForInputFocused {
            onItemClicked(currency)
        }
        holder.listenForOnClick { _, _, _ ->
            onItemClicked(currency)

        }
        holder.listenForInputChanged { input ->
            onInputChanged(input)
        }
    }

    class ViewHolder(private val binding: ItemCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val viewModel: CurrencyViewModel = CurrencyViewModel()

        fun bind(
            currencyEntry: CurrencyEntry
        ) {
            viewModel.bind(currencyEntry)
            binding.viewModel = viewModel
        }

        fun listenForInputChanged(onUserInputListener: (String) -> Unit) {
            viewModel.onUserInputListener = onUserInputListener
        }

        fun listenForInputFocused(
            onUserFocusListener: (View) -> Unit
        ) {
            viewModel.onUserFocusListener = {
                onUserFocusListener.invoke(itemView)
            }
        }
    }

    private fun updateDataSet() {
        if (currenciesList.size == 0) {
            this.currenciesList = buildCurrencyListFromRates(ratesBook, "1.00")
            notifyDataSetChanged()
        } else {
            this.currenciesList = updateListWithNewValues(ratesBook, lastInput)
            notifyItemRangeChanged(1, currenciesList.size - 1, {})
        }
    }

    private fun onItemClicked(
        currencyEntry: CurrencyEntry
    ) {
        val index = currenciesList.indexOfFirst { newCur -> newCur.code == currencyEntry.code }
        if (index > 0) {
            var oldFirst = currenciesList[0]

            val new = CurrencyEntry(currencyEntry.code, currencyEntry.value, true)
            val newSecond = CurrencyEntry(oldFirst.code, oldFirst.value)


            currenciesList.removeAt(index)
            currenciesList.add(0, new)
            currenciesList[1] = newSecond

            notifyItemMoved(index, 0)
            notifyItemChanged(0)
            notifyItemChanged(1)

            onClickListener.invoke(currencyEntry)
            onInputChanged(currencyEntry.value)
        }
    }

    private fun onInputChanged(input: String) {
        this.lastInput = input
    }


    private fun buildCurrencyListFromRates(ratesBook: RatesBook, baseValue: String): MutableList<CurrencyEntry> {
        val mutableList = mutableListOf<CurrencyEntry>()
        mutableList.add(CurrencyEntry(ratesBook.baseCurrency, baseValue, true))
        ratesBook.rates.forEach { (code, rate) ->
            mutableList.add(
                CurrencyEntry(
                    code,
                    convertValueWithRate(baseValue, rate)
                )
            )
        }
        return mutableList
    }


    private fun updateListWithNewValues(ratesBook: RatesBook, baseValue: String): MutableList<CurrencyEntry> {
        return currenciesList.mapIndexed { index, value ->
            if (index > 0) {
                CurrencyEntry(value.code, convertValueWithRate(baseValue, ratesBook.rates[value.code]))
            } else {
                value
            }
        }.toMutableList()
    }


}