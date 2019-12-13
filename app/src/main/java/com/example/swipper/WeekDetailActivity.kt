package com.example.swipper

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_config.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_weekdetail.*
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.*

class WeekDetailActivity :  AppCompatActivity() {
    val c = Calendar.getInstance()
    var usersDBHelper = UsersDBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weekdetail)



        buttonEditWeekSave.visibility = View.INVISIBLE
        buttonWeekEdit.visibility = View.INVISIBLE
        textViewEditedInTime.visibility = View.INVISIBLE
        textViewEditedOutTime.visibility = View.INVISIBLE
        textViewEditedTimeSpent.visibility = View.INVISIBLE
        checkBoxEditWeekRegularize.visibility = View.INVISIBLE
        buttonEditWeekApplyLeave.visibility = View.INVISIBLE
        buttonEditWeekInTime.visibility = View.INVISIBLE
        buttonEditWeekOutTime.visibility = View.INVISIBLE
        textViewInTimeFetched.visibility = View.INVISIBLE
        textViewOutTimeFetched.visibility = View.INVISIBLE
        textViewTimeSpentFetched.visibility = View.INVISIBLE
        textViewRegFetched.visibility = View.INVISIBLE
        textViewLeaveFetched.visibility = View.INVISIBLE
        textView8.visibility =View.INVISIBLE
        textView9.visibility = View.INVISIBLE
        textView10.visibility =View.INVISIBLE
        textView11.visibility = View.INVISIBLE
        textView16.visibility = View.INVISIBLE

        var d1: LocalTime? = null
        var d2: LocalTime? = null




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
        //val c = Calendar.getInstance()
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

            if(buttonDateSelectorWeekDetail.text == "Select Date"){
                Toast.makeText(this, "Please select a Date first", Toast.LENGTH_SHORT).show()
            } else {

                buttonWeekEdit.visibility =View.VISIBLE
                textView8.visibility =View.VISIBLE
                textView9.visibility = View.VISIBLE
                textView10.visibility =View.VISIBLE
                textView11.visibility = View.VISIBLE
                textView16.visibility = View.VISIBLE

                textViewInTimeFetched.visibility = View.VISIBLE
                textViewOutTimeFetched.visibility = View.VISIBLE
                textViewTimeSpentFetched.visibility = View.VISIBLE
                textViewRegFetched.visibility = View.VISIBLE
                textViewLeaveFetched.visibility = View.VISIBLE

                users.forEach {

                    textViewInTimeFetched.text = it.dataInTime
                    textViewOutTimeFetched.text = it.dataOutTime
                    textViewTimeSpentFetched.text = it.dataTimeSpent
                    textViewRegFetched.text = it.dataReg
                    textViewLeaveFetched.text = it.dataLeave

                }
            }
        }

        /*var d1: LocalTime? = null
        var d2: LocalTime? = null*/
        buttonEditWeekInTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                textViewEditedInTime.text = SimpleDateFormat("HH:mm").format(cal.time)                // HH:mm format Out Time
                d1 = LocalTime.parse(textViewEditedInTime.text.toString())
                //hourSetOut = timePicker.hour
                //minuteSetOut = timePicker.minute
            }

            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                false
            ).show()
        }

        buttonEditWeekOutTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                textViewEditedOutTime.text = SimpleDateFormat("HH:mm").format(cal.time)                // HH:mm format Out Time
                d2 = LocalTime.parse(textViewEditedOutTime.text.toString())

                Toast.makeText(this, "Yssss" + ChronoUnit.MINUTES.between(d1, d2).toInt().toString(), Toast.LENGTH_SHORT).show()
                val editedTimeSpent = ChronoUnit.MINUTES.between(d1, d2).toInt()
                textViewEditedTimeSpent.text = editedTimeSpent.toString()
                //hourSetOut = timePicker.hour
                //minuteSetOut = timePicker.minute
            }

            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                false
            ).show()

        }

        buttonEditWeekApplyLeave.setOnClickListener {
            popUpEditText()
        }
        //Toast.makeText(this, "Yssss" + ChronoUnit.MINUTES.between(d1, d2).toInt().toString(), Toast.LENGTH_SHORT).show()
        //editTextTest.setText(ChronoUnit.MINUTES.between(d1, d2).toInt().toString())

        buttonEditWeekSave.setOnClickListener {
            popUpRegularize()
            /*var dataDate = buttonDateSelectorWeekDetail.text.toString()
            var dataInTime = textViewEditedInTime.text.toString()
            var dataOutTime = textViewEditedInTime.text.toString()
            var dataTimeSpent = textViewEditedTimeSpent.text.toString()
            var dataWeekOfYear = c[Calendar.WEEK_OF_YEAR].toString()
            var dataLeave = "false"
            var dataReg : String? =  null
            if(checkBoxEditWeekRegularize.isChecked){
                dataReg = "true"
            } else {
                dataReg = "false"
            }

            if(textViewTimeSpentFetched.text.toString() == "TEST"){
                Toast.makeText(this, "Please fetch data first", Toast.LENGTH_SHORT).show()
            } else {

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
                        dataLeave = dataLeave
                    )
                )
                Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show()
            }*/
        }

        buttonWeekEdit.setOnClickListener {
            if(buttonDateSelectorWeekDetail.text == "Select Date"){
                Toast.makeText(this, "Please select a Date first", Toast.LENGTH_SHORT).show()
            } else {

                buttonEditWeekSave.visibility = View.VISIBLE
                buttonWeekEdit.visibility = View.VISIBLE
                textViewEditedInTime.visibility = View.VISIBLE
                textViewEditedOutTime.visibility = View.VISIBLE
                textViewEditedTimeSpent.visibility = View.VISIBLE
                checkBoxEditWeekRegularize.visibility = View.VISIBLE
                buttonEditWeekApplyLeave.visibility = View.VISIBLE
                buttonEditWeekInTime.visibility = View.VISIBLE
                buttonEditWeekOutTime.visibility = View.VISIBLE

                buttonWeekEdit.visibility = View.INVISIBLE

            }
        }

        buttonCancelWeekEdit.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }



    override fun onBackPressed() {                                                                        // super.onBackPressed(); commented this line in order to disable back press
        Toast.makeText(applicationContext, "Back press disabled!", Toast.LENGTH_SHORT).show()
    }

    private fun popUpEditText() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Do you really want to apply Leave?")
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

                var dataDate = buttonDateSelectorWeekDetail.text.toString()
                var dataInTime = "NA"
                var dataOutTime = "NA"
                var dataTimeSpent = "540"
                var dataReg = "false"
                var dataWeekOfYear = c[Calendar.WEEK_OF_YEAR].toString()
                var dataLeave = "true"

                if(buttonDateSelectorWeekDetail.text == "Select Date"){
                    Toast.makeText(this, "Please select a Date first", Toast.LENGTH_SHORT).show()
                } else {

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
                            dataLeave = dataLeave
                        )
                    )
                    Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            })
        builder.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        builder.show()
    }


    private fun popUpRegularize() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Do you really want to apply Leave?")
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
                var dataDate = buttonDateSelectorWeekDetail.text.toString()
                var dataInTime = textViewEditedInTime.text.toString()
                var dataOutTime = textViewEditedInTime.text.toString()
                var dataTimeSpent = textViewEditedTimeSpent.text.toString()
                var dataWeekOfYear = c[Calendar.WEEK_OF_YEAR].toString()
                var dataLeave = "false"
                var dataReg : String? =  null
                if(checkBoxEditWeekRegularize.isChecked){
                    dataReg = "true"
                } else {
                    dataReg = "false"
                }

                if (LocalTime.parse(textViewEditedInTime.text) > LocalTime.parse("14:00")) {
                    Toast.makeText(this, "Please select In time less than 2 PM.", Toast.LENGTH_SHORT).show()
                } else {
                if(textViewTimeSpentFetched.text.toString() == "TEST"){
                    Toast.makeText(this, "Please fetch data first", Toast.LENGTH_SHORT).show()
                } else {

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
                            dataLeave = dataLeave
                        )
                    )
                    Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show()
                }}
            })
        builder.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        builder.show()
    }
}
