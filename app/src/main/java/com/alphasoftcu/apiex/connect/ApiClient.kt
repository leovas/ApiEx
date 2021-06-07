package com.alphasoftcu.apiex.connect

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Base URL to connect API Service
 */
const val BASE_URL = "https://jsonplaceholder.typicode.com/"

/**
 * Class client for API. Implement static function getApiClient
 * that return a Retrofit object prepared for request to API service
 */
class ApiClient {
    companion object{
        private var retrofit:Retrofit?=null

        /**
         * Build a Retrofit object configured for API's service connection
         * @return Retrofit object for connections to API
         */
        fun getApiClient(): Retrofit {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(100, TimeUnit.SECONDS)
                .connectTimeout(100, TimeUnit.SECONDS)
                .build()
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }

            return retrofit!!
        }
    }
}