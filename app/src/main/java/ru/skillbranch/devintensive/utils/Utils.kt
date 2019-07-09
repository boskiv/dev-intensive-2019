package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName:String?):Pair<String?,String?>{


        val fullNameTrimmed = fullName?.trim()
        var parts : List<String>? = fullNameTrimmed?.split(" ")

        when(fullNameTrimmed?.trim()) {
            ""," " -> parts = null
        }

        var firstName = parts?.getOrNull(0)?.trim()
        var lastName = parts?.getOrNull(1)?.trim()

        when(firstName?.trim()) {
            ""," " -> firstName = null
        }

        when(lastName?.trim()) {
            ""," " ->  lastName = null
        }

        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val lettersMap: HashMap<Char, String> = hashMapOf(
            'а' to "a", 'б' to "b", 'в' to "v", 'г' to "g", 'д' to "d", 'е' to "e", 'ё' to "e", 'ж' to "zh", 'з' to "z",
            'и' to "i", 'й' to "i", 'к' to "k", 'л' to "l", 'м' to "m", 'н' to "n", 'о' to "o", 'п' to "p", 'р' to "r",
            'с' to "s", 'т' to "t", 'у' to "u", 'ф' to "f", 'х' to "h", 'ц' to "c", 'ч' to "ch", 'ш' to "sh", 'щ' to "sh'",
            'ъ' to "", 'ы' to "i", 'ь' to "", 'э' to "e", 'ю' to "yu", 'я' to "ya",
            'А' to "A", 'Б' to "B", 'В' to "V", 'Г' to "G", 'Д' to "D", 'Е' to "E", 'Ё' to "E", 'Ж' to "Zh", 'З' to "Z",
            'И' to "I", 'Й' to "I", 'К' to "K", 'Л' to "L", 'М' to "M", 'Н' to "N", 'О' to "O", 'П' to "P", 'Р' to "R",
            'С' to "S", 'Т' to "T", 'У' to "U", 'Ф' to "F", 'Х' to "H", 'Ц' to "C", 'Ч' to "Ch", 'Ш' to "Sh", 'Щ' to "Sh'",
            'Ъ' to "", 'Ы' to "I", 'Ь' to "", 'Э' to "E", 'Ю' to "Yu", 'Я' to "Ya"
        )

        val parts = payload.split(" ")
        val response = mutableListOf<String>()
        var tmp: String

        for (part in parts) {
            tmp = ""
            part.forEach {

                    c ->
                run {
                    if (c in lettersMap) {
                        tmp += lettersMap[c]
                    } else {
                        tmp += c
                    }
                }
            }
            response += tmp
        }

        return response.joinToString(separator = divider)


    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        var firstNameNull = false
        var lastNameNull = false
        when(firstName) {
            "", " ", null -> firstNameNull = true
        }

        when(lastName) {
            "", " ", null -> lastNameNull = true
        }

        return when {
            firstNameNull and lastNameNull -> null
            firstNameNull and !lastNameNull -> "${lastName?.trim()?.first()?.toUpperCase()}"
            !firstNameNull and lastNameNull -> "${firstName?.trim()?.first()?.toUpperCase()}"
            else -> "${firstName?.trim()?.first()?.toUpperCase()}${lastName?.trim()?.first()?.toUpperCase()}"
        }
    }
}