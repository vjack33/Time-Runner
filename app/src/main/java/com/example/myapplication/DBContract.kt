package com.example.myapplication

import android.provider.BaseColumns

object DBContract {

    /* Inner class that defines the table contents */
    class UserEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "users"
            val COLUMN_DATA_DATE = "dataDate"
            val COLUMN_DATA_IN_TIME = "dataInTime"
            val COLUMN_DATA_OUT_TIME = "dataOutTime"
            val COLUMN_DATA_TIME_SPENT = "dataTimeSpent"
            val COLUMN_DATA_REG = "dataReg"
        }
    }
}