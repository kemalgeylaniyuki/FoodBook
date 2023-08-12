package com.kemalgeylani.foodbook.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kemalgeylani.foodbook.model.Food
import com.kemalgeylani.foodbook.service.FoodDatabase
import kotlinx.coroutines.launch

class FoodDetailViewModel(application: Application) : BaseViewModel(application) {

    val foodLiveData = MutableLiveData<Food>()

    fun getData(uuid : Int){

        launch {

            val dao = FoodDatabase(getApplication()).foodDao()
            val food = dao.getFood(uuid)
            foodLiveData.value = food

        }

    }

}