package com.example.renew

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import android.widget.Toast
import com.example.renew.AppConfig.AVG_HOURS_PER_DAY
import com.example.renew.AppConfig.MAX_HOURS_PER_DAY
import com.example.renew.AppConfig.MAX_WEEK_HOURS
import com.example.renew.AppConfig.MINIMUM_TIME_RESTRICTION
import com.example.renew.AppConfig.MIN_HOURS_PER_DAY
import com.example.renew.AppConfig.TOAST_FUTURE
import com.example.renew.AppConfig.TOAST_MAXTIME
import com.example.renew.AppConfig.TOAST_MINTIME
import com.example.renew.AppConfig.TOAST_SAMEDAY_LEAVE_REG
import com.example.renew.AppConfig.TOAST_TIME_SELECTION_ERROR
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_weekdetails.*
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.Year
import java.time.temporal.ChronoUnit
import java.util.*

class WeekdetailsActivity : AppCompatActivity() {

    var usersDBHelper = UsersDBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weekdetails)

        textView20.visibility = INVISIBLE
        buttonWeekCancel.visibility = INVISIBLE
        buttonWeekSave.visibility = INVISIBLE
        textViewInTimeFetched.isEnabled = false
        textViewOutTimeFetched.isEnabled = false
        textViewRegFetched.isEnabled = false
        textViewLeaveFetched.isEnabled = false

        textViewWeekFetchDate.text = LocalDate.now().toString()
        var users = usersDBHelper.readUser(textViewWeekFetchDate.text.toString())
        users.forEach {

            textViewInTimeFetched.text = it.dataInTime
            textViewOutTimeFetched.text = it.dataOutTime
            textViewSpentTimeFetched.text = it.dataTimeSpent
            textViewRegFetched.text = it.dataReg
            textViewLeaveFetched.text = it.dataLeave

        }

        // Completed This week
        val c = Calendar.getInstance()
        var mWeek = c[Calendar.WEEK_OF_YEAR]
        var mMonth = c[Calendar.MONTH]
        var totalWeekTime: Int =
            usersDBHelper.readTimeCompleted(mWeek.toString()).toString().toInt()
        var weekHours = totalWeekTime / 60
        var weekMinutes = totalWeekTime % 60

        textViewWeekCompleted.text = weekHours.toString().padStart(2, '0') + ":" + weekMinutes.toString().padStart(2, '0')
        textViewRegularise.text = usersDBHelper.getRegulariseMonth(mMonth.toString()).toString()
        textViewLeaves.text = usersDBHelper.getLeaveMonth(mMonth.toString()).toString()

        var remainingWeekTime = MAX_WEEK_HOURS - weekHours * 60 - weekMinutes
        var remainingWeekHours = remainingWeekTime / 60
        var remainingWeekMinutes = remainingWeekTime % 60
        textViewWeekRemaining.text = remainingWeekHours.toString().padStart(2, '0') + ":" + remainingWeekMinutes.toString().padStart(2, '0')

        textViewWeekOverViewName.text = "Week " + c[Calendar.WEEK_OF_MONTH].toString()
        textViewMonthOverViewName.text = DateFormatSymbols().months[c[Calendar.MONTH]]
        textViewDayOverViewName.text = DateFormatSymbols().weekdays[c[Calendar.DAY_OF_WEEK]]


        imageButtonWeekNextDate.setOnClickListener {

            textViewWeekFetchDate.text =
                LocalDate.parse(textViewWeekFetchDate.text).plusDays(1).toString()
            var dataDate = textViewWeekFetchDate.text.toString()
            var users = usersDBHelper.readUser(dataDate)

            if (users.toString() == "[]") {
                textViewInTimeFetched.text = "-"
                textViewOutTimeFetched.text = "-"
                textViewSpentTimeFetched.text = "-"
                textViewHalfDayFetched.text = "-"
                textViewRegFetched.text = "-"
                textViewLeaveFetched.text = "-"
            }

            users.forEach {

                textViewInTimeFetched.text = it.dataInTime
                textViewOutTimeFetched.text = it.dataOutTime
                textViewSpentTimeFetched.text = it.dataTimeSpent
                textViewRegFetched.text = it.dataReg
                textViewLeaveFetched.text = it.dataLeave

            }

            val d = Calendar.getInstance()
            var tempDate = textViewWeekFetchDate.text.toString()
            var tempparts = tempDate.split("-")
            d.set(tempparts[0].toInt(), tempparts[1].toInt() - 1, tempparts[2].toInt())

            var tWeek = d[Calendar.WEEK_OF_YEAR]
            var tMonth = d[Calendar.MONTH]
            textViewWeekOverViewName.text = "Week " + d[Calendar.WEEK_OF_MONTH].toString()
            textViewMonthOverViewName.text = DateFormatSymbols().months[d[Calendar.MONTH]]
            textViewDayOverViewName.text = DateFormatSymbols().weekdays[d[Calendar.DAY_OF_WEEK]]

            var totalWeekTime: Int =
                usersDBHelper.readTimeCompleted(tWeek.toString()).toString().toInt()
            var weekHours = totalWeekTime / 60
            var weekMinutes = totalWeekTime % 60

//                Toast.makeText(this, usersDBHelper.readTimeCompleted(tWeek.toString()).toString().toInt(), Toast.LENGTH_SHORT).show()


            textViewWeekCompleted.text = weekHours.toString().padStart(2, '0') + ":" + weekMinutes.toString().padStart(2, '0')
            textViewRegularise.text = usersDBHelper.getRegulariseMonth(tMonth.toString()).toString()
            textViewLeaves.text = usersDBHelper.getLeaveMonth(tMonth.toString()).toString()

            var remainingWeekTime = MAX_WEEK_HOURS - weekHours * 60 - weekMinutes
            var remainingWeekHours = remainingWeekTime / 60
            var remainingWeekMinutes = remainingWeekTime % 60
            textViewWeekRemaining.text = remainingWeekHours.toString().padStart(2, '0') + ":" + remainingWeekMinutes.toString().padStart(2, '0')

        }

        imageButtonWeekPreviousDate.setOnClickListener {
            textViewWeekFetchDate.text = LocalDate.parse(textViewWeekFetchDate.text).minusDays(1).toString()
            var dataDate = textViewWeekFetchDate.text.toString()
            var users = usersDBHelper.readUser(dataDate)

            if (users.toString() == "[]") {
                textViewInTimeFetched.text = "-"
                textViewOutTimeFetched.text = "-"
                textViewSpentTimeFetched.text = "-"
                textViewHalfDayFetched.text = "-"
                textViewRegFetched.text = "-"
                textViewLeaveFetched.text = "-"
            }

            users.forEach {

                textViewInTimeFetched.text = it.dataInTime
                textViewOutTimeFetched.text = it.dataOutTime
                textViewSpentTimeFetched.text = it.dataTimeSpent
                textViewRegFetched.text = it.dataReg
                textViewLeaveFetched.text = it.dataLeave
            }

            val d = Calendar.getInstance()
            var tempDate = textViewWeekFetchDate.text.toString()
            var tempparts = tempDate.split("-")
            d.set(tempparts[0].toInt(), tempparts[1].toInt() - 1, tempparts[2].toInt())

            var tWeek = d[Calendar.WEEK_OF_YEAR]
            var tMonth = d[Calendar.MONTH]
            textViewWeekOverViewName.text = "Week " + d[Calendar.WEEK_OF_MONTH].toString()
            textViewMonthOverViewName.text = DateFormatSymbols().months[d[Calendar.MONTH]]
            textViewDayOverViewName.text = DateFormatSymbols().weekdays[d[Calendar.DAY_OF_WEEK]]

            var totalWeekTime: Int =
                usersDBHelper.readTimeCompleted(tWeek.toString()).toString().toInt()
            var weekHours = totalWeekTime / 60
            var weekMinutes = totalWeekTime % 60

            textViewWeekCompleted.text = weekHours.toString().padStart(2, '0') + ":" + weekMinutes.toString().padStart(2, '0')
            textViewRegularise.text = usersDBHelper.getRegulariseMonth(tMonth.toString()).toString()
            textViewLeaves.text = usersDBHelper.getLeaveMonth(tMonth.toString()).toString()

            var remainingWeekTime = MAX_WEEK_HOURS - weekHours * 60 - weekMinutes
            var remainingWeekHours = remainingWeekTime / 60
            var remainingWeekMinutes = remainingWeekTime % 60
            textViewWeekRemaining.text = remainingWeekHours.toString().padStart(2, '0') + ":" + remainingWeekMinutes.toString().padStart(2, '0')

        }


        imageViewBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        textViewWeekFetchDate.setOnClickListener {
            val cal = Calendar.getInstance()
            val dateSetListener =
                DatePickerDialog.OnDateSetListener { datePicker, year, month, dayOfMonth ->
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, month)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    textViewWeekFetchDate.text = SimpleDateFormat("YYYY-MM-DD").format(cal.time)                // HH:mm format Out Time

                    val d = Calendar.getInstance()
                    var tempDate = textViewWeekFetchDate.text.toString()
                    var tempparts = tempDate.split("-")
                    d.set(tempparts[0].toInt(), tempparts[1].toInt() - 1, tempparts[2].toInt())

                    var tWeek = d[Calendar.WEEK_OF_YEAR]
                    var tMonth = d[Calendar.MONTH]

                    textViewWeekOverViewName.text = "Week " + d[Calendar.WEEK_OF_MONTH].toString()
                    textViewMonthOverViewName.text = DateFormatSymbols().months[d[Calendar.MONTH]]
                    textViewDayOverViewName.text = DateFormatSymbols().weekdays[d[Calendar.DAY_OF_WEEK]]

                    var totalWeekTime: Int = usersDBHelper.readTimeCompleted(tWeek.toString()).toString().toInt()
                    var weekHours = totalWeekTime / 60
                    var weekMinutes = totalWeekTime % 60

                    textViewWeekCompleted.text = weekHours.toString().padStart(2, '0') + ":" + weekMinutes.toString().padStart(2, '0')
                    textViewRegularise.text = usersDBHelper.getRegulariseMonth(tMonth.toString()).toString()
                    textViewLeaves.text = usersDBHelper.getLeaveMonth(tMonth.toString()).toString()

                    var remainingWeekTime = MAX_WEEK_HOURS - weekHours * 60 - weekMinutes
                    var remainingWeekHours = remainingWeekTime / 60
                    var remainingWeekMinutes = remainingWeekTime % 60
                    textViewWeekRemaining.text = remainingWeekHours.toString().padStart(2, '0') + ":" + remainingWeekMinutes.toString().padStart(2, '0')
                }

            DatePickerDialog(
                this,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }


        textViewInTimeFetched.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                textViewInTimeFetched.text =
                    SimpleDateFormat("HH:mm").format(cal.time)                // HH:mm format Out Time

                if (textViewInTimeFetched.text != "-" && textViewOutTimeFetched.text != "-")
                    textViewSpentTimeFetched.text = ChronoUnit.MINUTES.between(
                        LocalTime.parse(textViewInTimeFetched.text),
                        LocalTime.parse(textViewOutTimeFetched.text)
                    ).toString()

            }

            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                false
            ).show()

        }

        textViewOutTimeFetched.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                textViewOutTimeFetched.text =
                    SimpleDateFormat("HH:mm").format(cal.time)                // HH:mm format Out Time

                if (textViewInTimeFetched.text != "-" && textViewOutTimeFetched.text != "-")
                    textViewSpentTimeFetched.text = ChronoUnit.MINUTES.between(
                        LocalTime.parse(textViewInTimeFetched.text),
                        LocalTime.parse(textViewOutTimeFetched.text)
                    ).toString()

            }

            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                false
            ).show()

        }

        textViewRegFetched.setOnClickListener {
            if (textViewRegFetched.text == "No") {
                if (textViewLeaveFetched.text == "Yes") {
                    Toast.makeText(this, TOAST_SAMEDAY_LEAVE_REG, Toast.LENGTH_SHORT).show()
                } else {
                    textViewRegFetched.text = "Yes"
                }
            } else {
                textViewRegFetched.text = "No"
            }

        }

        textViewLeaveFetched.setOnClickListener {
            if (textViewLeaveFetched.text == "No") {
                if (textViewRegFetched.text == "Yes") {
                    Toast.makeText(this, TOAST_SAMEDAY_LEAVE_REG, Toast.LENGTH_SHORT).show()
                } else {
                    textViewLeaveFetched.text = "Yes"
                }
            } else {
                textViewLeaveFetched.text = "No"
            }
        }

        buttonWeekSave.setOnClickListener {
            if (textViewSpentTimeFetched.text == "-" || textViewSpentTimeFetched.text.toString().toInt() < 0) {
                Toast.makeText(this, TOAST_TIME_SELECTION_ERROR, Toast.LENGTH_SHORT).show()
            } else if (textViewSpentTimeFetched.text.toString().toInt() > MAX_HOURS_PER_DAY) {
                Toast.makeText(this, TOAST_MAXTIME, Toast.LENGTH_SHORT).show()
                textViewSpentTimeFetched.text = MAX_HOURS_PER_DAY.toString()
                popUpEditText()
            } else if ((textViewSpentTimeFetched.text.toString().toInt() < MIN_HOURS_PER_DAY) && MINIMUM_TIME_RESTRICTION) {
                Toast.makeText(this, TOAST_MINTIME, Toast.LENGTH_SHORT).show()
            } else {
                popUpEditText()
            }
        }

        buttonWeekCancel.setOnClickListener {
            val intent = Intent(this, WeekdetailsActivity::class.java)
            startActivity(intent)
        }

        imageViewEditTime.setOnClickListener {
            textView20.visibility = VISIBLE
            buttonWeekCancel.visibility = VISIBLE
            buttonWeekSave.visibility = VISIBLE
            textViewInTimeFetched.isEnabled = true
            textViewOutTimeFetched.isEnabled = true
            textViewRegFetched.isEnabled = true
            textViewLeaveFetched.isEnabled = true

            banner5.setBackgroundColor(getColor(R.color.colorEditMode))
        }


    }


    private fun popUpEditText() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Sure, save it?")
        //val input = CheckBox(this)
        //input.text ="Are you sure to save it."
        //input.marginTop = 9
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        //input.layoutParams = lp
        //builder.setView(input)
        // Set up the buttons
        builder.setPositiveButton("YES",
            DialogInterface.OnClickListener { dialog, which ->

                val d = Calendar.getInstance()
                var dataDate = textViewWeekFetchDate.text.toString()
                var tempparts = dataDate.split("-")
                d.set(tempparts[0].toInt(), tempparts[1].toInt() - 1, tempparts[2].toInt())
                //d.set(2019,1, 1)
                Toast.makeText(this, d[Calendar.YEAR].toString() + "-" + d[Calendar.MONTH].toString() + "-" + d[Calendar.DATE].toString(), Toast.LENGTH_SHORT).show()

                var dataInTime = textViewInTimeFetched.text.toString()
                var dataOutTime = textViewOutTimeFetched.text.toString()
                var dataTimeSpent = textViewSpentTimeFetched.text.toString()
                var dataReg = textViewRegFetched.text.toString()
                var dataWeekOfYear = d[Calendar.WEEK_OF_YEAR].toString()
                var dataLeave = textViewLeaveFetched.text.toString()
                var dataMonthOfYear = d[Calendar.MONTH].toString()
                var dataDayOfWeek = d[Calendar.DAY_OF_WEEK].toString()

                if (dataReg == "-")
                    dataReg = "No"

                if (dataLeave == "-")
                    dataLeave = "No"

                if (dataLeave == "Yes")
                    dataTimeSpent = AVG_HOURS_PER_DAY.toString()


                var result = usersDBHelper.deleteUser(dataDate)
                Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show()

                result = usersDBHelper.insertUser(
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

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            })
        builder.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, which ->

                dialog.cancel()
            })
        builder.show()
    }


    override fun onStop() {
        super.onStop()
        finish()
    }

    override fun onBackPressed() {                                                                        // super.onBackPressed(); commented this line in order to disable back press
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}