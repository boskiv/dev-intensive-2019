package ru.skillbranch.devintensive.extensions

fun TimeUnits.plural(i: Int): String? {
    return when(this) {
        TimeUnits.SECOND -> humSeconds(i)
        TimeUnits.MINUTE -> humMinutes(i)
        TimeUnits.HOUR -> humHours(i)
        TimeUnits.DAY -> humDays(i)
    }

}

fun humSeconds(value: Int): String {
    return when( value%100/10) {
        1 -> "$value секунд"
        else -> when(value.rem(10)) {
            1 -> "$value секунду"
            2,3,4  -> "$value секунды"
            else -> "$value секунд"
        }
    }
}

fun humMinutes(value: Int): String {
    return when( value%100/10) {
        1 -> "$value минут"
        else -> when(value.rem(10)) {
            1 -> "$value минуту"
            2,3,4  -> "$value минуты"
            else -> "$value минут"
        }
    }
}

fun humHours(value: Int): String {
    return when( value%100/10) {
        1 -> "$value часов"
        else -> when(value.rem(10)) {
            1 -> "$value час"
            2,3,4  -> "$value часа"
            else -> "$value часов"
        }
    }
}

fun humDays(value: Int): String {
    return when( value%100/10) {
        1 -> "$value дней"
        else -> when(value.rem(10)) {
            1 -> "$value день"
            2,3,4  -> "$value дня"
            else -> "$value дней"
        }
    }
}