package com.kemalgeylani.foodbook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.kemalgeylani.foodbook.databinding.RecyclerRowBinding
import com.kemalgeylani.foodbook.model.Food
import com.kemalgeylani.foodbook.view.FoodListFragmentDirections

class FoodAdapter(val foodList : ArrayList<Food>) : RecyclerView.Adapter<FoodAdapter.FoodHolder>() {

    class FoodHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodHolder {
        val recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FoodHolder(recyclerRowBinding)
    }

    override fun getItemCount(): Int {
        return foodList.count()
    }

    override fun onBindViewHolder(holder: FoodHolder, position: Int) {
        holder.binding.name.text = foodList.get(position).foodName
        holder.binding.calorie.text = foodList.get(position).foodCalorie

        holder.itemView.setOnClickListener {
            val action = FoodListFragmentDirections.actionFoodListFragmentToFoodDetailFragment(0)
            Navigation.findNavController(it).navigate(action)
        }
    }

    fun updateFoodList(newFoodList : List<Food>){
        foodList.clear()
        foodList.addAll(newFoodList)
        notifyDataSetChanged()
    }

}