package com.kemalgeylani.foodbook.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kemalgeylani.foodbook.model.Food

class FoodDetailViewModel : ViewModel() {

    val foodLiveData = MutableLiveData<Food>()

    fun getData(){

        val muz = Food("muz","5","5","5","5","www.test.com")
        foodLiveData.value = muz

    }

}