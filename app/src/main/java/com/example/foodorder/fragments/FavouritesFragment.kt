package com.example.foodorder.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorder.R
import com.example.foodorder.activities.MainActivity
import com.example.foodorder.adapters.MealsAdapter
import com.example.foodorder.databinding.FragmentFavouritesBinding
import com.example.foodorder.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavouritesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavouritesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
 private lateinit var binding: FragmentFavouritesBinding
 private lateinit var viewModel: HomeViewModel
 private lateinit var mealsAdapter: MealsAdapter

    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_favourites,container,false)
  binding.apply { rvFavorites.setUp()
     val itemTouchHelper = object:ItemTouchHelper.SimpleCallback(
         ItemTouchHelper.UP or ItemTouchHelper.DOWN,
         ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
     ){
         override fun onMove(
             recyclerView: RecyclerView,
             viewHolder: RecyclerView.ViewHolder,
             target: RecyclerView.ViewHolder
         ): Boolean {
             return true
         }

         override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
             val position = viewHolder.adapterPosition
             val meal = mealsAdapter.differ.currentList[position]
             viewModel.deleteMeal(meal)
             Snackbar.make(requireView(),"Meal deleted ${meal.strMeal}",Snackbar.LENGTH_LONG)
                 .setAction("Undo",
                 View.OnClickListener {
                     viewModel.insertMeal(meal)
                 }).show()
         }

     }
  ItemTouchHelper(itemTouchHelper).attachToRecyclerView(rvFavorites)
  }
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

   binding.apply {
       viewModel.apply {
           getAllMeals()?.observe(viewLifecycleOwner, Observer {
     mealsAdapter.differ.submitList(it)
           })
       }
   }
    }
private fun RecyclerView.setUp(){
    mealsAdapter = MealsAdapter()
    adapter = mealsAdapter
    layoutManager= GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
}

}