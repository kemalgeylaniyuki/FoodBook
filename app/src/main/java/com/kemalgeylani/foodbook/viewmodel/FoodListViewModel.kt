package com.kemalgeylani.foodbook.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kemalgeylani.foodbook.model.Food
import com.kemalgeylani.foodbook.service.FoodAPIService
import com.kemalgeylani.foodbook.service.FoodDatabase
import com.kemalgeylani.foodbook.util.PrivateSP
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FoodListViewModel(application: Application) : BaseViewModel(application) {

    val foods = MutableLiveData<List<Food>>()
    val foodErrorMessage = MutableLiveData<Boolean>()
    val foodLoadingBar = MutableLiveData<Boolean>()
    private var updateTime = 10 * 60 * 1000 * 1000 * 1000L

    private val foodAPIService = FoodAPIService()
    private val compositeDisposable = CompositeDisposable()
    private val privateSP = PrivateSP(getApplication())

    fun refreshData(){

        val savedTime = privateSP.takeTime()
        if (savedTime != null && savedTime != 0L && System.nanoTime() - savedTime < updateTime){
            // from SQLite
            getDataFromSQLite()
        }
        else{
            getDataFromInternet()
        }

    }

    fun refreshFromIntrenet(){
        getDataFromInternet()
    }

    private fun getDataFromSQLite(){

        launch {

            foodLoadingBar.value = true

            val foodList = FoodDatabase(getApplication()).foodDao().getAllFood()
            showFoods(foodList)
            Toast.makeText(getApplication(), "get food from room", Toast.LENGTH_LONG).show()

        }

    }

    private fun getDataFromInternet(){

        foodLoadingBar.value = true

        compositeDisposable.add(foodAPIService.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<Food>>(){
                override fun onSuccess(t: List<Food>) {
                    addSQLite(t)
                    Toast.makeText(getApplication(), "get food from internet", Toast.LENGTH_LONG).show()
                }

                override fun onError(e: Throwable) {
                    foodErrorMessage.value = true
                    foodLoadingBar.value = false
                    e.printStackTrace()
                }

            })
        )

    }

    private fun showFoods(foodsList: List<Food>){
        foods.value = foodsList
        foodLoadingBar.value = false
        foodErrorMessage.value = false
    }

    private fun addSQLite(foodsList: List<Food>){

        launch {

            val dao = FoodDatabase(getApplication()).foodDao()
            dao.deleteAllFood()

            val uuidList = dao.insertAll(*foodsList.toTypedArray())
            var i = 0

            while (i < foodsList.size){
                foodsList[i].uuid = uuidList[i].toInt()
                i++
            }

            showFoods(foodsList)
        }

        privateSP.saveTime(System.nanoTime())

    }

}