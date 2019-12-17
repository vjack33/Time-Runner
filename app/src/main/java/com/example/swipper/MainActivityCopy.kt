/*
package com.example.swipper

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.os.SystemClock.sleep
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.marginTop
import kotlinx.android.synthetic.main.activity_config.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.math.round
import kotlin.math.roundToInt


class   MainActivityCopy : AppCompatActivity() {

    var publicD1 : LocalTime? = null
    var publicD2 : LocalTime? = null
    var publicD3 : LocalTime? = null
    var usersDBHelper = UsersDBHelper(this)                                                  // Init DB Helper class
    private val c : Calendar = Calendar.getInstance()                                               // Init Calender Object c


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //For making all unnecessary INVISIBLE
        buttonSaveTime.visibility = View.INVISIBLE
        textView1.visibility = View.INVISIBLE
        textView2.visibility = View.INVISIBLE
        textView3.visibility = View.INVISIBLE
        textViewTimeCompleted.visibility = View.INVISIBLE
        textViewTimeRemf9.visibility = View.INVISIBLE
        textViewTimeRemf8.visibility = View.INVISIBLE
        textViewHomeTimeCompleted.visibility = View.INVISIBLE
        textViewHomeMinTimeCompleted.visibility = View.INVISIBLE
        textViewHomeTimeRemf9.visibility = View.INVISIBLE
        textViewHomeMinTimeRemf9.visibility = View.INVISIBLE
        textViewHomeTimeRemf8.visibility = View.INVISIBLE
        textViewHomeMinTimeRemf8.visibility = View.INVISIBLE
        imageViewPlay.visibility = GONE
        imageViewStop.visibility = GONE

        //Initialization of SharedPreferences for storing settings
        val sharedPreferences = getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)

        // Saving Weekdays to SharedPreferences
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
        var settingMaxAllMinutes : Int = (9*60)+30 //editTextMaxHours.text.toString().toInt() * 60 + editTextMaxMinutes.text.toString().toInt()
        var settingMinAllMinutes : Int = 8*60 //editTextMinHours.text.toString().toInt() * 60 + editTextMinMinutes.text.toString().toInt()
        var settingWeekAllMinutes : Int = 45*60 //editTextWeekHours.text.toString().toInt() * 60 + editTextWeekMinutes.text.toString().toInt()

        // Saving Calender Instance
        var mYear = c[Calendar.YEAR]
        var mMonth = c[Calendar.MONTH]
        var mDay = c[Calendar.DAY_OF_MONTH]
        var mDate = mDay.toString() + "-" + (mMonth + 1).toString() +"-"+ mYear.toString()

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

        textViewToday.text = LocalDate.now().dayOfWeek.toString()                                           // Get today's day eg: SUNDAY
        textViewTodayDate.text = mDate                                                                      // Get today's date eg: 10-12-2019

        var todayDay = LocalDate.now().dayOfWeek.toString()                                                 // Save Today as LocalDate
        val mPickInTimeBtn = findViewById<Button>(R.id.pickInTimeBtn)                                       // Select In Time Button Init
        val mPickOutTimeBtn = findViewById<Button>(R.id.pickOutTimeBtn)                                     // Select Out Time Button Init
        val textViewInTime = findViewById<TextView>(R.id.textViewInTime)                                    // Main Time Entry Text Init
        val viewButton = findViewById<Button>(R.id.buttonViewRemTime)                                       // Submit Button Init
        val timeCompletedTextView = findViewById<TextView>(R.id.textViewTimeCompleted)                      // L1 : Completed Time hr:min view
        val timeCompletedHomeTextView = findViewById<TextView>(R.id.textViewHomeTimeCompleted)              // L1 : Completed final time HH view
        val timeCompletedHomeMinTextView = findViewById<TextView>(R.id.textViewHomeMinTimeCompleted)        // L1 : Completed final time MM view
        val timeRemf9TextView = findViewById<TextView>(R.id.textViewTimeRemf9)                              // L2 : Remaining Time for 9hr hr:min view
        val timeRemf9HomeTextView = findViewById<TextView>(R.id.textViewHomeTimeRemf9)                      // L2 : Remaining final time for 9hr HH view
        val timeRemf9HomeMinTextView = findViewById<TextView>(R.id.textViewHomeMinTimeRemf9)                // L2 : Remaining final time for 9hr MM view
        val timeRemf8TextView = findViewById<TextView>(R.id.textViewTimeRemf8)                              // L3 : Remaining Time for 8hr hr:min view
        val timeRemf8HomeTextView = findViewById<TextView>(R.id.textViewHomeTimeRemf8)                      // L3 : Remaining final time for 8hr HH view
        val timeRemf8HomeMinTextView = findViewById<TextView>(R.id.textViewHomeMinTimeRemf8)                // L3 : Remaining final time for 8hr MM view

        var hourSet : Int? = null
        var minuteSet : Int? = null
        var hourSetOut : Int? = null
        var minuteSetOut : Int? = null
        var hourLocal : Int? = null
        var minuteLocal : Int? = null

        if (sharedPreferences.getString("TODAYDATE", "") == mDate.toString()) {

            if (sharedPreferences.getString("INTIME", "") != "") {
                textViewInTime.text = sharedPreferences.getString("INTIME", "")
                textViewOutTime.text =  LocalTime.parse(textViewInTime.text).plusHours(9).toString()
                if (textViewInTime.text == "In Time") {
                    buttonWeekDetail.visibility = INVISIBLE
                    progressBar.visibility = INVISIBLE
                    textViewProgressStart.visibility = INVISIBLE
                    textViewProgressEnd.visibility = INVISIBLE
                    textViewPercent.visibility = INVISIBLE
                    textViewTimeRemaining.visibility = INVISIBLE
                }

                if (LocalTime.now() < LocalTime.parse(textViewOutTime.text)) {
                    Toast.makeText(this@MainActivityCopy, "IFF", Toast.LENGTH_SHORT).show()
                    // ContDown Logic for Less than 9 hr
                    var timeRemaining = ChronoUnit.MILLIS.between(LocalTime.now(), LocalTime.parse(sharedPreferences.getString("INTIME", "")).plusHours(9)).toLong()
                    val timer = object : CountDownTimer(timeRemaining, 1000) {
                        override fun onTick(millisUntilFinished: Long) {

                            var minutesTotalRemaining = ChronoUnit.MINUTES.between(LocalTime.now(), LocalTime.parse(sharedPreferences.getString("INTIME", "")).plusHours(9)).toLong()
                            var hoursRemaining = minutesTotalRemaining / 60                              // Convert Difference to Hours
                            var minutesRemaining = minutesTotalRemaining % 60
                            textViewTimeRemaining.text = (hoursRemaining.toString() + "hr " + minutesRemaining.toString() + "min remaining")
                            val percentTime: Int = ((minutesTotalRemaining.toFloat() / 540) * 100).toInt()
                            progressBar.progress = 100 - percentTime

                        }

                        override fun onFinish() {
                            Toast.makeText(this@MainActivityCopy, timeRemaining.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                    timer.start()
                }

                else {
                    Toast.makeText(this@MainActivityCopy, "ELSE", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this,"LOCAL " + LocalTime.now(), Toast.LENGTH_SHORT).show()
                    // Countdown Logic for More than 9hr and less than 9.30 hr
                    var timeRemaining = ChronoUnit.MILLIS.between(LocalTime.now(), LocalTime.parse(sharedPreferences.getString("INTIME", "")).plusHours(9).plusMinutes(30)).toLong()
                    val timer = object : CountDownTimer(timeRemaining, 1000) {
                        override fun onTick(millisUntilFinished: Long) {

                            var minutesTotalRemaining = ChronoUnit.MINUTES.between(LocalTime.now(), LocalTime.parse(sharedPreferences.getString("INTIME", "")).plusHours(9)).toLong()
                            var minutesRemaining = minutesTotalRemaining % 60
                            textViewTimeRemaining.text = ("Working Extra for " + (-minutesRemaining).toString() + "min")
                            val percentTime: Int = ((minutesTotalRemaining.toFloat() / 30) * 100).toInt()
                            progressBar2.progress =  - percentTime
                            progressBar.progress = 100

                            if (-minutesRemaining > 30){
                                textViewTimeRemaining.text = ("You have completed 9.30 hr")
                            }

                        }

                        override fun onFinish() {
                            Toast.makeText(this@MainActivityCopy, timeRemaining.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                    timer.start()
                }

            }


            Toast.makeText(this@MainActivityCopy, "Out", Toast.LENGTH_SHORT).show()
        }

        if (textViewInTime.text == "In Time") {
            buttonWeekDetail.visibility = INVISIBLE
            progressBar.visibility = INVISIBLE
            textViewProgressStart.visibility = INVISIBLE
            textViewProgressEnd.visibility = INVISIBLE
            textViewPercent.visibility = INVISIBLE
            textViewTimeRemaining.visibility = INVISIBLE

            imageViewPlay.visibility = VISIBLE

        }

        // In Time Picker Logic
        mPickInTimeBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                textViewInTime.text = SimpleDateFormat("HH:mm").format(cal.time)                // HH:mm format In Time
                hourSet = timePicker.hour                                                              // Save hours to variable
                minuteSet = timePicker.minute                                                          // save Minutes to variable
            }

            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                false
            ).show()
        }

        // Out Time Picker Logic
        mPickOutTimeBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                textViewOutTime.text = SimpleDateFormat("HH:mm").format(cal.time)                // HH:mm format Out Time
                hourSetOut = timePicker.hour
                minuteSetOut = timePicker.minute
            }

            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                false
            ).show()
        }


        // OnClick "VIEW" Button on main activity
        */
/*viewButton.setOnClickListener {

            if (sharedPreferences.getBoolean(todayDay,false) == true) {                                             // Check if today is working day from SharedPreferences

                if (textViewInTime.text == "In Time") {                                                                     // Check if there is any time set in "IN TIME"
                    Toast.makeText(this@MainActivity, "Please select a In time.", Toast.LENGTH_SHORT).show()
                } else if (LocalTime.parse(textViewInTime.text) > LocalTime.parse("14:00")) {
                    Toast.makeText(this@MainActivity, "Please select In time less than 2 PM.", Toast.LENGTH_SHORT).show()
                } else {

                    buttonSaveTime.visibility = View.VISIBLE
                    textView1.visibility = View.VISIBLE
                    textView2.visibility = View.VISIBLE
                    textView3.visibility = View.VISIBLE
                    textViewTimeCompleted.visibility = View.VISIBLE
                    textViewTimeRemf9.visibility = View.VISIBLE
                    textViewTimeRemf8.visibility = View.VISIBLE
                    textViewHomeTimeCompleted.visibility = View.VISIBLE
                    textViewHomeMinTimeCompleted.visibility = View.VISIBLE
                    textViewHomeTimeRemf9.visibility = View.VISIBLE
                    textViewHomeMinTimeRemf9.visibility = View.VISIBLE
                    textViewHomeTimeRemf8.visibility = View.VISIBLE
                    textViewHomeMinTimeRemf8.visibility = View.VISIBLE
                    // Initial textView Colors
                    timeCompletedTextView.setTextColor(getColor(R.color.black))
                    timeRemf9TextView.setTextColor(getColor(R.color.black))
                    timeRemf8TextView.setTextColor(getColor(R.color.black))

                    mYear = c[Calendar.YEAR]
                    mMonth = c[Calendar.MONTH]
                    mDay = c[Calendar.DAY_OF_MONTH]
                    mDate = mDay.toString() + "-" + (mMonth + 1).toString() +"-"+ mYear.toString()

                    var settingInTime = textViewInTime.text.toString()

                    editor.putString("INTIME",settingInTime)
                    editor.putString("TODAYDATE", mDate)
                    editor.apply()

                    hourLocal = LocalTime.now().hour
                    minuteLocal = LocalTime.now().minute

                    // Initializing the four different time values for many states
                    var d1: LocalTime = LocalTime.parse(textViewInTime.text.toString())                                        // Parse Time selector text as IN Time
                    var d2: LocalTime = LocalTime.now()                                                                        // Get local time from device
                    var d3: LocalTime = d1.plusHours(9)                                                             // Set Time plus 9 hr
                    var d4: LocalTime = d1.plusHours(8)                                                             // Set Time plus 8 hr
                    var d5: LocalTime = d1                                                                                     // Set just as placeholder

                    publicD1 = d1                                                                                              // save value public
                    publicD2 = d2                                                                                              //save value public
                    publicD3 = d3

                    if (textViewOutTime.text == "Out Time" || textViewOutTime.text == "") {
                        Toast.makeText(this@MainActivity, "Out time not selected.", Toast.LENGTH_SHORT).show()
                        textViewOutTime.text = d2.hour.toString() + ":" + d2.minute.toString()
                        d5  = d2
                    } else {
                        d5 = LocalTime.parse(textViewOutTime.text.toString())                                                   // Parse Time selector text as OUT Time
                    }

                    if (d1 > d5) {
                        Toast.makeText(this@MainActivity, "Out Time can not be before In Time.", Toast.LENGTH_SHORT).show()
                    }

                    if (d5 < d2){                                                                                                // Swap Out time with current Localtime if Already left
                        d2 = d5
                        Toast.makeText(this@MainActivity, "Swappped.", Toast.LENGTH_SHORT).show()
                    }


                    var diffCompleted: Int = ChronoUnit.MINUTES.between(d1, d2).toInt()  // Calculate difference between "In TIme" and "Current Time"
                    var completedHours = diffCompleted / 60                              // Convert Difference to Hours
                    var completedMinutes = diffCompleted % 60                            // Convert Difference to Minutes

                    timeCompletedTextView.text =
                        completedHours.toString() + "hr " + completedMinutes.toString() + "min"         // Print Completed time: (d2 - d1)
                    timeCompletedHomeTextView.text =
                        d2.hour.toString()                                                              // Print System current time: LocalTime HH
                    timeCompletedHomeMinTextView.text =
                        d2.minute.toString()                                                            // Print System Completed time current time: LocalTime MM

                    var diffRem9: Int = ChronoUnit.MINUTES.between(d2, d3).toInt()
                    var rem9Hours = diffRem9 / 60
                    var rem9Minuutes = diffRem9 % 60

                    timeRemf9TextView.text =
                        rem9Hours.toString() + "hr " + rem9Minuutes.toString() + "min"                     // Print Remaining time for 9hr: (d3 - d2)
                    timeRemf9HomeTextView.text =
                        d3.hour.toString()                                                                 // Print Final time for 9hr: d1 + 9hr HH
                    timeRemf9HomeMinTextView.text =
                        d3.minute.toString()                                                               // Print Final time for 9hr: d1 + 9hr MM

                    var diffRem8: Int = ChronoUnit.MINUTES.between(d2, d4).toInt()
                    var rem8Hours = diffRem8 / 60
                    var rem8Minutes = diffRem8 % 60

                    timeRemf8TextView.text =
                        rem8Hours.toString() + "hr " + rem8Minutes.toString() + "min"                      // Print Remaining time for 8hr: (d4 - d2)
                    timeRemf8HomeTextView.text =
                        d4.hour.toString()                                                                 // Print Final time for 8hr: d1 + 8hr HH
                    timeRemf8HomeMinTextView.text =
                        d4.minute.toString()                                                               // Print Final time for 8hr: d1 + 8hr MM
*//*
*/
/*
            Change color of TextView when completed the time
*//*
*/
/*
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
            else{
                Log.d("Not Working day","Today is not your working day.")
                Toast.makeText(this@MainActivity, "Today is not your working day.", Toast.LENGTH_SHORT).show()
            }

        }*//*


        imageViewPlay.setOnClickListener {
            //textViewInTime.text = LocalTime.now().hour.toString() + ":" + LocalTime.now().minute.toString()
            textViewInTime.text = LocalTime.now().toString()
            //textViewOutTime.text  = LocalTime.parse(textViewInTime.text).plusHours(9).toString()
            textViewOutTime.text = LocalTime.now().plusHours(9).toString()
            imageViewPlay.visibility = GONE
            imageViewStop.visibility = VISIBLE

            progressBar.visibility = VISIBLE
            textViewTimeRemaining.visibility = VISIBLE
            textViewPercent.visibility = VISIBLE

            var settingInTime = textViewInTime.text.toString()

            editor.putString("INTIME",settingInTime)
            editor.putString("TODAYDATE", mDate)
            editor.apply()

            ///////////////////////////LOOPER////////////////////////////
            if (LocalTime.now() < LocalTime.parse(textViewOutTime.text)) {
                Toast.makeText(this@MainActivityCopy, "IFF", Toast.LENGTH_SHORT).show()
                // ContDown Logic for Less than 9 hr
                var timeRemaining = ChronoUnit.MILLIS.between(LocalTime.now(), LocalTime.parse(sharedPreferences.getString("INTIME", "")).plusHours(9)).toLong()
                val timer = object : CountDownTimer(timeRemaining, 1000) {
                    override fun onTick(millisUntilFinished: Long) {

                        var minutesTotalRemaining = ChronoUnit.MINUTES.between(LocalTime.now(), LocalTime.parse(sharedPreferences.getString("INTIME", "")).plusHours(9)).toLong()
                        var hoursRemaining = minutesTotalRemaining / 60                              // Convert Difference to Hours
                        var minutesRemaining = minutesTotalRemaining % 60
                        textViewTimeRemaining.text = (hoursRemaining.toString() + "hr " + minutesRemaining.toString() + "min remaining")
                        val percentTime: Int = ((minutesTotalRemaining.toFloat() / 540) * 100).toInt()
                        progressBar.progress = 100 - percentTime

                    }

                    override fun onFinish() {
                        Toast.makeText(this@MainActivityCopy, timeRemaining.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
                timer.start()
            }

            else {
                Toast.makeText(this@MainActivityCopy, "ELSE", Toast.LENGTH_SHORT).show()
                Toast.makeText(this,"LOCAL " + LocalTime.now(), Toast.LENGTH_SHORT).show()
                // Countdown Logic for More than 9hr and less than 9.30 hr
                var timeRemaining = ChronoUnit.MILLIS.between(LocalTime.now(), LocalTime.parse(sharedPreferences.getString("INTIME", "")).plusHours(9).plusMinutes(30)).toLong()
                val timer = object : CountDownTimer(timeRemaining, 1000) {
                    override fun onTick(millisUntilFinished: Long) {

                        var minutesTotalRemaining = ChronoUnit.MINUTES.between(LocalTime.now(), LocalTime.parse(sharedPreferences.getString("INTIME", "")).plusHours(9)).toLong()
                        var minutesRemaining = minutesTotalRemaining % 60
                        textViewTimeRemaining.text = ("Working Extra for " + (-minutesRemaining).toString() + "min")
                        val percentTime: Int = ((minutesTotalRemaining.toFloat() / 30) * 100).toInt()
                        progressBar2.progress =  - percentTime
                        progressBar.progress = 100

                        if (-minutesRemaining > 30){
                            textViewTimeRemaining.text = ("You have completed 9.30 hr")
                        }

                    }

                    override fun onFinish() {
                        Toast.makeText(this@MainActivityCopy, timeRemaining.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
                timer.start()
            }

        }

        imageViewStop.setOnClickListener {
            if (textViewTimeCompleted.text != "Time")                                                                      // Check if there is any time set in "IN TIME"
                Toast.makeText(this@MainActivityCopy, "Please view time first.", Toast.LENGTH_SHORT).show()
            else {
                ///////////////////////////////////////////////////
                if (sharedPreferences.getBoolean(todayDay,false) == true) {                                             // Check if today is working day from SharedPreferences

                    if (textViewInTime.text == "In Time") {                                                                     // Check if there is any time set in "IN TIME"
                        Toast.makeText(this@MainActivityCopy, "Please select a In time.", Toast.LENGTH_SHORT).show()
                    }
                    else if (LocalTime.parse(textViewInTime.text) > LocalTime.parse("14:00")) {
                        Toast.makeText(this@MainActivityCopy, "Please select In time less than 2 PM.", Toast.LENGTH_SHORT).show()
                    }
                    else {

                        buttonSaveTime.visibility = View.VISIBLE
                        textView1.visibility = View.VISIBLE
                        textView2.visibility = View.VISIBLE
                        textView3.visibility = View.VISIBLE
                        textViewTimeCompleted.visibility = View.VISIBLE
                        textViewTimeRemf9.visibility = View.VISIBLE
                        textViewTimeRemf8.visibility = View.VISIBLE
                        textViewHomeTimeCompleted.visibility = View.VISIBLE
                        textViewHomeMinTimeCompleted.visibility = View.VISIBLE
                        textViewHomeTimeRemf9.visibility = View.VISIBLE
                        textViewHomeMinTimeRemf9.visibility = View.VISIBLE
                        textViewHomeTimeRemf8.visibility = View.VISIBLE
                        textViewHomeMinTimeRemf8.visibility = View.VISIBLE
                        // Initial textView Colors
                        timeCompletedTextView.setTextColor(getColor(R.color.black))
                        timeRemf9TextView.setTextColor(getColor(R.color.black))
                        timeRemf8TextView.setTextColor(getColor(R.color.black))

                        mYear = c[Calendar.YEAR]
                        mMonth = c[Calendar.MONTH]
                        mDay = c[Calendar.DAY_OF_MONTH]
                        mDate =
                            mDay.toString() + "-" + (mMonth + 1).toString() + "-" + mYear.toString()

                        var settingInTime = textViewInTime.text.toString()

                        editor.putString("INTIME", settingInTime)
                        editor.putString("TODAYDATE", mDate)
                        editor.apply()

                        hourLocal = LocalTime.now().hour
                        minuteLocal = LocalTime.now().minute

                        // Initializing the four different time values for many states
                        var d1: LocalTime = LocalTime.parse(textViewInTime.text.toString())                                        // Parse Time selector text as IN Time
                        var d2: LocalTime = LocalTime.now()                                                                        // Get local time from device
                        var d3: LocalTime = d1.plusHours(9)                                                             // Set Time plus 9 hr
                        var d4: LocalTime = d1.plusHours(8)                                                             // Set Time plus 8 hr
                        var d5: LocalTime = d1                                                                                     // Set just as placeholder

                        publicD1 = d1                                                                                              // save value public
                        publicD2 = d2                                                                                              //save value public
                        publicD3 = d3

                        if (textViewOutTime.text == "Out Time" || textViewOutTime.text == "") {
                            Toast.makeText(
                                this@MainActivityCopy,
                                "Out time not selected.",
                                Toast.LENGTH_SHORT
                            ).show()
                            //textViewOutTime.text = d2.hour.toString() + ":" + d2.minute.toString()
                            d5 = d2
                        } else {
                            d5 =
                                LocalTime.parse(textViewOutTime.text.toString())                                                   // Parse Time selector text as OUT Time
                        }

                        if (d1 > d5) {
                            Toast.makeText(
                                this@MainActivityCopy,
                                "Out Time can not be before In Time.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        if (d5 < d2) {                                                                                                // Swap Out time with current Localtime if Already left
                            d2 = d5
                            Toast.makeText(this@MainActivityCopy, "Swappped.", Toast.LENGTH_SHORT)
                                .show()
                        }


                        var diffCompleted: Int = ChronoUnit.MINUTES.between(d1, d2)
                            .toInt()  // Calculate difference between "In TIme" and "Current Time"
                        var completedHours =
                            diffCompleted / 60                              // Convert Difference to Hours
                        var completedMinutes =
                            diffCompleted % 60                            // Convert Difference to Minutes

                        timeCompletedTextView.text =
                            completedHours.toString() + "hr " + completedMinutes.toString() + "min"         // Print Completed time: (d2 - d1)
                        timeCompletedHomeTextView.text =
                            d2.hour.toString()                                                              // Print System current time: LocalTime HH
                        timeCompletedHomeMinTextView.text =
                            d2.minute.toString()                                                            // Print System Completed time current time: LocalTime MM

                        var diffRem9: Int = ChronoUnit.MINUTES.between(d2, d3).toInt()
                        var rem9Hours = diffRem9 / 60
                        var rem9Minuutes = diffRem9 % 60

                        timeRemf9TextView.text =
                            rem9Hours.toString() + "hr " + rem9Minuutes.toString() + "min"                     // Print Remaining time for 9hr: (d3 - d2)
                        timeRemf9HomeTextView.text =
                            d3.hour.toString()                                                                 // Print Final time for 9hr: d1 + 9hr HH
                        timeRemf9HomeMinTextView.text =
                            d3.minute.toString()                                                               // Print Final time for 9hr: d1 + 9hr MM

                        var diffRem8: Int = ChronoUnit.MINUTES.between(d2, d4).toInt()
                        var rem8Hours = diffRem8 / 60
                        var rem8Minutes = diffRem8 % 60

                        timeRemf8TextView.text =
                            rem8Hours.toString() + "hr " + rem8Minutes.toString() + "min"                      // Print Remaining time for 8hr: (d4 - d2)
                        timeRemf8HomeTextView.text =
                            d4.hour.toString()                                                                 // Print Final time for 8hr: d1 + 8hr HH
                        timeRemf8HomeMinTextView.text =
                            d4.minute.toString()                                                               // Print Final time for 8hr: d1 + 8hr MM
*/
/*
            Change color of TextView when completed the time
*//*

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
                ////////////////////////////////////////////////////
                //textViewOutTime.text = LocalTime.now().hour.toString() + ":" + LocalTime.now().minute.toString()
                popUpSaveToDB()  // Start POPUP
            }
        }

        buttonWeekDetail.setOnClickListener {
            val intent = Intent(this, WeekDetailActivity::class.java)
            startActivity(intent)
        }

        buttonSaveTime.setOnClickListener {
            if (textViewTimeCompleted.text == "Time")                                                                      // Check if there is any time set in "IN TIME"
                Toast.makeText(this@MainActivityCopy, "Please view time first.", Toast.LENGTH_SHORT).show()
            else {
                popUpSaveToDB()  // Start POPUP
            }

        }

    }
    override fun onBackPressed() {                                                                        // super.onBackPressed(); commented this line in order to disable back press
        Toast.makeText(applicationContext, "Back press disabled!", Toast.LENGTH_SHORT).show()
    }



    private fun popUpSaveToDB() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Time can only be saved once, Are you sure?")

        builder.setPositiveButton("OK",
            DialogInterface.OnClickListener { dialog, which ->
                // do something here on OK
                var dataDate = textViewTodayDate.text.toString()
                var dataInTime = textViewInTime.text.toString()
                var dataOutTime = textViewOutTime.text.toString()
                var dataTimeSpent = ChronoUnit.MINUTES.between(LocalTime.parse(dataInTime), LocalTime.parse(dataOutTime)).toString()

                if (dataTimeSpent.toInt() > 570)
                    dataTimeSpent = "570"

                var dataReg = "0"
                var dataWeekOfYear = c[Calendar.WEEK_OF_YEAR].toString()
                var dataLeave = "0"
                var dataMonthOfYear = c[Calendar.MONTH].toString()
                var dataDayOfWeek = c[Calendar.DAY_OF_WEEK].toString()

                var result = usersDBHelper.insertUser(
                    UserModel(
                        dataDate = dataDate,
                        dataInTime = dataInTime,
                        dataOutTime = dataOutTime,
                        dataTimeSpent = dataTimeSpent,
                        dataReg = dataReg,
                        dataWeekOfYear = dataWeekOfYear,
                        dataLeave = dataLeave,
                        dataMonthOfYear = dataMonthOfYear,
                        dataDayOfWeek = dataDayOfWeek
                    )
                )
                Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show()
            })
        builder.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        builder.show()
    }



}
*/
