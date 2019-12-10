package com.example.myapplication

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_config.*
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


        // Total Time This week Logic
        val c = Calendar.getInstance()
        var mWeek = c[Calendar.WEEK_OF_YEAR]
        textViewRecapTotalTime.text = sharedPreferences.getString("WEEKHOURS","") + "hr " + sharedPreferences.getString("WEEKMINUTES","") + "min"

        // Completed This week
        var totalWeekTime: Int = usersDBHelper.readTimeCompleted(mWeek.toString()).toString().toInt()
        var weekHours = totalWeekTime / 60
        var weekMinutes = totalWeekTime % 60
        textViewRecapCompletedTime.text = weekHours.toString() +"hr "+ weekMinutes.toString() + "min"

        // Remaining Time Logic
        var timeCompleted : Int = usersDBHelper.readTimeCompleted(mWeek.toString())
        var totalRemainingTime: Int = ((sharedPreferences.getString("WEEKALLMINUTES","").toString().toInt() ) - timeCompleted).toString().toInt() // x + 360 = 210
        var remainingHours = totalRemainingTime / 60
        var remainingMinutes = totalRemainingTime % 60
        textViewRecapRemainingTime.text = remainingHours.toString() +"hr "+ remainingMinutes.toString() + "min"


        buttonDateSelectorWeekDetail.setOnClickListener {
            // Get Current Date
            val c = Calendar.getInstance()
            var mYear = c[Calendar.YEAR]
            var mMonth = c[Calendar.MONTH]
            var mDay = c[Calendar.DAY_OF_MONTH]
            var mWeek = c[Calendar.WEEK_OF_YEAR]
            Toast.makeText(this, "Yssss" + mWeek, Toast.LENGTH_SHORT).show()

            val datePickerDialog = DatePickerDialog(
                this,
                OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    buttonDateSelectorWeekDetail.setText(
                        dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year
                    )
                }, mYear, mMonth, mDay
            )
            datePickerDialog.show()

        }

        buttonFetchDetailsWeek.setOnClickListener {
            var dataDate = buttonDateSelectorWeekDetail.text.toString()
            var users = usersDBHelper.readUser(dataDate)
            users.forEach {

                textViewInTimeFetched.text = it.dataInTime
                textViewOutTimeFetched.text = it.dataOutTime
                textViewTimeSpentFetched.text = it.dataTimeSpent
                textViewRegFetched.text = it.dataReg
                textViewLeaveFetched.text = it.dataLeave

            }
        }

    }

}