package ru.skillbranch.devintensive.extensions
import org.jsoup.Jsoup

fun String.truncate(count:Int = 16):String {
    return when(this.trim().length <= count) {
        true -> this.trim()
        false -> this.substring(0, count).trim() + "..."
    }
}

fun String.stripHtml(): String {

    return Jsoup.parse(this).text()
}
