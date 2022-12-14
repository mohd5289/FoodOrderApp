package com.example.foodorder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodorder.database.MealDatabase
import com.example.foodorder.models.CategoryList
import com.example.foodorder.models.Meal
import com.example.foodorder.models.MealList
import com.example.foodorder.network.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(private val mealDatabase: MealDatabase?) :ViewModel() {

    private  var _mealDetailsLiveData =MutableLiveData<Meal>()
    val mealDetailsLiveData:LiveData<Meal>
     get() = _mealDetailsLiveData

//    private var categoryList

    private val TAG = "MealActivity"
    fun getMealDetails(id:String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object :Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body()!=null){
                    _mealDetailsLiveData.value= response.body()!!.meals[0]
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

fun insertMeal(meal: Meal){
    viewModelScope.launch {
        mealDatabase?.mealDao()?.upsert(meal)
    }
}





}