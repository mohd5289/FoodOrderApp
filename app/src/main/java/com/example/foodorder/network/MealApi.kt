package com.example.foodorder.network

import com.example.foodorder.models.Meal
import com.example.foodorder.models.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface  MealApi {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php")
    fun getMealDetails(@Query("i")id:String):Call<MealList>

}