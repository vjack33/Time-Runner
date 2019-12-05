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

        val submitBtn = findViewById<Button>(R.id.buttonSubmit)
        val timeCompletedTextView = findViewById<TextView>(R.id.textViewTimeCompleted)
        val timeRemf8TextView = findViewById<TextView>(R.id.textViewTimeRemf8)
        val timeRemf9TextView = findViewById<TextView>(R.id.textViewTimeRemf9)

        val timeCompletedHomeTextView = findViewById<TextView>(R.id.textViewHomeTimeCompleted)
        val timeCompletedHomeMinTextView = findViewById<TextView>(R.id.textViewHomeMinTimeCompleted)

        val timeRemf8HomeTextView = findViewById<TextView>(R.id.textViewHomeTimeRemf8)
        val timeRemf8HomeMinTextView = findViewById<TextView>(R.id.textViewHomeMinTimeRemf8)

        val timeRemf9HomeTextView = findViewById<TextView>(R.id.textViewHomeTimeRemf9)
        val timeRemf9HomeMinTextView = findViewById<TextView>(R.id.textViewHomeMinTimeRemf9)

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

        submitBtn.setOnClickListener {

            val format =
                SimpleDateFormat("HH:mm")
            val localTime = LocalTime()
            var d1: Date? = null
            var d2: Date? = null

            var d3: Date? = null
            var d4: Date? = null

            Toast.makeText(this@MainActivity, localTime.toString() , Toast.LENGTH_SHORT).show()
            try {
                d1 = format.parse(textViewInTime.text.toString())
                d2 = format.parse(localTime.toString())

                d3 = format.parse(localTime.plusHours(9).toString())
                d4 = format.parse(localTime.plusHours(8).toString())

                val dt1 = DateTime(d1)
                val dt2 = DateTime(d2)
                //timeCompletedTextView.text = Minutes.minutesBetween(dt1, dt2).getMinutes() .toString()
                val t = Minutes.minutesBetween(dt1, dt2).getMinutes()
                val hours = t / 60 //since both are ints, you get an int
                val minutes = t % 60
                timeCompletedTextView.text = hours.toString() + "hr " + minutes.toString() + "min"
                timeCompletedHomeTextView.text =  SimpleDateFormat("HH").format(d2)
                timeCompletedHomeMinTextView.text = SimpleDateFormat("mm").format(d2)



                var temT = 540 - t
                val temhours = temT / 60
                val temminutes = temT % 60
                timeRemf9TextView.text = temhours.toString() + "hr " + temminutes.toString() + "min"


                var tempT = 480 - t
                val temphours = tempT/ 60
                val tempminutes = tempT % 60
                timeRemf8TextView.text = temphours.toString() + "hr " + tempminutes.toString() + "min"


                //val d3 = LocalTime().plusHours(8)
                //val d4 = LocalTime().plusHours(9)

                Toast.makeText(this@MainActivity, d3.toString() , Toast.LENGTH_SHORT).show()
                Toast.makeText(this@MainActivity, SimpleDateFormat("HH:mm").format(d3).toString() , Toast.LENGTH_SHORT).show()

                timeRemf9HomeTextView.text =  SimpleDateFormat("HH").format(d3)
                timeRemf9HomeMinTextView.text = SimpleDateFormat("mm").format(d3)

                timeRemf8HomeTextView.text = SimpleDateFormat("HH").format(d4)
                timeRemf8HomeMinTextView.text = SimpleDateFormat("mm").format(d4)

                //val r = Integer.parseInt(t.toString())
                Toast.makeText(this@MainActivity, minutes.toString() , Toast.LENGTH_SHORT).show()


            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}