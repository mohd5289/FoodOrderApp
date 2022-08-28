package com.example.foodorder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.foodorder.models.Meal
import com.example.foodorder.models.MealList
import com.example.foodorder.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel:ViewModel() {

    private  var _randomMealLiveData= MutableLiveData<Meal>()
     val randomMealLiveData:LiveData<Meal>
        get() =_randomMealLiveData
//init {
//    getRandomMeal()
//}

    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object: Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body()!=null){
                    val randomMeal: Meal = response.body()!!.meals[0]

    _randomMealLiveData.value= randomMeal

                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
//                Log.d(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }
}