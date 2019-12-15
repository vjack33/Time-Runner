package com.example.swipper

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_config.*


class ConfigActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        //Initialization of SharedPreferences for storing settings
        val sharedPreferences = getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)

        // Saving Weekdays to SharedPreferences
        editTextConfigName.setText(sharedPreferences.getString("NAME","TEST"))
        checkBoxSunday.isChecked = sharedPreferences.getBoolean("SUNDAY",false)
        checkBoxMonday.isChecked = sharedPreferences.getBoolean("MONDAY", true)
        checkBoxTuesday.isChecked = sharedPreferences.getBoolean("TUESDAY",true)
        checkBoxWednesday.isChecked = sharedPreferences.getBoolean("WEDNESDAY",true)
        checkBoxThursday.isChecked = sharedPreferences.getBoolean("THURSDAY",true)
        checkBoxFriday.isChecked = sharedPreferences.getBoolean("FRIDAY",true)
        checkBoxSaturday.isChecked = sharedPreferences.getBoolean("SATURDAY", false)

        // Saving Config values to SharedPreferences
        editTextMaxHours.setText(sharedPreferences.getString("MAXHOURS","9"))
        editTextMaxMinutes.setText(sharedPreferences.getString("MAXMINUTES", "30"))
        editTextMinHours.setText(sharedPreferences.getString("MINHOURS","8"))
        editTextMinMinutes.setText(sharedPreferences.getString("MINMINUTES","00"))
        editTextWeekHours.setText(sharedPreferences.getString("WEEKHOURS","45"))
        editTextWeekMinutes.setText(sharedPreferences.getString("WEEKMINUTES","00"))

        // Action to do on pressing the "Save Button" on Configuration screen
        buttonConfigSave.setOnClickListener {

            // Getting user applied settings from the respective fields
            var settingName = "TEST" //editTextConfigName.text.toString()
            var settingSunday = false //checkBoxSunday.isChecked
            var settingMonday = true //checkBoxMonday.isChecked
            var settingTuesday = true//checkBoxTuesday.isChecked
            var settingWednesday = true//checkBoxWednesday.isChecked
            var settingThursday = true//checkBoxThursday.isChecked
            var settingFriday = true//checkBoxFriday.isChecked
            var settingSaturday = false//checkBoxSaturday.isChecked

            var settingMaxHours = "9"//editTextMaxHours.text.toString()
            var settingMaxMinutes = "30"//editTextMaxMinutes.text.toString()
            var settingMinHours = "8"//editTextMinHours.text.toString()
            var settingMinMinutes = "00"//editTextMinMinutes.text.toString()
            var settingWeekHours = "45"//editTextWeekHours.text.toString()
            var settingWeekMinutes = "00"//editTextWeekMinutes.text.toString()

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

    override fun onBackPressed() {                                                                        // super.onBackPressed(); commented this line in order to disable back press
        Toast.makeText(applicationContext, "Back press disabled!", Toast.LENGTH_SHORT).show()
    }
}