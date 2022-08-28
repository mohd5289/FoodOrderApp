package com.example.foodorder.fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.foodorder.R
import com.example.foodorder.activities.MealActivity
import com.example.foodorder.databinding.FragmentHomeBinding
import com.example.foodorder.models.Meal
import com.example.foodorder.models.MealList
import com.example.foodorder.network.RetrofitInstance
import com.example.foodorder.viewmodel.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

     val  TAG = "HomeFragment"
//    bindi
    companion object{
        const val MEAL_ID = "com.example.foodorder.fragments.idMeal"
    const val MEAL_NAME = "com.example.foodorder.fragments.nameMeal"
    const val MEAL_THUMB = "com.example.foodorder.fragments.thumbMeal"
    }
    lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var randomMeal: Meal
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this@HomeFragment)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding = FragmentHomeBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       viewModel.getRandomMeal()
        binding.apply {
            viewModel.randomMealLiveData.observe(viewLifecycleOwner,Observer<Meal> {

                    Glide.with(this@HomeFragment)
                        .load(it.strMealThumb)
                        .into(imgRandomMeal)
                this@HomeFragment.randomMeal= it

            })
        }

//        binding.apply {
//            Glide.with(this@HomeFragment)
//                .load(randomMeal.strMealThumb)
//                .into(imgRandomMeal)
//        }
   onRandomMealClick()
    }

    private fun onRandomMealClick(){
        binding.apply {
            randomMealCard.setOnClickListener{
                val intent = Intent(activity,MealActivity::class.java)
                intent.putExtra(MEAL_ID,randomMeal.idMeal)
                intent.putExtra(MEAL_NAME, randomMeal.strMeal)
                intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
                startActivity(intent)
            }
        }
    }
}