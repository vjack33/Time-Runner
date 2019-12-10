package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences

class   Prefs(context: Context) {
    //private val prefsFileName = context.packageName
    private val prefsFileName = "TEST"

    private val numberID = "number"
    private val pName = "NAME"
    private val pSunday = "SUNDAY"
    private val pMonday = "MONDAY"
    private val pTuesday = "TUESDAY"
    private val pWednesday = "WEDNESDAY"
    private val pThursday = "THURSDAY"
    private val pFriday = "FRIDAY"
    private val pSaturday = "SATURDAY"
    private val pMaxHours = "MAXHOURS"
    private val pMaxMinutes= "MAXMINUTES"
    private val pMinHours = "MINHOURS"
    private val pMinMinutes = "MINMINUTES"
    private val pWeekHours = "WEEKHOURS"
    private val pWeekMinutes = "WEEKMINUTES"

    private val prefs : SharedPreferences = context.getSharedPreferences(prefsFileName,0)

    var number: Int
        get() = prefs.getInt(numberID,0)
        set(value) = prefs.edit().putInt(numberID,value).apply()

    var prefsName : String?
        get() = prefs.getString("NAME","sss")
        set(value) = prefs.edit().putString(pName,value).apply()

    var prefsMonday : Boolean
        get() = prefs.getBoolean("MONDAY",false)
        set(value) = prefs.edit().putBoolean(pMonday,value).apply()

    var prefsTuesday : Boolean
        get() = prefs.getBoolean("TUESDAY",true)
        set(value) = prefs.edit().putBoolean(pTuesday,value).apply()

    var prefsWednesday : Boolean
        get() = prefs.getBoolean("WEDNESDAY",true)
        set(value) = prefs.edit().putBoolean(pWednesday,value).apply()

    var prefsThursday : Boolean
        get() = prefs.getBoolean("THURSDAY",true)
        set(value) = prefs.edit().putBoolean(pThursday,value).apply()

    var prefsFriday : Boolean
        get() = prefs.getBoolean("FRIDAY",true)
        set(value) = prefs.edit().putBoolean(pFriday,value).apply()

    var prefsSaturday : Boolean
        get() = prefs.getBoolean("SATURDAY",true)
        set(value) = prefs.edit().putBoolean(pSaturday,value).apply()

    var prefsSunday : Boolean
        get() = prefs.getBoolean("SUNDAY",true)
        set(value) = prefs.edit().putBoolean(pSunday,value).apply()

    var prefsMaxHours : String?
        get() = prefs.getString("MAXHOURS","")
        set(value) = prefs.edit().putString(pMaxHours,value).apply()

    var prefsMaxMinutes : String?
        get() = prefs.getString("MAXMINUTES","")
        set(value) = prefs.edit().putString(pMaxMinutes,value).apply()

    var prefsMinHours : String?
        get() = prefs.getString("MINHOURS","")
        set(value) = prefs.edit().putString(pMinHours,value).apply()

    var prefsMinMinutes : String?
        get() = prefs.getString("MINMINUTES","")
        set(value) = prefs.edit().putString(pMinMinutes,value).apply()

    var prefsWeekHours : String?
        get() = prefs.getString("WEEKHOURS","")
        set(value) = prefs.edit().putString(pWeekHours,value).apply()

    var prefsWeekMinutes : String?
        get() = prefs.getString("WEEKMINUTES","")
        set(value) = prefs.edit().putString(pWeekMinutes,value).apply()

}