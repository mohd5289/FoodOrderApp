package com.example.foodorder.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorder.databinding.ActivityMealBinding
import com.example.foodorder.databinding.PopularItemsBinding
import com.example.foodorder.models.CategoryList
import com.example.foodorder.models.CategoryMeals

class MostPopularAdapter:RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {
 lateinit var onItemClick:((CategoryMeals)->Unit)

 inner   class PopularMealViewHolder(val binding: PopularItemsBinding):RecyclerView.ViewHolder(binding.root)
    private val differCallBack= object : DiffUtil.ItemCallback<CategoryMeals>(){
        override fun areItemsTheSame(oldItem: CategoryMeals, newItem: CategoryMeals): Boolean {
            return  oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: CategoryMeals, newItem: CategoryMeals): Boolean {
            return oldItem== newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        holder.apply {
          binding.apply {
              Glide.with(itemView)
                  .load(differ.currentList[position].strMealThumb)
                  .into(imgPopularMealItem)
itemView.setOnClickListener{
    onItemClick(differ.currentList[position])
}
         executePendingBindings() }
        }
    }

    override fun getItemCount(): Int {
     return differ.currentList.size
    }

}