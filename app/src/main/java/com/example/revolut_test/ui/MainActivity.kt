package com.example.revolut_test.ui

import android.os.Bundle
import android.util.Log.d
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.revolut_test.R
import com.example.revolut_test.databinding.ActivityMainBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: RatesViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?){
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(RatesViewModel::class.java)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.currenciesList.layoutManager = layoutManager
        binding.viewModel = viewModel
        viewModel.subscribeToRates()

        viewModel.setOnItemClickListener{
            binding.currenciesList.scrollToPosition(0) }
    }
}
