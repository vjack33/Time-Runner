package com.example.myapplication

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_weekdetail.*
import java.util.*

class WeekDetailActivity :  AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weekdetail)

        var usersDBHelper = UsersDBHelper(this)

        val sharedPreferences = getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)            //Initialization of SharedPreferences for storing settings

        if (sharedPreferences.getBoolean("SUNDAY",false) != true){
            textViewSunday.setTextColor(getColor(R.color.red))
        }

        if (sharedPreferences.getBoolean("MONDAY",false) != true){
            textViewMonday.setTextColor(getColor(R.color.red))
        }

        if (sharedPreferences.getBoolean("TUESDAY",false) != true){
            textViewTuesday.setTextColor(getColor(R.color.red))
        }

        if (sharedPreferences.getBoolean("WEDNESDAY",false) != true){
            textViewWednesday.setTextColor(getColor(R.color.red))
        }

        if (sharedPreferences.getBoolean("THURSDAY",false) != true){
            textViewThursday.setTextColor(getColor(R.color.red))
        }

        if (sharedPreferences.getBoolean("FRIDAY",false) != true){
            textViewFriday.setTextColor(getColor(R.color.red))
        }

        if (sharedPreferences.getBoolean("SATURDAY",false) != true){
            textViewSaturday.setTextColor(getColor(R.color.red))
        }

        buttonDateSelectorWeekDetail.setOnClickListener {
            // Get Current Date
            // Get Current Date
            val c = Calendar.getInstance()
            var mYear = c[Calendar.YEAR]
            var mMonth = c[Calendar.MONTH]
            var mDay = c[Calendar.DAY_OF_MONTH]


            val datePickerDialog = DatePickerDialog(
                this,
                OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    buttonDateSelectorWeekDetail.setText(
                        dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year
                    )
                }, mYear, mMonth, mDay
            )
            datePickerDialog.show()


            //var result = usersDBHelper.readUser(UserModel(dataDate = dataDate,dataInTime = dataInTime,dataOutTime = dataOutTime,dataTimeSpent = dataTimeSpent,dataReg = dataReg))

        }

        buttonFetchDetailsWeek.setOnClickListener {
            var dataDate = buttonDateSelectorWeekDetail.text.toString()
            var users = usersDBHelper.readUser(dataDate)
            users.forEach {
                var tv_user = TextView(this)
                tv_user.textSize = 30F
                tv_user.text = it.dataDate.toString() + " - " + it.dataInTime.toString()
                Toast.makeText(this, "You clicked me." + tv_user.text, Toast.LENGTH_SHORT).show()
                //this.ll_entries.addView(tv_user)
            }
        }


    }


}