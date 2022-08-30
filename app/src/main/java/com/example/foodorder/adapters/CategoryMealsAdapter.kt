package com.example.foodorder.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorder.databinding.CategoryItemBinding
import com.example.foodorder.databinding.MealItemBinding
import com.example.foodorder.models.MealsByCategory

class CategoryMealsAdapter:RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewHolder>() {


    inner class CategoryMealsViewHolder(val binding: MealItemBinding):RecyclerView.ViewHolder(binding.root)

   val differCallBack= object :DiffUtil.ItemCallback<MealsByCategory>(){
       override fun areItemsTheSame(oldItem: MealsByCategory, newItem: MealsByCategory): Boolean {
          return oldItem.idMeal == newItem.idMeal
       }

       override fun areContentsTheSame(
           oldItem: MealsByCategory,
           newItem: MealsByCategory
       ): Boolean {
           return oldItem == newItem
       }

   }

   val differ = AsyncListDiffer(this,differCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
      return  CategoryMealsViewHolder(MealItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {
        holder.binding.apply {
            Glide.with(root)
                .load(differ.currentList[position].strMealThumb)
                .into(imgMeal)

            tvMealName.text = differ.currentList[position].strMeal

        }
    }

    override fun getItemCount(): Int {
       return  differ.currentList.size
    }
}