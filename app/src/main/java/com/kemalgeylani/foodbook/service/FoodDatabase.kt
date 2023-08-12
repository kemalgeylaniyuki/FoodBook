package com.kemalgeylani.foodbook.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kemalgeylani.foodbook.model.Food

@Database(entities = arrayOf(Food::class), version = 1)
abstract class FoodDatabase : RoomDatabase() {

    abstract fun foodDao() : FoodDao

    //Farklı thread'lerde, aynı anda, tek objeye, erişilmesi isteniyorsa singleton kullanılır.
    //Singleton
    companion object { //companion object, singleton desing pattern uygulamak için kullanılan yapıdır.

        @Volatile private var instance : FoodDatabase? = null
        //volatile : farklı thread'lerden, instance'ın güncel değerini senkronize etmek için kullanılır.

        private val lock = Any() //thread'ler arasında güvenli instance oluşturmak için kullanılır.

        //invoke : singleton sınıfı çağrıldığında, çalışacak olan özel bir fonksiyondur.
        operator fun invoke(context : Context) = instance ?: synchronized(lock){
            //instance null ise yeni database instance'ı oluşturulur.
            instance ?: database(context).also {
                instance = it
            }
        }

        private fun database(context : Context) = Room.databaseBuilder(
            context.applicationContext,
            FoodDatabase::class.java,
            "foodDatabase"
        ).build()
    }

}