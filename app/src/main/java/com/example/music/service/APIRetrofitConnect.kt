package com.example.music.service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

 class APIRetrofitConnect {

    companion object {
        private lateinit var mRetrofit: Retrofit
        @JvmStatic
        public fun getClient(url: String): Retrofit {
            var okHttpClient: OkHttpClient = OkHttpClient.Builder()
                .readTimeout(3000, TimeUnit.MILLISECONDS)
                .writeTimeout(3000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .build()
            var gson: Gson = GsonBuilder().setLenient().create()
            mRetrofit = Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return mRetrofit
        }
    }
}