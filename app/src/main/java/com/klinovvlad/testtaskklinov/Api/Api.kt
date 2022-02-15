package com.klinovvlad.testtaskklinov.Api

import com.klinovvlad.testtaskklinov.Model.Data
import retrofit2.Call
import retrofit2.http.GET

const val API_KEY = "bIcqOZWUN2Ax1AzHNM4BNuJrJ0SOCiTA"
interface Api {

    @GET("v1/gifs/trending?api_key=${API_KEY}")
    fun getItem(): Call<Data>

}