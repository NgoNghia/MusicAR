package mieubongcity.music.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class APIRetrofitConnect {

    companion object {
        @JvmStatic
        public fun getClient(url: String): Retrofit {
            var okHttpClient: OkHttpClient = OkHttpClient.Builder()
                .readTimeout(10000, TimeUnit.MILLISECONDS)
                .writeTimeout(10000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .build()
            var gson: Gson = GsonBuilder().setLenient().create()

            val mRetrofit: Retrofit = Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            return mRetrofit
        }
    }
}