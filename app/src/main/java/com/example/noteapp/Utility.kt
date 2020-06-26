package com.example.noteapp

import android.util.Log
import java.lang.Exception
import java.lang.NullPointerException
import java.text.SimpleDateFormat
import java.util.*

class Utility {

    //companion object = +- static
    companion object {
        private val TAG = "Utility"
        fun currentTimeStamps(): String? {
            try {

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val dateTime = sdf.format(Date())

                return dateTime
            } catch (e: Exception) {
                Log.e(TAG, "Current Time is null")
                e.printStackTrace()

                return null
            }
        }

        fun getMonth(number: String): String? {
            try {

                when (number) {
                    "01" -> return "Jan"
                    "02" -> return "Feb"
                    "03" -> return "Mar"
                    "04" -> return "Apr"
                    "05" -> return "May"
                    "06" -> return "Jun"
                    "07" -> return "Jul"
                    "08" -> return "Aug"
                    "09" -> return "Sep"
                    "10" -> return "Oct"
                    "11" -> return "Nov"
                    "12" -> return "Dec"
                    else ->
                        return "invalid"
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return number
        }
    }
}