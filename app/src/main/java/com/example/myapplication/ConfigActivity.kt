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

        //Initialization of SharedPreferences for storing settings
        val sharedPreferences = getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
            // Saving Weekdays to SharedPreferences
            editTextConfigName.setText(sharedPreferences.getString("NAME",""))
            checkBoxSunday.isChecked = sharedPreferences.getBoolean("SUNDAY",false)
            checkBoxMonday.isChecked = sharedPreferences.getBoolean("MONDAY", false)
            checkBoxTuesday.isChecked = sharedPreferences.getBoolean("TUESDAY",false)
            checkBoxWednesday.isChecked = sharedPreferences.getBoolean("WEDNESDAY",false)
            checkBoxThursday.isChecked = sharedPreferences.getBoolean("THURSDAY",false)
            checkBoxFriday.isChecked = sharedPreferences.getBoolean("FRIDAY",false)
            checkBoxSaturday.isChecked = sharedPreferences.getBoolean("SATURDAY", false)

            // Saving Config values to SharedPreferences
            editTextMaxHours.setText(sharedPreferences.getString("MAXHOURS",""))
            editTextMaxMinutes.setText(sharedPreferences.getString("MAXMINUTES", ""))
            editTextMinHours.setText(sharedPreferences.getString("MINHOURS",""))
            editTextMinMinutes.setText(sharedPreferences.getString("MINMINUTES",""))
            editTextWeekHours.setText(sharedPreferences.getString("WEEKHOURS",""))
            editTextWeekMinutes.setText(sharedPreferences.getString("WEEKMINUTES",""))

        // Action to do on pressing the "Save Button" on Configuration screen
        buttonConfigSave.setOnClickListener {

            // Getting user applied settings from the respective fields
            var settingName = editTextConfigName.text.toString()
            var settingSunday = checkBoxSunday.isChecked
            var settingMonday = checkBoxMonday.isChecked
            var settingTuesday = checkBoxTuesday.isChecked
            var settingWednesday = checkBoxWednesday.isChecked
            var settingThursday = checkBoxThursday.isChecked
            var settingFriday = checkBoxFriday.isChecked
            var settingSaturday = checkBoxSaturday.isChecked

            var settingMaxHours = editTextMaxHours.text.toString()
            var settingMaxMinutes = editTextMaxMinutes.text.toString()
            var settingMinHours = editTextMinHours.text.toString()
            var settingMinMinutes = editTextMinMinutes.text.toString()
            var settingWeekHours = editTextWeekHours.text.toString()
            var settingWeekMinutes = editTextWeekMinutes.text.toString()

            var settingMaxAllMinutes : Int = editTextMaxHours.text.toString().toInt() * 60 + editTextMaxMinutes.text.toString().toInt()
            var settingMinAllMinutes : Int = editTextMinHours.text.toString().toInt() * 60 + editTextMinMinutes.text.toString().toInt()
            var settingWeekAllMinutes : Int = editTextWeekHours.text.toString().toInt() * 60 + editTextWeekMinutes.text.toString().toInt()

            // Pushing all collected config to SharedPreference
            val editor = sharedPreferences.edit()
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
            editor.putString("MAXALLMINUTES", settingMaxAllMinutes.toString())
            editor.putString("MINHOURS", settingMinHours)
            editor.putString("MINMINUTES", settingMinMinutes)
            editor.putString("MINALLMINUTES", settingMinAllMinutes.toString())
            editor.putString("WEEKHOURS", settingWeekHours)
            editor.putString("WEEKMINUTES", settingWeekMinutes)
            editor.putString("WEEKALLMINUTES", settingWeekAllMinutes.toString())
            editor.apply()

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