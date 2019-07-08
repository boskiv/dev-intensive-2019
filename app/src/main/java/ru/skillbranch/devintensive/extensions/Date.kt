package ru.skillbranch.devintensive.extensions

import java.lang.Math.*
import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

enum class TimeUnits{
    SECOND,
    MINUTE,
    HOUR,
    DAY

}

fun Date.format(pattern: String="HH:mm:ss dd.MM.yy"):String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value:Int, units:TimeUnits = TimeUnits.SECOND) : Date {
    var time = this.time

    time += when(units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(date : Date = Date()): String {
//    0с - 1с "только что"
//
//    1с - 45с "несколько секунд назад"
//
//    45с - 75с "минуту назад"
//
//    75с - 45мин "N минут назад"
//
//    45мин - 75мин "час назад"
//
//    75мин 22ч "N часов назад"
//
//    22ч - 26ч "день назад"
//
//    26ч - 360д "N дней назад"
//
//    >360д "более года назад"

    val diff = Date().time - date.time
    return when(diff) {
        in 0 .. 1 * SECOND -> "только что"
        in 1 * SECOND .. 45 * SECOND -> "несколько секунд назад"
        in 45 * SECOND .. 75 * SECOND -> "минуту назад"
        in 75 * SECOND .. 45 * MINUTE -> "${abs(diff/ MINUTE)} ${humMinutes(diff)} назад"
        in 45* MINUTE .. 75 * MINUTE -> "час назад"
        in 75 * MINUTE .. 22* HOUR -> "${abs(diff/ HOUR)} ${humHours(diff)} назад"
        in 22 * HOUR .. 26 * HOUR -> "день назад"
        in 26 * HOUR .. 360 * DAY -> "${abs(diff/ DAY)} дней назад"
        in 360 * DAY .. 99999 * DAY -> "более года назад"
        else -> "ошибка $diff"

    }
}

fun humHours(diff: Long): String {
    val hour = abs(diff/ HOUR)
    return when(hour) {
        1L -> "час"
        2L,3L,4L,22L,23L,24L -> "часа"
        else -> "часов"
    }
}

fun humMinutes(diff: Long): String {
    val minutes = abs(diff/ MINUTE)
    val remainder = minutes.rem(10)
    return when(remainder) {
        1L -> "минуту"
        2L,3L,4L  -> "минуты"
        else -> "минут"
    }
}

