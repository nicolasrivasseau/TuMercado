package com.example.tumercado.api

import com.example.tumercado.entity.SearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TuMercadoApi {
    @GET("sites/MLA/search")
    fun search(@Query("q")productDescription:String):Call<SearchResult>
}
