package com.example.simplecalculator

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NewtonAPI {
    @GET("/{operation}/{expression}")
    fun function(@Path("operation") operation : String,@Path("expression") expression : String)  : Call<NewtonData>

    @GET("/zeroes/{expression}")
    fun zeroes(@Path("expression") expression : String)  : Call<FindZeroesData>
}