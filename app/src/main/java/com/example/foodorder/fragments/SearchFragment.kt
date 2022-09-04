package com.example.foodorder.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorder.R
import com.example.foodorder.activities.MainActivity
import com.example.foodorder.adapters.MealsAdapter
import com.example.foodorder.databinding.FragmentSearchBinding
import com.example.foodorder.viewmodel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters

private lateinit var binding: FragmentSearchBinding
private lateinit var viewModel: HomeViewModel
private lateinit var mealsAdapter: MealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
viewModel= (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            rvSearchedMeals.setUp()
            imgSearchArrow.setOnClickListener {
                val searchQuery = etSearchBox.text.toString()
                if(searchQuery.isNotEmpty()){
                    viewModel.searchMeals(searchQuery)
                }
            }
            var searchJob: Job?= null
            etSearchBox.addTextChangedListener {
                searchJob?.cancel()
                searchJob = lifecycleScope.launch {
                    delay(2000)
                    viewModel.searchMeals(it.toString())
                }
            }
        }

        viewModel.searchMealLiveData.observe(viewLifecycleOwner, Observer {
            mealsAdapter.differ.submitList(it)


        })

    }
    private fun RecyclerView.setUp(){
        mealsAdapter = MealsAdapter()
        adapter = mealsAdapter
        layoutManager= GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
    }
}