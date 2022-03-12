package com.posse.android.moneyconverter.view.main

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.posse.android.moneyconverter.R
import com.posse.android.moneyconverter.databinding.MainFragmentLayoutBinding
import com.posse.android.moneyconverter.model.AppState
import com.posse.android.moneyconverter.model.data.Currency
import com.posse.android.moneyconverter.model.data.Response
import com.posse.android.moneyconverter.utils.NetworkStatus
import com.posse.android.moneyconverter.utils.syncTime
import com.posse.android.moneyconverter.view.main.adapter.Adapter
import com.posse.android.moneyconverter.view.main.viewModel.MainFragmentViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.*
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MainFragmentViewModel by lazy {
        viewModelFactory.create(MainFragmentViewModel::class.java)
    }

    private var _binding: MainFragmentLayoutBinding? = null
    private val binding get() = _binding!!

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private var adapter: Adapter? = null

    private var isOnline: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = MainFragmentLayoutBinding.inflate(inflater, container, false)
        .apply { _binding = this }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTopCard()
        val savedData = savedInstanceState?.getParcelableArray(screenDataKey)
        val adapterData = if (savedData?.isNotEmpty() == true && savedData.first() is Currency) {
            @Suppress("UNCHECKED_CAST")
            savedData as Array<Currency>
        } else null
        attachAdapter(adapterData)
        viewModel.getStateLiveData().observe(viewLifecycleOwner) { renderData(it) }
    }

    private fun attachAdapter(adapterData: Array<Currency>?) = with(binding) {
        if (adapter == null) {
            adapter = Adapter()
        } else {
            loadingLayout.visibility = View.INVISIBLE
            changeLoadingAnimation(false)
        }
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        if (adapterData?.isNotEmpty() == true) {
            restoreAdapter(adapterData)
            loadingLayout.visibility = View.INVISIBLE
            changeLoadingAnimation(false)
        } else {
            adapter?.clear()
            coroutineScope.launch {
                var timeout = 0L
                val delay = 50L
                while (isOnline == null && timeout < 1000) {
                    delay(delay)
                    timeout += delay
                }
                val online =
                    isOnline == true && System.currentTimeMillis() - sharedPreferences.syncTime > DAY
                withContext(Dispatchers.Main) { viewModel.getData(online) }
            }
        }
    }

    private fun restoreAdapter(adapterData: Array<Currency>) {
        adapter?.clear()
        adapterData.forEach {
            adapter?.setSingleData(it)
        }
    }

    private fun renderData(appState: AppState<*>) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                if (appState.data is Response) {
                    if (appState.data.Timestamp != null) sharedPreferences.syncTime =
                        System.currentTimeMillis()
                    if (appState.data.Valute.isEmpty()) {
                        recyclerCard.visibility = View.INVISIBLE
                    } else {
                        appState.data.Valute.forEach {
                            adapter?.setSingleData(it.value)
                        }
                        recyclerCard.visibility = View.VISIBLE
                    }
                    loadingLayout.visibility = View.INVISIBLE
                    changeLoadingAnimation(false)
                } else throw RuntimeException(getString(R.string.wrong_data) + " " + appState.data)
            }

            is AppState.Error -> {
                loadingLayout.visibility = View.INVISIBLE
                changeLoadingAnimation(false)
                handleErrorWithToast(appState.error)
            }

            AppState.Loading -> {
                recyclerCard.visibility = View.VISIBLE
                loadingLayout.visibility = View.VISIBLE
                changeLoadingAnimation(true)
            }
        }
    }

    private fun initTopCard() {
        setTimeAnimation()
        binding.topPanel.setOnClickListener {
            refreshPrice()
        }
    }

    private fun setTimeAnimation() {
        coroutineScope.launch {
            while (isActive) {
                val timeToShow = if (sharedPreferences.syncTime < 0) getString(R.string.never)
                else {
                    val timePassed = System.currentTimeMillis() - sharedPreferences.syncTime
                    val second: Long = timePassed / 1000 % 60
                    val minute: Long = timePassed / (1000 * 60) % 60
                    val hour: Long = timePassed / (1000 * 60 * 60) % 24
                    String.format("%02d:%02d:%02d", hour, minute, second)
                }
                withContext(Dispatchers.Main) { binding.updateTime.text = timeToShow }
            }
        }
    }

    private fun refreshPrice() {
        if (isOnline == true) {
            viewModel.getData(true)
        } else Toast.makeText(
            context,
            getString(R.string.offline),
            Toast.LENGTH_SHORT
        ).show()
    }

    @Inject
    fun initNetwork(networkStatus: NetworkStatus) {
        coroutineScope.launch {
            networkStatus
                .isOnline()
                .collect { isOnline = it }
        }
    }

    private fun handleErrorWithToast(e: Throwable?) = Toast
        .makeText(context, getString(R.string.error_happened) + " " + e, Toast.LENGTH_SHORT)
        .show()

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        adapter?.getData()?.let {
            outState.putParcelableArray(screenDataKey, it.toTypedArray())
        }
    }

    private fun changeLoadingAnimation(enable: Boolean) = with(binding) {
        if (enable) {
            if (!loadingBar.isIndeterminate) {
                loadingBar.visibility = View.INVISIBLE
                loadingBar.isIndeterminate = true
                loadingBar.visibility = View.VISIBLE
            }
        } else if (loadingBar.isIndeterminate)
            loadingBar.setProgressCompat(85, true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        coroutineScope.coroutineContext.cancelChildren()
        _binding = null
    }

    companion object {
        fun newInstance() = MainFragment()
        private const val screenDataKey = "KEY_DATA"
        private const val DAY = 86400000L
    }
}