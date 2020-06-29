package com.planetjuno.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {

    companion object {

        fun dateFormatForFetchingPlanetData(time: Date): String {
            val myFormat = "YYYY-MM-dd" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            return sdf.format(time)
        }

        fun dateFormatForDisplay(time: String): String {
            return if (time.isNotEmpty()) {
                val myFormat = "YYYY-MM-dd" // mention the format you need
                val myFormat1 = "dd MMM, yy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
                val sdf1 = SimpleDateFormat(myFormat1, Locale.getDefault())
                val date = sdf.parse(time) as Date
                sdf1.format(date)
            } else {
                ""
            }
        }
    }
}