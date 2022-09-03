package com.example.foodorder.fragments.bottomsheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.foodorder.R
import com.example.foodorder.activities.CategoryMealsActivity
import com.example.foodorder.activities.MainActivity
import com.example.foodorder.activities.MealActivity
import com.example.foodorder.databinding.FragmentMealBottomSheetBinding
import com.example.foodorder.fragments.HomeFragment
import com.example.foodorder.viewmodel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val MEAL_ID = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MealBottomSheetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MealBottomSheetFragment : BottomSheetDialogFragment() {
    // TODO: Rename and change types of parameters
    private var mealId: String? = null
    private lateinit var viewModel: HomeViewModel
//    private lateinit var mealId:String
    private  var mealName:String?=null
    private var mealThumb:String?=null

private lateinit var binding: FragmentMealBottomSheetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)

        }
    viewModel=(activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_meal_bottom_sheet,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mealId?.let {
            viewModel.getMealById(mealId as String)

        }
        viewModel.bottomSheetMealLiveData.observe(viewLifecycleOwner, Observer {
            binding.apply {
                Glide.with(root).load(it.strMealThumb).into(imgBottomSheet)
                tvBottomSheetArea.text = it.strArea
                tvBottomSheetCategory.text= it.strCategory
                tvBottomSheetMealName.text= it.strMeal
            }
        mealName = it.strMeal
            mealThumb= it.strMealThumb
        })
    binding.bottomSheet.setOnClickListener{
       if(mealName!=null && mealThumb!=null) {
           val intent = Intent(activity, MealActivity::class.java)
           intent.apply {
               putExtra(HomeFragment.MEAL_ID, mealId)
               putExtra(HomeFragment.MEAL_NAME, mealName)
               putExtra(HomeFragment.MEAL_THUMB, mealThumb)
           }
//           intent.putExtra(Ho, mealId)
           startActivity(intent)
       }
       }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MealBottomSheetFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            MealBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)

                }
            }
    }
}