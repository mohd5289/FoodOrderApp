package com.example.foodorder.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorder.databinding.CategoryItemBinding
import com.example.foodorder.databinding.PopularItemsBinding
import com.example.foodorder.models.Category

class CategoryAdapter:RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

inner class CategoryViewHolder(val binding: CategoryItemBinding):RecyclerView.ViewHolder(binding.root)

    private val differCallback = object :DiffUtil.ItemCallback<Category>(){
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
           return oldItem.idCategory==newItem.idCategory
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem==newItem
        }

    }

   val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
//       holder.binding
        holder.apply {
           Glide.with(itemView)
               .load(differ.currentList[position].strCategoryThumb)
               .into(binding.imgCategory)
            binding.tvCategoryName.text= differ.currentList[position].strCategory
        }
    }

    override fun getItemCount(): Int {
        return  differ.currentList.size
    }


}