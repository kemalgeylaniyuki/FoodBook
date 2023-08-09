package com.kemalgeylani.foodbook.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kemalgeylani.foodbook.model.Food

class FoodListViewModel : ViewModel() {

    val foods = MutableLiveData<List<Food>>()
    val foodErrorMessage = MutableLiveData<Boolean>()
    val foodLoadingBar = MutableLiveData<Boolean>()

    fun refreshData(){

        val muz = Food("muz","5","5","5","5","www.test.com")
        val cilek = Food("cilek","3","3","3","3","www.test.com")
        val elma = Food("elma","1","1","1","1","www.test.com")

        val foodList = arrayListOf<Food>(muz,cilek,elma)

        foods.value = foodList
        foodErrorMessage.value = false
        foodLoadingBar.value = false

    }

}