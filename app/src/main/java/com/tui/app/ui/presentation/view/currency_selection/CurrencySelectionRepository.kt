package com.tui.app.ui.presentation.view.currency_selection

import android.content.Context
import com.tui.app.network.ApiService
import com.tui.app.network.response.CurrencyResponseModal
import com.tui.app.utils.ConnectionState
import com.tui.app.utils.InternetConnectivity
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.Response

class CurrencySelectionRepository(private val apiService: ApiService) {


    suspend fun getChallenges(currency:String): Response<CurrencyResponseModal>? {
        return apiService.getCurrency(currency)
    }
}