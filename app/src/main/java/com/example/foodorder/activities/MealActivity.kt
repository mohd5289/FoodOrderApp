package com.example.foodorder.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.foodorder.R
import com.example.foodorder.databinding.ActivityMealBinding
import com.example.foodorder.fragments.HomeFragment
import com.example.foodorder.models.Meal
import com.example.foodorder.viewmodel.MealViewModel

class MealActivity : AppCompatActivity() {
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var viewModel:MealViewModel
  private lateinit var binding:ActivityMealBinding
  private  lateinit var youtubeLink:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
       viewModel= ViewModelProviders.of(this)[MealViewModel::class.java]

       getMealInformationFromIntent()
       setInformationInViews()
        loadingCase()
        viewModel.getMealDetails(mealId)

        viewModel.mealDetailsLiveData.observe(this, Observer<Meal> {
            onResponseCase()
            binding.apply {

                tvCategory.text=  "Category: ${it.strCategory}"
                tvArea.text ="Area: ${it.strArea}"
                tvInstructionsSteps.text = it.strInstructions
                youtubeLink = it.strYoutube
            }
        })
        onYouTubeImageClicked()
    }

    private fun onYouTubeImageClicked() {
        binding.apply {
            imgYoutube.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
                startActivity(intent)
            }
        }
    }

    private fun getMealInformationFromIntent(){
    val intent = intent
    mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
    mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!


}
    private fun setInformationInViews(){
      binding.apply {
          Glide.with(applicationContext)
              .load(mealThumb)
              .into(imgMealDetail)

      collapsingToolbar.title = mealName
          collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
          collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
      }

    }
    private fun loadingCase(){
binding.apply {
   progressBar.visibility = View.VISIBLE
    btnAddToFav.visibility = View.INVISIBLE
    tvInstructions.visibility = View.INVISIBLE
    tvCategory.visibility = View.INVISIBLE
    tvArea.visibility = View.INVISIBLE
    imgYoutube.visibility = View.INVISIBLE
}
    }
    private fun onResponseCase(){
        binding.apply {
            progressBar.visibility = View.INVISIBLE
            btnAddToFav.visibility = View.VISIBLE
            tvInstructions.visibility = View.VISIBLE
            tvCategory.visibility = View.VISIBLE
            tvArea.visibility = View.VISIBLE
            imgYoutube.visibility = View.VISIBLE
        }
    }
}