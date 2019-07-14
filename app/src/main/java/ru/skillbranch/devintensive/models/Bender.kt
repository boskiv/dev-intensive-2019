package ru.skillbranch.devintensive.models

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME, var answerTry: AnswerTry = AnswerTry.FIRST) {

    fun askQuestion(): String = when(question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer:String): Pair<String, Triple<Int, Int, Int>> {
        val (isValid, message) = question.validate(answer)
        if (! isValid) {
            return message to status.color
        }
        if (question == Question.IDLE) {
            return question.question to status.color
        }

        if(question.answers.contains(answer)) {
            question = question.nextQuestion()
            return "Отлично - ты справился\n${question.question}" to status.color
        } else {
            status = status.nextStatus()
            answerTry = answerTry.nextTry()
            if (answerTry == AnswerTry.LAST) {
                question = Question.NAME
                status = Status.NORMAL
                answerTry = AnswerTry.FIRST
                return "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
            }
            return "Это неправильный ответ\n${question.question}" to status.color
        }
    }

    enum class AnswerTry {
        FIRST,
        SECOND,
        THIRD,
        FOUR,
        LAST;

        fun nextTry(): AnswerTry {
            return when(this) {
                FIRST -> SECOND
                SECOND -> THIRD
                THIRD -> FOUR
                FOUR -> LAST
                LAST -> FIRST
            }
        }
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)) ,
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0)) ;

        fun nextStatus(): Status {
            return when(this) {
                NORMAL -> WARNING
                WARNING -> DANGER
                DANGER -> CRITICAL
                CRITICAL -> CRITICAL
            }
        }
    }
    enum class Question(val question: String,
                        val answers: List<String>,
                        private val validationString: String,
                        private val validationMessage: String) {
        NAME("Как меня зовут?",
            listOf("Бендер", "Bender"),
            "^[A-Z]{1}.*" ,
            "Имя должно начинаться с заглавной буквы\n") {

            override fun nextQuestion(): Question = PROFESSION
        },
        PROFESSION("Назови мою профессию?",
            listOf("сгибальщик", "bender"),
            "^[a-z]{1}.*",
            "Профессия должна начинаться со строчной буквы\n") {
            override fun nextQuestion(): Question = MATERIAL
        },
        MATERIAL("Из чего я сделан?",
            listOf("металл", "дерево", "metal", "iron", "wood"),
            "\\D+",
            "Материал не должен содержать цифр\n") {
            override fun nextQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?",
            listOf("2993"),
            "\\d+",
            "Год моего рождения должен содержать только цифры\n") {
            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?",
            listOf("2716057"),
            "\\d{7}",
            "Серийный номер содержит только цифры, и их 7\n") {
            override fun nextQuestion(): Question = IDLE
        },
        IDLE("На этом все, вопросов больше нет",
            listOf(), "\\.*", "") {
            override fun nextQuestion(): Question = IDLE
        };

        abstract fun nextQuestion(): Question
        fun validate(answer: String): Pair<Boolean, String> {
            var message = "${this.validationMessage}${this.question}"
            val pattern = this.validationString.toRegex()
            val isValid = pattern.matches(answer)
            if (isValid) {
                message = ""
            }
            return isValid to message
        }
    }
}