package com.example.renew

import java.time.MonthDay

object AppConfig {

    var MIN_HOURS_PER_DAY = 8*60
    var AVG_HOURS_PER_DAY = 9*60
    var MAX_HOURS_PER_DAY = (9*60)+30

    var MINIMUM_TIME_RESTRICTION = false
    var LATE_TIME_RESTRICTION = false

    val WORKING_DAYS = setOf("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY")
    var WEEKDAYS = WORKING_DAYS.count()
    var MAX_WEEK_HOURS = AVG_HOURS_PER_DAY * WEEKDAYS

    var PUBLIC_HOLIDAY = setOf("2020-01-01","2020-03-10","2020-05-01","2020-10-02","2020-11-16")

    var TOAST_MINTIME_COMPLETED = "Minimum time completed"
    var TOAST_MINITIME_NOT_COMPLETED = "Minimum time not yet completed"

    var TOAST_SAMEDAY_LEAVE_REG = "You can not apply Leave and Regularisation on same day."
    var TOAST_TIME_SELECTION_ERROR = "Select time properly."
    var TOAST_MAXTIME = "Maximum allowed time is " + (MAX_HOURS_PER_DAY/60) + "Hr."
    var TOAST_MINTIME = "Please complete Minimum "+ (MIN_HOURS_PER_DAY/60) + " Hours."
    var TOAST_FUTURE = "Can't view future dates."

}