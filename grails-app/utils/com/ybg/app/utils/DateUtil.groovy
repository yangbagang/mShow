package com.ybg.app.utils

import java.text.SimpleDateFormat

/**
 * Created by yangbagang on 2016/10/25.
 */
class DateUtil {

    static sdf_short = new SimpleDateFormat("yyyy-MM-dd")
    static sdf_full = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    static getDefaultBirthday() {
        sdf_short.parse("1990-01-01")
    }

    static getTimeString(Date date) {
        sdf_full.format(date)
    }
}
