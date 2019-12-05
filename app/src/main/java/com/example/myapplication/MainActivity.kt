package com.example.myapplication

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.joda.time.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mPickInTimeBtn = findViewById<Button>(R.id.pickInTimeBtn)
        val textViewInTime = findViewById<TextView>(R.id.textViewInTime)
        //val mPickOutTimeBtn = findViewById<Button>(R.id.pickOutTimeBtn)
        //val textViewOutTime = findViewById<TextView>(R.id.textViewOutTime)
        val submitBtn = findViewById<Button>(R.id.buttonSubmit)
        val timeCompletedTextView = findViewById<TextView>(R.id.textViewTimeCompleted)
        val timeRemf8TextView = findViewById<TextView>(R.id.textViewTimeRemf8)
        val timeRemf9TextView = findViewById<TextView>(R.id.textViewTimeRemf9)

        val timeCompletedHomeTextView = findViewById<TextView>(R.id.textViewHomeTimeCompleted)
        val timeRemf8HomeTextView = findViewById<TextView>(R.id.textViewHomeTimeRemf8)
        val timeRemf9HomeTextView = findViewById<TextView>(R.id.textViewHomeTimeRemf9)

        mPickInTimeBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                textViewInTime.text = SimpleDateFormat("HH:mm").format(cal.time)
            }

            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                false
            ).show()
        }

        /*mPickOutTimeBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                textViewOutTime.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }*/

        submitBtn.setOnClickListener {

            val format =
                SimpleDateFormat("HH:mm")
            val localTime = LocalTime()
            var d1: Date? = null
            var d2: Date? = null
            Toast.makeText(this@MainActivity, localTime.toString() , Toast.LENGTH_SHORT).show()
            try {
                d1 = format.parse(textViewInTime.text.toString())
                d2 = format.parse(localTime.toString())
                val dt1 = DateTime(d1)
                val dt2 = DateTime(d2)
                //timeCompletedTextView.text = Minutes.minutesBetween(dt1, dt2).getMinutes() .toString()
                val t = Minutes.minutesBetween(dt1, dt2).getMinutes()
                val hours = t / 60 //since both are ints, you get an int
                val minutes = t % 60
                timeCompletedTextView.text = hours.toString() + "hr " + minutes.toString() + "min"
                timeCompletedHomeTextView.text =  SimpleDateFormat("HH:mm").format(d1)

                var temT = 540 - t
                val temhours = temT / 60
                val temminutes = temT % 60
                timeRemf9TextView.text = temhours.toString() + "hr " + temminutes.toString() + "min"


                var tempT = 480 - t
                val temphours = tempT/ 60
                val tempminutes = tempT % 60
                timeRemf8TextView.text = temphours.toString() + "hr " + tempminutes.toString() + "min"


                //val r = Integer.parseInt(t.toString())
                Toast.makeText(this@MainActivity, minutes.toString() , Toast.LENGTH_SHORT).show()


            } catch (e: Exception) {
                e.printStackTrace()
            }








        }
    }
}