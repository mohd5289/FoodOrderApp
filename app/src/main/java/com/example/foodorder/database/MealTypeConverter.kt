package com.example.foodorder.database

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.jar.Attributes

@TypeConverters
class MealTypeConverter {
    @TypeConverter
    fun fromAnyToString(attribute: Any?):String{
        var attr= attribute?.let {
            ""
        }
    return  attr as String
    }
    @TypeConverter
    fun fromStringToAny(attribute: String?):Any?{
        var attr = attribute?.let {
            ""
        }
        return  attr as Any
    }
}