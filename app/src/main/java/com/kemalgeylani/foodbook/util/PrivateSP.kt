package com.kemalgeylani.foodbook.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit

class PrivateSP {

    companion object {

        private val TIME = "time"
        private var sharedPreferences : SharedPreferences? = null

        @Volatile private var instance : PrivateSP? = null
        private val lock = Any()
        operator fun invoke(context : Context) : PrivateSP = instance ?: synchronized(lock){
            instance ?: makePrivateSP(context).also {
                instance = it
            }
        }

        private fun makePrivateSP(context: Context) : PrivateSP{
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return PrivateSP()
        }

    }

    fun saveTime(time : Long){
        sharedPreferences?.edit(commit = true){
            putLong(TIME,time)
        }
    }

    fun takeTime() = sharedPreferences?.getLong(TIME,0)

}