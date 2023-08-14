package com.kemalgeylani.foodbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.kemalgeylani.foodbook.R
import com.kemalgeylani.foodbook.databinding.RecyclerRowBinding
import com.kemalgeylani.foodbook.model.Food
import com.kemalgeylani.foodbook.view.FoodListFragmentDirections

class FoodAdapter(val foodList : ArrayList<Food>) : RecyclerView.Adapter<FoodAdapter.FoodHolder>(), FoodClickListener {

    class FoodHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodHolder {
        val recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FoodHolder(recyclerRowBinding)
    }

    override fun getItemCount(): Int {
        return foodList.count()
    }

    override fun onBindViewHolder(holder: FoodHolder, position: Int) {

        holder.binding.food = foodList[position]
        holder.binding.listener = this

        /*
        holder.binding.name.text = foodList.get(position).foodName
        holder.binding.calorie.text = foodList.get(position).foodCalorie

        holder.itemView.setOnClickListener {
            val action = FoodListFragmentDirections.actionFoodListFragmentToFoodDetailFragment(foodList.get(position).uuid)
            Navigation.findNavController(it).navigate(action)
        }

        holder.binding.imageViewrecyclerView.downloadImage(foodList.get(position).foodImage, makePlaceHolder(holder.itemView.context))

         */
    }

    fun updateFoodList(newFoodList : List<Food>){
        foodList.clear()
        foodList.addAll(newFoodList)
        notifyDataSetChanged()
    }

    override fun foodClicked(view: View) {

        val uuid = view.findViewById<TextView>(R.id.food_uuid).text.toString().toIntOrNull()
        uuid?.let {
            val action = FoodListFragmentDirections.actionFoodListFragmentToFoodDetailFragment(it)
            Navigation.findNavController(view).navigate(action)
        }
    }

}