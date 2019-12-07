package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceFragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_config.*
import java.io.File


class ConfigActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        val sharedPreferences = getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)

        if(sharedPreferences.getString("NAME","") != null){

            editTextConfigName.hint = sharedPreferences.getString("NAME","")
            editTextConfigName.setText(sharedPreferences.getString("NAME",""))

            checkBoxSunday.isChecked = sharedPreferences.getBoolean("SUNDAY",false)
            checkBoxMonday.isChecked = sharedPreferences.getBoolean("MONDAY", false)
/*            checkBoxTuesday.isChecked = sharedPreferences.getBoolean("TUESDAY",false)
            checkBoxWednesday.isChecked = sharedPreferences.getBoolean("WEDNESDAY",false)
            checkBoxThursday.isChecked = sharedPreferences.getBoolean("THURSDAY",false)
            checkBoxFriday.isChecked = sharedPreferences.getBoolean("FRIDAY",false)
            checkBoxSunday.isChecked = sharedPreferences.getBoolean("SATURDAY", false)

            editTextMaxHours.setText(sharedPreferences.getString("MAXHOURS",""))
            editTextMaxMinutes.setText(sharedPreferences.getString("MAXMINUTES", ""))
            editTextMinHours.setText(sharedPreferences.getString("MINHOURS",""))
            editTextMinMinutes.setText(sharedPreferences.getString("MINMINUTES",""))
            editTextWeekHours.setText(sharedPreferences.getString("WEEKHOURS",""))
            editTextWeekMinutes.setText(sharedPreferences.getString("WEEKMINUTES",""))*/
        }

        buttonConfigSave.setOnClickListener {

            val settingName = editTextConfigName.text.toString()

            val settingSunday = checkBoxSunday.isChecked
            val settingMonday = checkBoxMonday.isChecked
/*            val settingTuesday = checkBoxTuesday.isChecked
            val settingWednesday = checkBoxWednesday.isChecked
            val settingThursday = checkBoxThursday.isChecked
            val settingFriday = checkBoxFriday.isChecked
            val settingSaturday = checkBoxSaturday.isChecked

            val settingMaxHours = editTextMaxHours.text.toString()
            val settingMaxMinutes = editTextMaxMinutes.text.toString()
            val settingMinHours = editTextMinHours.text.toString()
            val settingMinMinutes = editTextMinMinutes.text.toString()
            val settingWeekHours = editTextWeekHours.text.toString()
            val settingWeekMinutes = editTextWeekMinutes.text.toString()*/

            val editor = sharedPreferences.edit()
            editor.putString("NAME",settingName)
            editor.putBoolean("SUNDAY", settingSunday)
            editor.putBoolean("MONDAY", settingMonday)
/*            editor.putBoolean("TUESDAY", settingTuesday)
            editor.putBoolean("WEDNESDAY", settingWednesday)
            editor.putBoolean("THURSDAY", settingThursday)
            editor.putBoolean("FRIDAY", settingFriday)
            editor.putBoolean("SATURDAY", settingSaturday)

            editor.putString("MAXHOURS", settingMaxHours)
            editor.putString("MAXMINUTES", settingMaxMinutes)
            editor.putString("MINHOURS", settingMinHours)
            editor.putString("MINMINUTES", settingMinMinutes)
            editor.putString("WEEKHOURS", settingWeekHours)
            editor.putString("WEEKMINUTES", settingWeekMinutes)*/

            editor.apply()

        }

        buttonConfigCancel.setOnClickListener {
            val name = sharedPreferences.getString("NAME","")
            val sunday = sharedPreferences.getBoolean("SUNDAY", false)
            val monday = sharedPreferences.getBoolean("MONDAY", false)
            Toast.makeText(this@ConfigActivity, name.toString() + sunday.toString() + monday.toString() +"You clicked me.", Toast.LENGTH_SHORT).show()
        }
    }
}