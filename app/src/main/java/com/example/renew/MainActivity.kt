package com.example.renew

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.renew.AppConfig.AVG_HOURS_PER_DAY
import com.example.renew.AppConfig.MAX_HOURS_PER_DAY
import com.example.renew.AppConfig.MIN_HOURS_PER_DAY
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.String
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*


class MainActivity : AppCompatActivity() {

    var usersDBHelper = UsersDBHelper(this)
    private val c : Calendar = Calendar.getInstance()                                               // Init Calender Object c
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageButtonPlay.visibility = VISIBLE
        imageButtonStop.visibility = INVISIBLE
        textViewPercent.visibility = INVISIBLE
        textView9.visibility = INVISIBLE
        progressBar.visibility = INVISIBLE
        progressBar2.visibility = INVISIBLE

        textViewTimeRemaining.visibility = VISIBLE
        textViewAfterWelcome.visibility = INVISIBLE
        imageViewSleep.visibility = INVISIBLE

        //Initialization of SharedPreferences for storing settings
        val sharedPreferences = getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val handler = Handler()
        handler.post(object : Runnable {
            override fun run() {

                // Saving Calender Instance
                var mYear = c[Calendar.YEAR]
                var mMonth = c[Calendar.MONTH]
                var mDay = c[Calendar.DAY_OF_MONTH]
                var mDate = LocalDate.now().toString()
                var mWeekday = LocalDate.now().dayOfWeek.toString()
                var mMonthName = LocalDate.now().month.toString()

                textViewDetailedCurrentTime.text = mWeekday.substring(0,1).toUpperCase() + mWeekday.substring(1).toLowerCase() + ", " + mMonthName.substring(0,1) + mMonthName.substring(1,3).toLowerCase() + " " + mDay.toString() + " - " + LocalTime.now().format(formatter).toString()

                if (sharedPreferences.getString("TODAYDATE", "") == mDate.toString()) {
                    if (sharedPreferences.getString("INTIME", "") != "") {

                        imageButtonPlay.visibility = INVISIBLE
                        imageButtonStop.visibility = VISIBLE
                        textViewPercent.visibility = VISIBLE
                        textView9.visibility = VISIBLE
                        progressBar.visibility = VISIBLE
                        progressBar2.visibility = VISIBLE
                        textViewTimeRemaining.visibility = VISIBLE
                        textViewAfterWelcome.visibility = INVISIBLE
                        imageViewSleep.visibility = INVISIBLE

                        textViewInTime.text = sharedPreferences.getString("INTIME", "")
                        textViewExpt8.text = LocalTime.parse(textViewInTime.text).plusMinutes(MIN_HOURS_PER_DAY.toLong()).format(formatter).toString()
                        textViewExpt9.text = LocalTime.parse(textViewInTime.text).plusMinutes(AVG_HOURS_PER_DAY.toLong()).format(formatter).toString()
                        textViewExpt95.text = LocalTime.parse(textViewInTime.text).plusMinutes(MAX_HOURS_PER_DAY.toLong()).format(formatter).toString()

                        var tvInTime : LocalTime = LocalTime.parse(textViewInTime.text)
                        var tvOutTime9 : LocalTime = tvInTime.plusMinutes(AVG_HOURS_PER_DAY.toLong())
                        var tvOutTime95 : LocalTime = tvInTime.plusMinutes(MAX_HOURS_PER_DAY.toLong())

                        // ContDown Logic for Less than 9 hr
                        var timeRemaining = ChronoUnit.MILLIS.between(LocalTime.now(), tvOutTime95)
                        val timer = object : CountDownTimer(timeRemaining, 1000) {
                            override fun onTick(millisUntilFinished: Long) {

                                var minutesTotalRemaining = ChronoUnit.MINUTES.between(LocalTime.now(), tvOutTime95)

                                if (minutesTotalRemaining >= 30) {

                                    var hoursRemaining = (minutesTotalRemaining - 30) / 60                              // Convert Difference to Hours
                                    var minutesRemaining = (minutesTotalRemaining - 30) % 60

                                    textViewTimeRemaining.text = (hoursRemaining.toString() + "hr " + minutesRemaining.toString() + "min remaining")
                                    var percentTime: Float = (((minutesTotalRemaining.toFloat() - 30 ) / 540) * 100)
                                    progressBar.progress = (100 - percentTime).toInt()

                                    val rounded = String.format("%.2f", (100 - percentTime))
                                    textViewPercent.text = rounded + "%"

                                   // textViewPercent.text = (100 - percentTime).toString() + "%"

                                    if (LocalTime.now() > tvInTime.plusHours(8))
                                        textViewFunny.text = "Minimum time completed"
                                    else
                                        textViewFunny.text = "Minimum time not yet completed"
                                }

                                else if (minutesTotalRemaining < 30 && minutesTotalRemaining > 0) {
                                    var minutesExtra = (30 - minutesTotalRemaining)  % 60
                                    textViewTimeRemaining.text = ("You hv worked " + minutesExtra + "min extra. ")
                                    var percentTime: Int = ((minutesExtra.toFloat() / 30) * 100).toInt()
                                    progressBar.progress = 100
                                    progressBar2.progress = percentTime
                                    //progressBar.setBackgroundColor(3)
                                    progressBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN))

                                    textViewPercent.text = "100%"
                                    textViewFunny.text = "Working Extra"

                                }

                                else if (minutesTotalRemaining <= 0) {
                                    textViewTimeRemaining.text = ("You have exceeded 9.5 hr limit for today. ")
                                    progressBar.progress = 100
                                    progressBar2.progress = 100
                                    textViewPercent.text = "100%"
                                    textViewFunny.text = "9.5 Hour limit reached"

                                }

                            }

                            override fun onFinish() {
                                //Toast.makeText(this@MainActivity, timeRemaining.toString(), Toast.LENGTH_SHORT).show()
                                textViewTimeRemaining.text = ("You have exceeded 9.5 hr limit for today. ")
                                progressBar.progress = 0
                                progressBar2.progress = 100
                                textViewPercent.text = "100%"
                                textViewFunny.text = "9.5 Hour limit reached"
                            }
                        }
                        timer.start()


                    }

                    if (sharedPreferences.getString("OUTDATE", "") == mDate.toString()) {
                        if (sharedPreferences.getString("OUTTIME","") != "") {
                            textViewOutTime.text = LocalTime.parse(sharedPreferences.getString("OUTTIME","")).format(formatter).toString()
                            var finalInTime = LocalTime.parse(textViewInTime.text.toString())
                            var finalOutTime = LocalTime.parse(textViewOutTime.text.toString())
                            var finalSpentMinutes = ChronoUnit.MINUTES.between(finalInTime, finalOutTime)
                            if (finalSpentMinutes > 570) {
                                finalSpentMinutes = 570
                            }
                            textViewSpentTime.text = (finalSpentMinutes/60).toString().padStart(2, '0') + ":" + (finalSpentMinutes%60).toString().padStart(2, '0')

                            if (finalSpentMinutes < 540){
                                textViewAfterWelcome.text = "You have worked " + (540 - finalSpentMinutes) + " min less today."
                            }
                            else {
                                textViewAfterWelcome.text = "You have worked " + (finalSpentMinutes - 540) + " min extra today."
                            }
                            imageButtonStop.visibility = INVISIBLE
                            textViewPercent.visibility = INVISIBLE
                            textView9.visibility = INVISIBLE
                            progressBar.visibility = INVISIBLE
                            progressBar2.visibility = INVISIBLE
                            textViewTimeRemaining.visibility = INVISIBLE
                            textViewAfterWelcome.visibility = VISIBLE
                            imageViewSleep.visibility = VISIBLE

                        }
                    }

                }



                handler.postDelayed(this, 1000) // set time here to refresh textView
            }
        })

        // In Time Picker Logic.
        imageView.setOnClickListener {
            val intent = Intent(this, WeekdetailsActivity::class.java)
            startActivity(intent)
        }

        imageButtonPlay.setOnClickListener {

            if (LocalDate.now().dayOfWeek.toString() == "SUNDAY" || LocalDate.now().dayOfWeek.toString() == "SATURDAY") {
                Toast.makeText(this@MainActivity, "Today is not not your Working Day", Toast.LENGTH_SHORT).show()
            }
            else if (LocalTime.now() > LocalTime.parse("14:00")) {
                Toast.makeText(this@MainActivity, "Apply leave its 2 PM already.", Toast.LENGTH_SHORT).show()
            }
            else {

                imageButtonPlay.visibility = INVISIBLE
                imageButtonStop.visibility = VISIBLE
                textViewPercent.visibility = VISIBLE
                textView9.visibility = VISIBLE
                progressBar.visibility = VISIBLE
                progressBar2.visibility = VISIBLE

                textViewTimeRemaining.visibility = VISIBLE
                textViewAfterWelcome.visibility = INVISIBLE
                imageViewSleep.visibility = INVISIBLE

                textViewInTime.text = LocalTime.now().format(formatter).toString()
                textViewExpt8.text = LocalTime.now().plusHours(8).format(formatter).toString()
                textViewExpt9.text = LocalTime.now().plusHours(9).format(formatter).toString()
                textViewExpt95.text = LocalTime.now().plusHours(9).plusMinutes(30).format(formatter).toString()

                // Saving Calender Instance
                var mYear = c[Calendar.YEAR]
                var mMonth = c[Calendar.MONTH]
                var mDay = c[Calendar.DAY_OF_MONTH]
                var mDate = LocalDate.now().toString()

                editor.putString("INTIME", textViewInTime.text.toString())
                editor.putString("TODAYDATE", mDate)
                editor.apply()

                var tvOutTime95 : LocalTime = LocalTime.now().plusHours(9).plusMinutes(30)

                // ContDown Logic for Less than 9 hr
                var timeRemaining = ChronoUnit.MILLIS.between(LocalTime.now(), tvOutTime95)
                val timer = object : CountDownTimer(timeRemaining, 1000) {
                    override fun onTick(millisUntilFinished: Long) {

                        var minutesTotalRemaining = ChronoUnit.MINUTES.between(LocalTime.now(), tvOutTime95)

                        if (minutesTotalRemaining >= 30) {

                            var hoursRemaining =
                                (minutesTotalRemaining - 30) / 60                              // Convert Difference to Hours
                            var minutesRemaining = (minutesTotalRemaining - 30) % 60

                            textViewTimeRemaining.text =
                                (hoursRemaining.toString() + "hr " + minutesRemaining.toString() + "min remaining")
                            var percentTime: Float = (((minutesTotalRemaining.toFloat() - 30) / 540) * 100).toFloat()
                            progressBar.progress = (100 - percentTime).toInt()
                            //textViewPercent.text = (100 - percentTime).toString() + "%"

                            val rounded = String.format("%.2f", (100 - percentTime))
                            textViewPercent.text = rounded + "%"

                            if (LocalTime.now() > LocalTime.now().plusHours(8))
                                textViewFunny.text = "Minimum time completed"
                            else
                                textViewFunny.text = "Minimum time not yet completed"
                        }

                        else if (minutesTotalRemaining < 30 && minutesTotalRemaining > 0) {
                            var minutesExtra = (30 - minutesTotalRemaining)  % 60

                            textViewTimeRemaining.text = ("You have worked " + minutesExtra + "min extra. ")
                            var percentTime: Int = ((minutesExtra.toFloat() / 30) * 100).toInt()
                            progressBar.progress = 100

                            progressBar2.progress = percentTime.toInt()

                            textViewPercent.text = "100%"
                            textViewFunny.text = "Working Extra"

                        }

                        else if (minutesTotalRemaining <= 0) {
                            textViewTimeRemaining.text = ("You hv exceeded 9.5 hr limit for today. ")
                            //progressBar.progress = 100
                            progressBar2.progress = 100
                            textViewPercent.text = "100%"
                            textViewFunny.text = "9.5 Hour limit reached"

                        }

                    }

                    override fun onFinish() {
                        //Toast.makeText(this@MainActivity, timeRemaining.toString(), Toast.LENGTH_SHORT).show()
                        textViewTimeRemaining.text = ("You hv exceeded 9.5 hr limit for today. ")
                        progressBar.progress = 0
                        progressBar2.progress = 100
                        textViewPercent.text = "100%"
                        textViewFunny.text = "9.5 Hour limit reached"

                    }
                }
                timer.start()
            }
        }

        imageButtonStop.setOnClickListener {
            var finalOutTime = LocalTime.now()
            var finalInTime = LocalTime.parse(sharedPreferences.getString("INTIME", ""))
            var finalSpentMinutes = ChronoUnit.MINUTES.between(finalInTime, finalOutTime)

            if (finalSpentMinutes.toString().toInt() < (8*60)) {
                Toast.makeText(this, "Please complete Minimum 8 Hours.", Toast.LENGTH_SHORT).show()
            } else {
                textViewOutTime.text = finalOutTime.format(formatter).toString()
                textViewSpentTime.text = (finalSpentMinutes/60).toString().padStart(2, '0') + ":" + (finalSpentMinutes%60).toString().padStart(2, '0')

                popUpSaveToDB()
            }

        }

    }

    private fun popUpSaveToDB() {
        imageButtonStop.visibility = View.INVISIBLE // JUST FOR POPUP
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Time can only be saved once, Are you sure?")
        builder.setPositiveButton("OK",
            DialogInterface.OnClickListener { dialog, which ->
                // do something here on OK

                imageButtonStop.visibility = View.VISIBLE // Pop Stop
                var dataDate =  LocalDate.now().toString()
                var dataInTime = textViewInTime.text.toString()
                var dataOutTime = textViewOutTime.text.toString()
                var dataTimeSpent = ChronoUnit.MINUTES.between(LocalTime.parse(dataInTime), LocalTime.parse(dataOutTime)).toString()

                if (dataTimeSpent.toInt() > 570)
                    dataTimeSpent = "570"

                var dataReg = "No"
                var dataWeekOfYear = c[Calendar.WEEK_OF_YEAR].toString()
                var dataLeave = "No"
                var dataMonthOfYear = c[Calendar.MONTH].toString()
                var dataDayOfWeek = c[Calendar.DAY_OF_WEEK].toString()

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

                val sharedPreferences = getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("OUTTIME", textViewOutTime.text.toString())
                editor.putString("OUTDATE", LocalDate.now().toString())
                editor.apply()
            })
        builder.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel()
                imageButtonStop.visibility = View.VISIBLE // pop Stop
                textViewOutTime.text = "-"
                textViewSpentTime.text = "-"

            })
        builder.show()
    }

    override fun onStop() {
        super.onStop()
        finish()
    }

    override fun onBackPressed() {                                                                        // super.onBackPressed(); commented this line in order to disable back press
        Toast.makeText(applicationContext, "Back press disabled!", Toast.LENGTH_SHORT).show()
    }
}
