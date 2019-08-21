package com.example.hotelexplorer

import com.example.hotelexplorer.model.Hotel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IApi {

    @GET("0777.json")
    fun getHotels(): Call<MutableList<Hotel>>


    @GET("{id}.json")
    fun getSingleHotel(@Path("id") id: Long): Call<Hotel>
}