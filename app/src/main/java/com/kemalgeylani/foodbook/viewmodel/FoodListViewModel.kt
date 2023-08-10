package com.kemalgeylani.foodbook.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kemalgeylani.foodbook.model.Food
import com.kemalgeylani.foodbook.service.FoodAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class FoodListViewModel : ViewModel() {

    val foods = MutableLiveData<List<Food>>()
    val foodErrorMessage = MutableLiveData<Boolean>()
    val foodLoadingBar = MutableLiveData<Boolean>()

    private val foodAPIService = FoodAPIService()
    private val compositeDisposable = CompositeDisposable()

    fun refreshData(){
        getDataFromInternet()
    }

    private fun getDataFromInternet(){

        foodLoadingBar.value = true

        compositeDisposable.add(foodAPIService.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object  : DisposableSingleObserver<List<Food>>(){
                override fun onSuccess(t: List<Food>) {
                    foods.value = t
                    foodLoadingBar.value = false
                    foodErrorMessage.value = false
                }

                override fun onError(e: Throwable) {
                    foodErrorMessage.value = true
                    foodLoadingBar.value = false
                    e.printStackTrace()
                }

            })
        )

    }

}