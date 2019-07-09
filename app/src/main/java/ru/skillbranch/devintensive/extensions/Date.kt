package ru.skillbranch.devintensive.extensions

import java.lang.Math.*
import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    fun plural(i: Int): String? {
        return when(this) {
            SECOND -> humSeconds(i)
            MINUTE -> humMinutes(i)
            HOUR -> humHours(i)
            DAY -> humDays(i)
        }
    }

    private fun humSeconds(value: Int): String {
        return when( value%100/10) {
            1 -> "$value секунд"
            else -> when(value.rem(10)) {
                1 -> "$value секунду"
                2,3,4  -> "$value секунды"
                else -> "$value секунд"
            }
        }
    }

    private fun humMinutes(value: Int): String {
        return when( value%100/10) {
            1 -> "$value минут"
            else -> when(value.rem(10)) {
                1 -> "$value минуту"
                2,3,4  -> "$value минуты"
                else -> "$value минут"
            }
        }
    }

    private fun humHours(value: Int): String {
        return when( value%100/10) {
            1 -> "$value часов"
            else -> when(value.rem(10)) {
                1 -> "$value час"
                2,3,4  -> "$value часа"
                else -> "$value часов"
            }
        }
    }

    private fun humDays(value: Int): String {
        return when( value%100/10) {
            1 -> "$value дней"
            else -> when(value.rem(10)) {
                1 -> "$value день"
                2,3,4  -> "$value дня"
                else -> "$value дней"
            }
        }
    }
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
    val messageDate = this
    val diffPair = when {
        messageDate.time > date.time -> Pair(messageDate.time - date.time, true)
        messageDate.time < date.time -> Pair(date.time - messageDate.time, false)
        else -> Pair(0L,false)
    }

    val (diff, future) = diffPair
    return when {
            diff <= 1 * SECOND -> "только что"
            diff > 1 * SECOND && diff <= 45 * SECOND -> if (future) "через несколько секунд" else "несколько секунд назад"
            diff > 45 * SECOND && diff <= 75 * SECOND -> if (future) "через минуту" else "минуту назад"
            diff > 75 * SECOND && diff <= 45 * MINUTE -> if (future) "через ${TimeUnits.MINUTE.plural(abs(diff/ MINUTE).toInt())}" else "${TimeUnits.MINUTE.plural(abs(diff/ MINUTE).toInt())} назад"
            diff > 45* MINUTE && diff <= 75 * MINUTE -> if (future) "через час" else "час назад"
            diff > 75 * MINUTE && diff <= 22* HOUR -> if (future) "через ${TimeUnits.HOUR.plural(abs(diff/ HOUR).toInt())}" else "${TimeUnits.HOUR.plural(abs(diff/ HOUR).toInt())} назад"
            diff > 22 * HOUR && diff <= 26 * HOUR -> if (future) "через день" else "день назад"
            diff > 26 * HOUR && diff <= 360 * DAY -> if (future) "через ${TimeUnits.DAY.plural(abs(diff/ DAY).toInt())}" else "${TimeUnits.DAY.plural(abs(diff/ DAY).toInt())} назад"
            diff > 360 * DAY && diff <= Long.MAX_VALUE -> if (future) "более чем через год" else "более года назад"
            else -> "ошибка $diff"
    }
}



