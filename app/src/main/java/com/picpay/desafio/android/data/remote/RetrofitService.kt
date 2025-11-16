package com.picpay.desafio.android.data.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {
    companion object {
        const val BASE_URL = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"

        private val gson: Gson by lazy { GsonBuilder().create() }

        private val okHttp: OkHttpClient by lazy {
            OkHttpClient.Builder()
                .build()
        }

        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttp)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }

        @JvmStatic
        val apiService: PicPayService
            get() = retrofit.create(PicPayService::class.java)
    }
}
