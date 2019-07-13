package ru.skillbranch.devintensive.extensions

fun String.truncate(count:Int = 16):String {
    return when(this.trim().length <= count) {
        true -> this.trim()
        false -> this.substring(0, count).trim() + "..."
    }
}

fun String.stripHtml(): String {
    return this.replace(Regex("\\<.*?>"), "").replace(Regex("&.*?;"),"").replace(Regex("\\s+"), " ")




}
