package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_config.*


class ConfigActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)


        /*var test = prefs
        prefs.number = 3
        Toast.makeText(this, test.toString(), Toast.LENGTH_SHORT).show()*/

        //val sharedPreferences = getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)            //Initialization of SharedPreferences for storing settings
        // If Settings is NULL : When settings opened for first time

        editTextConfigName.setText(prefs.prefsName)
        checkBoxSunday.isChecked = prefs.prefsSunday
        checkBoxMonday.isChecked = prefs.prefsMonday
        checkBoxTuesday.isChecked = prefs.prefsTuesday
        checkBoxWednesday.isChecked = prefs.prefsWednesday
        checkBoxThursday.isChecked = prefs.prefsThursday
        checkBoxFriday.isChecked = prefs.prefsFriday
        checkBoxSaturday.isChecked = prefs.prefsSaturday

        editTextMaxHours.setText(prefs.prefsMaxHours)
        editTextMaxMinutes.setText(prefs.prefsMaxMinutes)
        editTextMinHours.setText(prefs.prefsMinHours)
        editTextMinMinutes.setText(prefs.prefsMinMinutes)
        editTextWeekHours.setText(prefs.prefsWeekHours)
        editTextWeekMinutes.setText(prefs.prefsWeekMinutes)

            /*editTextConfigName.setText(sharedPreferences.getString("NAME",""))
            checkBoxSunday.isChecked = sharedPreferences.getBoolean("SUNDAY",false)
            checkBoxMonday.isChecked = sharedPreferences.getBoolean("MONDAY", false)
            checkBoxTuesday.isChecked = sharedPreferences.getBoolean("TUESDAY",false)
            checkBoxWednesday.isChecked = sharedPreferences.getBoolean("WEDNESDAY",false)
            checkBoxThursday.isChecked = sharedPreferences.getBoolean("THURSDAY",false)
            checkBoxFriday.isChecked = sharedPreferences.getBoolean("FRIDAY",false)
            checkBoxSaturday.isChecked = sharedPreferences.getBoolean("SATURDAY", false)

            editTextMaxHours.setText(sharedPreferences.getString("MAXHOURS",""))
            editTextMaxMinutes.setText(sharedPreferences.getString("MAXMINUTES", ""))
            editTextMinHours.setText(sharedPreferences.getString("MINHOURS",""))
            editTextMinMinutes.setText(sharedPreferences.getString("MINMINUTES",""))
            editTextWeekHours.setText(sharedPreferences.getString("WEEKHOURS",""))
            editTextWeekMinutes.setText(sharedPreferences.getString("WEEKMINUTES",""))*/


        // Action to do on pressing the Save Button on Configuration screen
        buttonConfigSave.setOnClickListener {

            val settingName = editTextConfigName.text.toString()
            val settingSunday = checkBoxSunday.isChecked
            val settingMonday = checkBoxMonday.isChecked
            val settingTuesday = checkBoxTuesday.isChecked
            val settingWednesday = checkBoxWednesday.isChecked
            val settingThursday = checkBoxThursday.isChecked
            val settingFriday = checkBoxFriday.isChecked
            val settingSaturday = checkBoxSaturday.isChecked
            val settingMaxHours = editTextMaxHours.text.toString()
            val settingMaxMinutes = editTextMaxMinutes.text.toString()
            val settingMinHours = editTextMinHours.text.toString()
            val settingMinMinutes = editTextMinMinutes.text.toString()
            val settingWeekHours = editTextWeekHours.text.toString()
            val settingWeekMinutes = editTextWeekMinutes.text.toString()

            prefs.prefsName = settingName
            prefs.prefsSunday = settingSunday
            prefs.prefsMonday = settingMonday
            prefs.prefsTuesday = settingTuesday
            prefs.prefsWednesday = settingWednesday
            prefs.prefsThursday = settingThursday
            prefs.prefsFriday = settingFriday
            prefs.prefsSaturday = settingSaturday

            prefs.prefsMaxHours = settingMaxHours
            prefs.prefsMaxMinutes = settingMaxMinutes
            prefs.prefsMinHours = settingMinHours
            prefs.prefsMinMinutes = settingMinMinutes
            prefs.prefsWeekHours = settingWeekHours
            prefs.prefsWeekMinutes = settingWeekMinutes

       /*     val editor = sharedPreferences.edit()
            editor.putString("NAME",settingName)
            editor.putBoolean("SUNDAY", settingSunday)
            editor.putBoolean("MONDAY", settingMonday)
            editor.putBoolean("TUESDAY", settingTuesday)
            editor.putBoolean("WEDNESDAY", settingWednesday)
            editor.putBoolean("THURSDAY", settingThursday)
            editor.putBoolean("FRIDAY", settingFriday)
            editor.putBoolean("SATURDAY", settingSaturday)
            editor.putString("MAXHOURS", settingMaxHours)
            editor.putString("MAXMINUTES", settingMaxMinutes)
            editor.putString("MINHOURS", settingMinHours)
            editor.putString("MINMINUTES", settingMinMinutes)
            editor.putString("WEEKHOURS", settingWeekHours)
            editor.putString("WEEKMINUTES", settingWeekMinutes)
            editor.apply()*/

            //Start Main activity after saving the Configs
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Cancel Button onclick Function
        buttonConfigCancel.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}