package net.chenyoung.example.composechallengeforandroidstudyjam

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AppService {

    @GET("search.php")
    fun searchByName(@Query("s") name: String): Call<Response>

    @GET("filter.php")
    fun fetchByCategory(@Query("c") category: String): Call<Response>
}