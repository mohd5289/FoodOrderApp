package com.example.foodorder.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorder.R
import com.example.foodorder.activities.CategoryMealsActivity
import com.example.foodorder.activities.MainActivity
import com.example.foodorder.adapters.CategoriesAdapter
import com.example.foodorder.databinding.FragmentCategoriesBinding
import com.example.foodorder.viewmodel.HomeViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CategoriesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoriesFragment : Fragment() {
    // TODO: Rename and change types of parameters
   private lateinit var binding: FragmentCategoriesBinding
   private lateinit var categoriesAdapter: CategoriesAdapter
   private lateinit var viewModel: HomeViewModel
    private val TAG = "CategoriesFragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      viewModel= (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_categories,container,false)
        // Inflate the layout for this fragment
        binding.rvCategories.setUp()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//   viewModel.getCategories()

        viewModel.categoriesLiveData.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onViewCreated: ${it}")
       categoriesAdapter.differ.submitList(it)
   })

        categoriesAdapter.onItemClick={
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(HomeFragment.CATEGORY_NAME,it.strCategory)
            startActivity(intent)
        }
    }

private fun RecyclerView.setUp(){

    categoriesAdapter= CategoriesAdapter()
    adapter = categoriesAdapter
    layoutManager= GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
}
}