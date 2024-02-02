package com.example.imagesearch.retrofit

import com.example.imagesearch.Consts.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val imageSearchApi: ImageSearchApi by lazy {
        retrofit.create(ImageSearchApi::class.java)
    }
}