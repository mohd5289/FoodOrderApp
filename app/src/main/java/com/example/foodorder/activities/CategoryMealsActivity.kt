package com.example.foodorder.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorder.R
import com.example.foodorder.adapters.CategoryMealsAdapter
import com.example.foodorder.databinding.ActivityCategoryMealsBinding
import com.example.foodorder.databinding.CategoryItemBinding
import com.example.foodorder.fragments.HomeFragment
import com.example.foodorder.viewmodel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryMealsBinding
    lateinit var viewModel: CategoryMealsViewModel

    lateinit var categoryMealsAdapter: CategoryMealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProviders.of(this)[CategoryMealsViewModel::class.java]
    viewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)
   binding.apply { rvMeals.setUp() }

        viewModel.mealsLiveData.observe(this, Observer {
            binding.tvCategoryCount.text = it.size.toString()
            categoryMealsAdapter.differ.submitList(it)
        })
    }

private fun RecyclerView.setUp(){
    categoryMealsAdapter= CategoryMealsAdapter()
    adapter= categoryMealsAdapter
    layoutManager= GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
}

}