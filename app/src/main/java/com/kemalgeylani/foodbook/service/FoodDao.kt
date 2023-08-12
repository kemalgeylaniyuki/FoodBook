package com.kemalgeylani.foodbook.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kemalgeylani.foodbook.model.Food

@Dao
interface FoodDao {

    @Insert
    suspend fun insertAll(vararg food : Food) : List<Long>
    // vararg -> birden fazla, istenilen sayıda food
    // List<Long> -> long id'ler

    @Query("SELECT * FROM Food")
    suspend fun getAllFood() : List<Food>

    @Query("SELECT * FROM Food WHERE uuid = :foodId")
    suspend fun getFood(foodId : Int) : Food

    @Query("DELETE FROM Food")
    suspend fun deleteAllFood()

}