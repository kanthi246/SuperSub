package com.example.supersub.Network

import com.example.supersub.Models.Exercise
import com.example.supersub.Models.Explore
import com.example.supersub.Utils.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SuperSubApi {
    @GET(Constants.GETEXPLORE)
    fun getExplore():Call<Explore>

    @GET(Constants.GETEXERCISE)
    fun getExercise(@Path("value") exerciseid:String):Call<Exercise>
}