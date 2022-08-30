package com.example.foodorder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodorder.models.MealsByCategory
import com.example.foodorder.models.MealsByCategoryList
import com.example.foodorder.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel:ViewModel() {
private var _mealsLiveData= MutableLiveData<List<MealsByCategory>>()
    val mealsLiveData:LiveData<List<MealsByCategory>>
    get() = _mealsLiveData
fun getMealsByCategory(categoryName:String){
    RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object :Callback<MealsByCategoryList>{
        override fun onResponse(
            call: Call<MealsByCategoryList>,
            response: Response<MealsByCategoryList>
        ) {
            response.body()?.let {
                _mealsLiveData.postValue(it.meals)
            }
        }

        override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
            TODO("Not yet implemented")
        }
    })
}
}