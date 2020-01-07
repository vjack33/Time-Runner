package com.example.renew

import java.time.MonthDay

object AppConfig {
    var URL_ALL = "http://192.168.43.117/android_login_api/"
    var URL_LOGIN = URL_ALL + "login.php"
    var URL_REGISTER = URL_ALL + "register.php"
    var URL_DATA = URL_ALL + "data.php"
    var URL_CHART_2 = URL_ALL + "charts/chart1.php"
    var URL_RELAY_STATUS = "http://192.168.43.117" + "/ledstatus"
    var URL_CHART_1 = URL_ALL + "charts/chart2.php"

    var MIN_HOURS_PER_DAY = 8*60
    var AVG_HOURS_PER_DAY = 9*60
    var MAX_HOURS_PER_DAY = (9*60)+30
    var SUNDAY = true
    var MONDAY = true
    var TUESDAY = true
    var WEDNESDAY = true
    var THURSDAY = true
    var FRIDAY = true
    var SATURDAY = true

}