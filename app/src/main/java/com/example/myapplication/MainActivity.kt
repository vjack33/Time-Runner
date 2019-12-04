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

        val mPickTimeBtn = findViewById<Button>(R.id.pickTimeBtn)
        val textView = findViewById<TextView>(R.id.timeTv)
        val mPickTimeBtn2 = findViewById<Button>(R.id.pickTimeBtn2)
        val textView2 = findViewById<TextView>(R.id.timeTv2)
        val mButton = findViewById<Button>(R.id.buttonTest)
        val mTextView = findViewById<TextView>(R.id.textViewTest)

        mPickTimeBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                textView.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }

        mPickTimeBtn2.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                textView2.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }

        mButton.setOnClickListener {

            val format =
                SimpleDateFormat("HH:mm")
            val localTime = LocalTime()
            var d1: Date? = null
            var d2: Date? = null
            Toast.makeText(this@MainActivity, localTime.toString() , Toast.LENGTH_SHORT).show()
            try {
                d1 = format.parse(textView.text.toString())
                d2 = format.parse(localTime.toString())
                Toast.makeText(this@MainActivity, d1.toString() , Toast.LENGTH_SHORT).show()
                val dt1 = DateTime(d1)
                val dt2 = DateTime(d2)
                mTextView.text = Days.daysBetween(dt1, dt2).getDays().toString()+ " " + Hours.hoursBetween(dt1, dt2).getHours().toString() + " " +  Minutes.minutesBetween(dt1, dt2).getMinutes() .toString()

            } catch (e: Exception) {
                e.printStackTrace()
            }








        }
    }
}