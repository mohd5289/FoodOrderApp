package com.example.foodorder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.foodorder.models.CategoryList
import com.example.foodorder.models.CategoryMeals
import com.example.foodorder.models.Meal
import com.example.foodorder.models.MealList
import com.example.foodorder.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel:ViewModel() {
private val TAG = "HomeFragment"
    private  var _randomMealLiveData= MutableLiveData<Meal>()
     val randomMealLiveData:LiveData<Meal>
        get() =_randomMealLiveData
//init {
//    getRandomMeal()
//}
private var _popularItemsLiveData = MutableLiveData<List<CategoryMeals>>()
    val popularItemsLiveData: LiveData<List<CategoryMeals>>
        get() = _popularItemsLiveData


    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object: Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body()!=null){
                    val randomMeal: Meal = response.body()!!.meals[0]

    _randomMealLiveData.value= randomMeal

                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
               Log.d(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }
fun getPopularItems(){
    RetrofitInstance.api.getPopularItems("Seafood").enqueue(object :Callback<CategoryList>{
        override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
           if(response.body()!=null){
               _popularItemsLiveData.value = response.body()!!.meals
           }
        }

        override fun onFailure(call: Call<CategoryList>, t: Throwable) {
            Log.d(TAG, "onFailure: ${t.message.toString()}")

        }

    })
}
}