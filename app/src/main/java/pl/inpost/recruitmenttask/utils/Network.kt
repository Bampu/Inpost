package pl.inpost.recruitmenttask.utils

import pl.inpost.recruitmenttask.data.source.remote.ShipmentApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://your.api.base.url/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ShipmentApi by lazy {
        retrofit.create(ShipmentApi::class.java)
    }
}