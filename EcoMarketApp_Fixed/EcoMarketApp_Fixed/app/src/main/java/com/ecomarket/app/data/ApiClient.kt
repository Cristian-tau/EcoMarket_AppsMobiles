//1
package com.ecomarket.app.data
//2
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//3
object ApiClient {
    //4
    private const val BASE_URL = "http://192.168.0.100:3000"
    //5
    private val log = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    //6
    private val http = OkHttpClient.Builder().addInterceptor(log).build()
    //7
    val api: ApiService = Retrofit.Builder().baseUrl(BASE_URL).client(http).addConverterFactory(GsonConverterFactory.create()).build().create(ApiService::class.java)
}
//8
