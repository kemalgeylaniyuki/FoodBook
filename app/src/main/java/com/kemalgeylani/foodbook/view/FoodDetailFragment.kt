package com.kemalgeylani.foodbook.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kemalgeylani.foodbook.databinding.FragmentFoodDetailBinding
import com.kemalgeylani.foodbook.util.downloadImage
import com.kemalgeylani.foodbook.util.makePlaceHolder
import com.kemalgeylani.foodbook.viewmodel.FoodDetailViewModel

class FoodDetailFragment : Fragment() {

    private var _binding : FragmentFoodDetailBinding? = null
    private val binding get() = _binding!!

    private var foodId = 0
    private lateinit var viewModel : FoodDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_food_detail, container, false)
        _binding = FragmentFoodDetailBinding.inflate(layoutInflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            foodId = FoodDetailFragmentArgs.fromBundle(it).foodId
        }

        viewModel = ViewModelProvider(this).get(FoodDetailViewModel::class.java)
        viewModel.getData(foodId)


        observeliveData()

        }

    fun observeliveData(){

        viewModel.foodLiveData.observe(viewLifecycleOwner, Observer { food ->
            food?.let {

                binding.selectedFood = it

                /*
                binding.foodNameText.text = it.foodName
                binding.foodCalorieText.text = it.foodCalorie
                binding.carbohydrateText.text = it.foodCarbohydrate
                binding.proteinText.text = it.foodProtein
                binding.oilText.text = it.foodOil
                context?.let {
                    binding.foodImage.downloadImage(food.foodImage, makePlaceHolder(it))
                }

                 */

            }
        })

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}