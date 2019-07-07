package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName:String?):Pair<String?,String?>{



        var parts : List<String>? = fullName?.split(" ")

        when(fullName) {
            ""," " -> parts = null
        }

        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)

        return firstName to lastName
    }

    fun translitiration(payload: String, divider: String = " "): String {
        val lettersMap: HashMap<Char, String> = hashMapOf(
            'а' to "a", 'б' to "b", 'в' to "v", 'г' to "g", 'д' to "d", 'е' to "e", 'ё' to "e", 'ж' to "zh", 'з' to "z",
            'и' to "i", 'й' to "i", 'к' to "k", 'л' to "l", 'м' to "m", 'н' to "n", 'о' to "o", 'п' to "p", 'р' to "r",
            'с' to "s", 'т' to "t", 'у' to "u", 'ф' to "f", 'х' to "h", 'ц' to "c", 'ч' to "ch", 'ш' to "sh", 'щ' to "sh'",
            'ъ' to "", 'ы' to "i", 'ь' to "", 'э' to "e", 'ю' to "yu", 'я' to "ya"
        )

        var firstName = ""
        var lastName = ""
        val (firstNameRaw, lastNameRaw) = payload.toLowerCase().split(" ")

        firstNameRaw.forEach {
            c -> firstName += lettersMap[c]
        }

        lastNameRaw.forEach {
                c -> lastName += lettersMap[c]
        }

        return firstName.capitalize() + divider + lastName.capitalize()
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
            firstNameNull and !lastNameNull -> "${lastName?.first()?.toUpperCase()}"
            !firstNameNull and lastNameNull -> "${firstName?.first()?.toUpperCase()}"
            else -> "${firstName?.first()?.toUpperCase()}${lastName?.first()?.toUpperCase()}"
        }
    }
}