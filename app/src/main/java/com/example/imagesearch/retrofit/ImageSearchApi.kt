package com.example.imagesearch.retrofit

import com.example.imagesearch.Consts.API_MAX_RESULT
import com.example.imagesearch.Consts.AUTH_HEADER
import com.example.imagesearch.Consts.PAGE_NUMBER
import com.example.imagesearch.Consts.SORT_DEFAULT
import com.example.imagesearch.data.ImageSearch
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface ImageSearchApi {
    @GET("/v2/search/image")
    suspend fun getImage(
        @Header("Authorization") authorization: String = AUTH_HEADER,
        @Query("query") query: String,
        @Query("sort") sort: String = SORT_DEFAULT,
        @Query("page") page: Int = PAGE_NUMBER,
        @Query("size") size: Int = API_MAX_RESULT
    ): ImageSearch
}