package com.kemalgeylani.foodbook.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.kemalgeylani.foodbook.adapter.FoodAdapter
import com.kemalgeylani.foodbook.databinding.FragmentFoodListBinding
import com.kemalgeylani.foodbook.viewmodel.FoodListViewModel


class FoodListFragment : Fragment() {

    private var _binding : FragmentFoodListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel : FoodListViewModel
    private val foodAdapter = FoodAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_food_list, container, false)
        _binding = FragmentFoodListBinding.inflate(layoutInflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(FoodListViewModel::class.java)
        viewModel.refreshData()

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = foodAdapter

        observeLivedata()

    }

    fun observeLivedata(){

        viewModel.foods.observe(viewLifecycleOwner, Observer { foods ->
            foods?.let {
                binding.recyclerView.visibility = View.VISIBLE
                foodAdapter.updateFoodList(foods)
            }

        })

        viewModel.foodErrorMessage.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (it){
                    binding.textView.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
                else{
                    binding.textView.visibility = View.GONE
                }
            }
        })

        viewModel.foodLoadingBar.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it){
                    binding.recyclerView.visibility = View.GONE
                    binding.textView.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
                else{
                    binding.progressBar.visibility = View.GONE
                }
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}