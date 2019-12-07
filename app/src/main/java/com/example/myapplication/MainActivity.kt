package com.example.myapplication

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
/*
        Initialization of every TextView and Buttons
*/
        textViewToday.text = LocalDate.now().dayOfWeek.toString()                                       // Get today's day eg: SUNDAY

        val mPickInTimeBtn = findViewById<Button>(R.id.pickInTimeBtn)                                   // Select Time Button Init
        val textViewInTime = findViewById<TextView>(R.id.textViewInTime)                                // Main Time Entry Text Init
        val submitBtn = findViewById<Button>(R.id.buttonSubmit)                                         // Submit Button Init

        val timeCompletedTextView = findViewById<TextView>(R.id.textViewTimeCompleted)                  // L1 : Completed Time hr:min view
        val timeCompletedHomeTextView = findViewById<TextView>(R.id.textViewHomeTimeCompleted)          // L1 : Completed final time HH view
        val timeCompletedHomeMinTextView = findViewById<TextView>(R.id.textViewHomeMinTimeCompleted)    // L1 : Completed final time MM view

        val timeRemf9TextView = findViewById<TextView>(R.id.textViewTimeRemf9)                          // L2 : Remaining Time for 9hr hr:min view
        val timeRemf9HomeTextView = findViewById<TextView>(R.id.textViewHomeTimeRemf9)                  // L2 : Remaining final time for 9hr HH view
        val timeRemf9HomeMinTextView = findViewById<TextView>(R.id.textViewHomeMinTimeRemf9)            // L2 : Remaining final time for 9hr MM view

        val timeRemf8TextView = findViewById<TextView>(R.id.textViewTimeRemf8)                          // L3 : Remaining Time for 8hr hr:min view
        val timeRemf8HomeTextView = findViewById<TextView>(R.id.textViewHomeTimeRemf8)                  // L3 : Remaining final time for 8hr HH view
        val timeRemf8HomeMinTextView = findViewById<TextView>(R.id.textViewHomeMinTimeRemf8)            // L3 : Remaining final time for 8hr MM view

        var hourSet : Int? = null
        var minuteSet : Int? = null
        var hourLocal : Int? = null
        var minuteLocal : Int? = null
/*
         Time Picker logic
*/

        mPickInTimeBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                textViewInTime.text = SimpleDateFormat("HH:mm").format(cal.time)                // HH:mm format Set Time
                hourSet = timePicker.hour
                minuteSet = timePicker.minute
            }

            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                false
            ).show()
        }

        submitBtn.setOnClickListener {

            if(textViewInTime.text == "Time"){
                Toast.makeText(this@MainActivity, "Please select a time.", Toast.LENGTH_SHORT).show()
            }
            else {
/*
            Initial textView Colors
*/

                timeCompletedTextView.setTextColor(getColor(R.color.black))
                timeRemf9TextView.setTextColor(getColor(R.color.black))
                timeRemf8TextView.setTextColor(getColor(R.color.black))

                hourLocal = LocalTime.now().hour
                minuteLocal = LocalTime.now().minute
/*
            Initializing the four different time values for many states
*/

                var d1: LocalTime =
                    LocalTime.parse(textViewInTime.text.toString())                                        // Parse Time selector text as Time
                var d2: LocalTime =
                    LocalTime.now()                                                                        // Get local time from device
                var d3: LocalTime =
                    d1.plusHours(9)                                                             // Set Time plus 9 hr
                var d4: LocalTime =
                    d1.plusHours(8)                                                             // Set Time plus 8 hr

/*
            Print all TextViews Here with logic
*/
                var diffCompleted: Int = ChronoUnit.MINUTES.between(d1, d2).toInt()
                var completedHours = diffCompleted / 60 //since both are ints, you get an int
                var completedMinutes = diffCompleted % 60

                timeCompletedTextView.text =
                    completedHours.toString() + "hr " + completedMinutes.toString() + "min"        // Print Completed time: (d2 - d1)
                timeCompletedHomeTextView.text =
                    d2.hour.toString()                                                        // Print System current time: LocalTime HH
                timeCompletedHomeMinTextView.text =
                    d2.minute.toString()                                                   // Print System Completed time current time: LocalTime MM

                var diffRem9: Int = ChronoUnit.MINUTES.between(d2, d3).toInt()
                var rem9Hours = diffRem9 / 60
                var rem9Minuutes = diffRem9 % 60

                timeRemf9TextView.text =
                    rem9Hours.toString() + "hr " + rem9Minuutes.toString() + "min"                     // Print Remaining time for 9hr: (d3 - d2)
                timeRemf9HomeTextView.text =
                    d3.hour.toString()                                                            // Print Final time for 9hr: d1 + 9hr HH
                timeRemf9HomeMinTextView.text =
                    d3.minute.toString()                                                       // Print Final time for 9hr: d1 + 9hr MM

                var diffRem8: Int = ChronoUnit.MINUTES.between(d2, d4).toInt()
                var rem8Hours = diffRem8 / 60
                var rem8Minutes = diffRem8 % 60

                timeRemf8TextView.text =
                    rem8Hours.toString() + "hr " + rem8Minutes.toString() + "min"                      // Print Remaining time for 8hr: (d4 - d2)
                timeRemf8HomeTextView.text =
                    d4.hour.toString()                                                            // Print Final time for 8hr: d1 + 8hr HH
                timeRemf8HomeMinTextView.text =
                    d4.minute.toString()                                                       // Print Final time for 8hr: d1 + 8hr MM
/*
            Change color of TextView when completed the time
*/
                if (diffCompleted > 540) {
                    timeCompletedTextView.setTextColor(this.getResources().getColor(R.color.green))
                }

                if (diffRem9 < 0) {
                    timeRemf9TextView.setTextColor(getColor(R.color.green))
                }

                if (diffRem8 < 0) {
                    timeRemf8TextView.setTextColor(getColor(R.color.green))
                }

            }
        }

        buttonSetting.setOnClickListener {
            val intent = Intent(this, ConfigActivity::class.java)
            startActivity(intent)
        }
    }
}