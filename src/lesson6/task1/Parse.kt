@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson2.task2.daysInMonth

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main(args: Array<String>) {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


val MONTHS_NAME = listOf("января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября",
        "октября", "ноября", "декабря")

/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */

fun dateStrToDigit(str: String): String {
    val temp = str.split(" ")
    val month: Int
    try {
        if (temp.size != 3) throw Exception()
        month = MONTHS_NAME.indexOf(temp[1]) + 1
        if (month !in 1..12) throw Exception()
    } catch (e: Exception) {
        return ""
    }
    var day = 40
    var year: Int = -2
    try {
        if (Regex("""[0-3]?\d""").matches(temp[0])) day = Regex("""[1-9]\d*""").find(temp[0])!!.value.toInt()
        if (temp.size >= 3) year = temp[2].toInt()
    } catch (e: NumberFormatException) {
        return ""
    }
    return if (day in 1..daysInMonth(month, year)) String.format("%02d.%02d.%d", day, month, year) else ""
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    val temp: List<Int>
    val month: String
    try {
        temp = digital.split(".").map { it.toInt() }
    } catch (e: NumberFormatException) {
        return ""
    }
    try {
        if (temp.size != 3) throw Exception()
        if (temp[1] !in 1..12) throw Exception()
        if (temp[0] !in 1..daysInMonth(temp[1], temp[2])) throw Exception()
        month = MONTHS_NAME[temp[1] - 1]
    } catch (e: Exception) {
        return ""
    }
    return String.format("%d %s %d", temp[0], month, temp[2])
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -98 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */
fun flattenPhoneNumber(phone: String): String = if (
        Regex("""^\+?([\d\-()\s])*""").matches(phone) &&
        Regex("""([^()]*\([^()]*\)[^()]*)|([^()]*)""").matches(phone))
    Regex("""[^\d+]""").replace(phone, "") else ""

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    val shortJumps = jumps.replace(Regex("""%|-"""), "").replace(Regex("""\s+"""), " ")
    return allJumps(shortJumps)
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    val shortJumps = jumps
            .replace(Regex("""\d+\s[%-]+\s"""), "")
            .replace(Regex("""[+%-]+"""), "")
            .replace(Regex("""\s+"""), " ")
    return allJumps(shortJumps)
}


fun allJumps(jumps: String): Int {
    var res = -1
    if (Regex("""[^\d\s]""").containsMatchIn(jumps)) return res
    Regex("""\s+""").split(jumps).filter { Regex("""\d+""").matches(it) }.forEach { res = maxOf(it.toInt(), res) }
    return res
}
/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    var plus = 0
    var minus = 0
    if (Regex("""((([1-9]+\d+)|\d)\s(\+|\-)\s)*(([1-9]+\d+)|\d)""").matches(expression)) {
        Regex("""\d+""").findAll(expression).forEach { plus += it.value.toInt() }
        Regex("""\-\s\d+""").findAll(expression).forEach { minus += Regex("""\d+""").find(it.value)!!.value.toInt() }
    } else throw IllegalArgumentException()
    return plus - 2 * minus
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    var strNow = str
    var charCount = 0
    while (strNow.contains(Regex("""[^\s]+\s[^\s]+\s?"""))) {
        val pairWords = Regex("""[^\s]+\s[^\s]+\s?""").find(strNow)!!.value
        val listWords = pairWords.split(" ")
        if (listWords[0].toLowerCase() == listWords[1].toLowerCase()) return charCount
        else {
            val charCountInWord = Regex("""[^\s]+\s""").find(pairWords)!!.value.length
            charCount += charCountInWord
            strNow = strNow.substring(charCountInWord)
        }
    }
    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    var str = "" to (-1 to -1)
    if (Regex("""([^\s]+\s(\d+\.\d+|\d+);\s)*[^\s]+\s(\d+\.\d+|\d+)""").matches(description)) {
        val strList = description.split("; ").map { "$it.0" }
        val pairList = strList.map {
            val tally1 = Regex("""\d+\.""").find(it)!!.value
            val tally2 = Regex("""\.\d+""").find(it)!!.value
            val temp1 = tally1.substring(0, tally1.length - 1).toInt()
            val temp2 = tally2.substring(1, tally2.length).toInt()
            Regex("""[^\s]+""").find(it)!!.value to (temp1 to temp2)
        }
        for ((name, pr) in pairList)
            if (pr.first > str.second.first || (pr.first == str.second.first && pr.second > str.second.second))
                str = name to pr
    }
    return str.first
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
val ROMAN_NO = mapOf('M' to 1, 'D' to 2, 'C' to 3, 'L' to 4, 'X' to 5, 'V' to 6, 'I' to 7, 'S' to 8)
val ROMAN_VALUE = mapOf('M' to 1000, 'D' to 500, 'C' to 100, 'L' to 50, 'X' to 10, 'V' to 5, 'I' to 1)
fun fromRoman(roman: String): Int {
    var res = 0
    val str = roman + 'S'
    if (roman.isEmpty()) return -1
    if (Regex("""M*(CM)?(D|CD)?C{0,3}(XC)?(L|XL)?X{0,3}(IX)?(V|(IV))?I{0,3}""").matches(roman)) {
        for (i in str.length - 2 downTo 0) {
            var k = i + 1
            while (ROMAN_NO[str[i]] == ROMAN_NO[str[k]]) k++
            if (ROMAN_NO[str[i]]!!.toInt() < ROMAN_NO[str[k]]!!.toInt()) res += ROMAN_VALUE[str[i]]!!
            else res -= ROMAN_VALUE[str[i]]!!
        }
    } else return -1
    return res
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    var hand = cells / 2
    var power = limit
    var order = 0
    var p = 0
    val conveyer = Array(cells) { 0 }.toMutableList()

    fun stay() {
        order++
        power--
    }

    if (!Regex("""[+<>\[\]\s-]*""").matches(commands)) throw IllegalArgumentException()
    var controler = 0
    Regex("""[\[\]]""").findAll(commands).forEach {
        if (it.value == "[") controler++ else controler--
        if (controler < 0) throw IllegalArgumentException()
    }
    if (controler != 0) throw IllegalArgumentException()
    while (order in 0 until commands.length && power > 0) {
        if (hand !in 0 until cells) throw IllegalStateException()
        val comLit = commands[order]
        when (comLit) {
            '>' -> {
                hand++
                stay()
            }
            '<' -> {
                hand--
                stay()
            }
            '+' -> {
                conveyer[hand]++
                stay()
            }
            '-' -> {
                conveyer[hand]--
                stay()
            }
            '[' -> {
                if (conveyer[hand] == 0) {
                    p--
                    do {
                        order++
                        when (commands[order]) {
                            '[' -> p--
                            ']' -> p++
                        }
                    } while (p != 0)
                }
                stay()
            }
            ']' -> {
                if (conveyer[hand] != 0) {
                    p++
                    do {
                        order--
                        when (commands[order]) {
                            '[' -> p--
                            ']' -> p++
                        }
                    } while (p != 0)
                }
                stay()
            }
            ' ' -> {
                order++
                power--
            }
        }
    }
    return conveyer
}


//    var hand = cells / 2
//    var power = limit
//    var order = 0
//    var p = 0
//    val conveyer = Array(cells) { 0 }.toMutableList()
//
//    fun stay() {
//        order++
//        power--
//    }
//
//    fun toR() {
//        hand++
//        stay()
//    }
//
//    fun toL() {
//        hand--
//        stay()
//    }
//
//    fun toP() {
//        conveyer[hand]++
//        stay()
//    }
//
//    fun toM() {
//        conveyer[hand]--
//        stay()
//    }
//
//    fun goR() {
//        if (conveyer[hand] == 0) {
//            p--
//            do {
//                order++
//                when (commands[order]) {
//                    '[' -> p--
//                    ']' -> p++
//                }
//            } while (p != 0)
//        }
//        stay()
//    }
//
//    fun goL() {
//        if (conveyer[hand] != 0) {
//            p++
//            do {
//                order--
//                when (commands[order]) {
//                    '[' -> p--
//                    ']' -> p++
//                }
//            } while (p != 0)
//        }
//        stay()
//    }
//
//
//    if (!Regex("""[+<>\[\]\s-]+""").matches(commands)) throw IllegalArgumentException()
//    var controler = 0
//    Regex("""[\[\]]""").findAll(commands).forEach {
//        if (it.value == "[") controler++ else controler--
//        if (controler < 0) throw IllegalArgumentException()
//    }
//    if (controler != 0) throw IllegalArgumentException()
//
//    while (order in 0 until commands.length && power > 0) {
//        if (hand !in 0 until cells) throw IllegalStateException()
//        val comLit = commands[order]
//        when (comLit) {
//            '>' -> toR()
//            '<' -> toL()
//            '+' -> toP()
//            '-' -> toM()
//            '[' -> goR()
//            ']' -> goL()
//            ' ' -> stay()
//        }
//    }
//    return conveyer