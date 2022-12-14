package com.example.foodorder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodorder.database.MealDatabase
import com.example.foodorder.models.*
import com.example.foodorder.network.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

class HomeViewModel(private val mealDatabase: MealDatabase?) :ViewModel() {
private val TAG = "HomeFragment"
    private  var _randomMealLiveData= MutableLiveData<Meal>()
     val randomMealLiveData:LiveData<Meal>
        get() =_randomMealLiveData
   
    private var _bottomSheetMealLiveData = MutableLiveData<Meal>()
    val bottomSheetMealLiveData:LiveData<Meal>
    get() = _bottomSheetMealLiveData
init {
    getRandomMeal()
}

private var _popularItemsLiveData = MutableLiveData<List<MealsByCategory>>()
    val popularItemsLiveData: LiveData<List<MealsByCategory>>
        get() = _popularItemsLiveData


    private var _categoriesLiveData = MutableLiveData<List<Category>>()
    val categoriesLiveData:LiveData<List<Category>>
     get() = _categoriesLiveData
    
    
    private var _searchMealLiveData = MutableLiveData<List<Meal>>()
    val searchMealLiveData:LiveData<List<Meal>>
    get() = _searchMealLiveData


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
    RetrofitInstance.api.getPopularItems("Seafood").enqueue(object :Callback<MealsByCategoryList>{
        override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
           if(response.body()!=null){
               _popularItemsLiveData.value = response.body()!!.meals
           }
        }

        override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
            Log.d(TAG, "onFailure: ${t.message.toString()}")

        }

    })
}

    fun getCategories(){
        RetrofitInstance.api.getCategories().enqueue(object :Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body()?.let {
      _categoriesLiveData.postValue(it.categories)
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}", )
            }

        })
    }
    fun getAllMeals()=
        mealDatabase?.mealDao()?.getAllMeals()

    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase?.mealDao()?.delete(meal)
        }
    }
    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase?.mealDao()?.upsert(meal)
        }
    }
    fun getMealById(id:String)
    =RetrofitInstance.api.getMealDetails(id).enqueue(object :Callback<MealList>{
        override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
            val meal= response.body()!!.meals.first()
            meal?.let { 
                _bottomSheetMealLiveData.postValue(it)
            }
        }

        override fun onFailure(call: Call<MealList>, t: Throwable) {
            Log.e(TAG, "onFailure: ${t.message.toString()}", )
        }

    })


fun searchMeals(searchQuery: String)
= RetrofitInstance.api.searchMeals(searchQuery).enqueue(object : Callback<MealList>{
    override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
        val mealList= response.body()?.meals
    mealList?.let {
        _searchMealLiveData.postValue(it)
    }
    }

    override fun onFailure(call: Call<MealList>, t: Throwable) {
        TODO("Not yet implemented")
    }

})

}