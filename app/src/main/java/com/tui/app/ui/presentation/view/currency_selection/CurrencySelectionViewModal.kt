package com.tui.app.ui.presentation.view.currency_selection

import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.tui.app.R
import com.tui.app.network.modal.SelectionModal
import com.tui.app.network.response.CurrencyResponseModal
import com.tui.app.ui.presentation.component.AppBottomSheetModal
import com.tui.app.ui.presentation.component.CustomBottomSheet
import com.tui.app.utils.InternetConnectivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


const val PAGE_SIZE = 190

@HiltViewModel
class CurrencySelectionViewModal @Inject constructor(
    private val repository: CurrencySelectionRepository
) : ViewModel() {

 init {
     Log.e("TAG", ": init viewmodel constructor ", )
 }
    @OptIn(ExperimentalMaterialApi::class)
    var appSheetState = ModalBottomSheetState(ModalBottomSheetValue.Hidden)
    var allCurrencies = MutableStateFlow<CurrencyResponseModal?>(null)

    var appSheetModal = MutableStateFlow(AppBottomSheetModal())
    var selectCurrentModal = MutableStateFlow(SelectionModal())


    fun hitToGetCurrency(currency: String) {
        viewModelScope.launch {
            repository.getChallenges(currency).let {
                allCurrencies.value = it?.body()!!
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    fun chooseFromConversionCurrency(
        coroutineScope: CoroutineScope
    ) {

        appSheetModal.value = appSheetModal.value.copy {
            CustomBottomSheet(appSheetState) { data ->
                selectCurrentModal.value= selectCurrentModal.value.copy(fromCurrency = data)
                hitToGetCurrency(data)
                coroutineScope.launch {
                    appSheetState.hide()
                }
            }
        }
        coroutineScope.launch {
            appSheetState.show()
        }


    }
    @OptIn(ExperimentalMaterialApi::class)
    fun chooseToConversionCurrency(
        coroutineScope: CoroutineScope
    ) {

        appSheetModal.value = appSheetModal.value.copy {
            CustomBottomSheet(appSheetState) { data ->
                selectCurrentModal.value= selectCurrentModal.value.copy(toCurrency = data)
                coroutineScope.launch {
                    appSheetState.hide()
                }
            }
        }
        coroutineScope.launch {
            appSheetState.show()
        }


    }
    fun calculateCurrency(navController: NavController) {
        if (selectCurrentModal.value.amount.isEmpty())return
        val from = toJSONRate().get( selectCurrentModal.value.fromCurrency)
        val toRate = toJSONRate().get( selectCurrentModal.value.toCurrency)
        selectCurrentModal.value.rate = doubleFormat(toRate.asDouble).toDouble()
        val  amount = selectCurrentModal.value.amount
        val calc = ((amount.toDouble()) * toRate.asDouble)
        selectCurrentModal.value.conversionAmount = doubleFormat(calc)
        Log.e("TAG", "calculateCurrency find rate ${String.format(Locale.US, "%.2f", calc)} ")

        navController.navigate(R.id.currencyDetailFragment)
    }
    fun doubleFormat(rate: Double): String {
        return String.format(Locale.US, "%.2f", rate)
    }

    fun toJSONRate():JsonObject{
        allCurrencies.let {
            val data = Gson().toJson(allCurrencies.value, CurrencyResponseModal::class.java)
            return JsonParser.parseString(data).asJsonObject.getAsJsonObject("rates")
        }
    }

}

