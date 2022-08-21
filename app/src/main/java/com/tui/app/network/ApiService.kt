package com.tui.app.network

import com.tui.app.network.response.CurrencyResponseModal
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("latest/{currency}")
    suspend fun getCurrency(@Path("currency") currency: String): Response<CurrencyResponseModal>?
}