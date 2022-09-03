package com.example.foodorder.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorder.activities.CategoryMealsActivity
import com.example.foodorder.activities.MainActivity
import com.example.foodorder.activities.MealActivity
import com.example.foodorder.adapters.CategoriesAdapter
import com.example.foodorder.adapters.MostPopularAdapter
import com.example.foodorder.database.MealDatabase
import com.example.foodorder.databinding.FragmentHomeBinding
import com.example.foodorder.fragments.bottomsheet.MealBottomSheetFragment
import com.example.foodorder.models.MealsByCategory
import com.example.foodorder.models.Meal
import com.example.foodorder.viewmodel.HomeViewModel


//    get() {}

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
    const val CATEGORY_NAME = "com.example.foodorder.fragments.categoryName"
    }
    lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var mostPopularAdapter: MostPopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var mealDatabase: MealDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding = FragmentHomeBinding.inflate(inflater,container,false)
//        binding.apply {      }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       viewModel.apply {
           getRandomMeal()
           getPopularItems()
           getCategories()
       }

//        viewModel

        binding.apply {

            rvMealsPopular.setUp()
            rvCategories.setUpGrid()
            viewModel.randomMealLiveData.observe(viewLifecycleOwner,Observer<Meal> {

                    Glide.with(this@HomeFragment)
                        .load(it.strMealThumb)
                        .into(imgRandomMeal)
                this@HomeFragment.randomMeal= it

            })

        }
viewModel.apply {
    popularItemsLiveData.observe(viewLifecycleOwner, Observer {
//      Log.d(TAG, "onViewCreated: ${it[0]}")
        mostPopularAdapter.differ.submitList(it as ArrayList<MealsByCategory>)

    })
  categoriesLiveData.observe(viewLifecycleOwner, Observer {

      categoriesAdapter.differ.submitList(it)
  })
categoriesAdapter.onItemClick={
    val intent = Intent(activity,CategoryMealsActivity::class.java)
    intent.putExtra(CATEGORY_NAME,it.strCategory)
    startActivity(intent)
}
  mostPopularAdapter.onItemLongClick={
      val mealBottomSheetFragment= MealBottomSheetFragment.newInstance(it.idMeal)
      mealBottomSheetFragment.show(childFragmentManager,"Meal Info")
  }
}


//        binding.apply {
//            Glide.with(this@HomeFragment)
//                .load(randomMeal.strMealThumb)
//                .into(imgRandomMeal)
//        }
        onRandomMealClick()
        onPopularItemClick()
    }

    private fun onPopularItemClick() {
        mostPopularAdapter.onItemClick = {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, it.idMeal)
            intent.putExtra(MEAL_NAME, it.strMeal)
            intent.putExtra(MEAL_THUMB, it.strMealThumb)
            startActivity(intent)
        }
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
    private fun RecyclerView.setUp(){
        mostPopularAdapter = MostPopularAdapter()
        adapter= mostPopularAdapter
        layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
    }
    private fun RecyclerView.setUpGrid(){
        categoriesAdapter = CategoriesAdapter()
        adapter =categoriesAdapter
        layoutManager = GridLayoutManager(context,3, GridLayoutManager.VERTICAL,false)
    }
}