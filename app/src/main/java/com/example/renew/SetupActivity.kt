package com.example.renew

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.renew.AppConfig.AVG_HOURS_PER_DAY
import kotlinx.android.synthetic.main.activity_setup.*

class SetupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)



        buttonSetupSave.setOnClickListener {
            AVG_HOURS_PER_DAY = editTextSetupWeekHours.text.toString().toInt()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
