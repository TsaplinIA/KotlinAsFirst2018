@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import lesson3.task1.isPrime
import kotlin.math.sqrt
val GLOBAL_ALPHA = listOf("", "десять", "двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят", "семьдесят", "восемьдесят",
        "девяносто", "один", "две", "три", "четыр", "пят", "шест", "сем", "восем", "девят", "надцать")
val GLOBAL_BETA = listOf("сто", "двести", "триста", "четыреста", "пятьсот", "шестьсот", "семьсот", "восемьсот", "девятьсот")
val GLOBAL_PHI = listOf("", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять")

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
        when {
            y < 0 -> listOf()
            y == 0.0 -> listOf(0.0)
            else -> {
                val root = sqrt(y)
                // Результат!
                listOf(-root, root)
            }
        }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {

    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double = sqrt(v.map { it * it }.sum())


/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double = if (list.isEmpty()) 0.0 else list.sum() / list.size
fun meanMute(list: MutableList<Double>): Double = if (list.isEmpty()) 0.0 else list.sum() / list.size

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    val meanNow = mean(list)
    for (i in 0 until list.size) {
        list[i] -= meanNow
    }
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.0.
 */
fun times(a: List<Double>, b: List<Double>): Double {
    var res = 0.0
    for (i in 0 until a.size) res += a[i] * b[i]
    return res
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0.0 при любом x.
 */
fun polynom(p: List<Double>, x: Double): Double {
    var res = 0.0
    var multi = 1.0
    for (i in 0 until p.size) {
        res += p[i] * multi
        multi *= x
    }
    return res
}
/* = p.fold(0.0) { previousResult, element ->
    previousResult + element * pow(x, p.indexOf(element).toDouble())*/
/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Double>): MutableList<Double> {
    for (i in 1 until list.size) {
        list[i] += list[i - 1]
    }
    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    var n2 = n
    val beta = mutableListOf<Int>()
    while (n2 != 1) {
        var numNow = 2
        while (n2 % numNow != 0) numNow++
        beta.add(numNow)
        n2 /= numNow
    }
    return beta.toList()
}
//fun factorize(n: Int): List<Int> {
//    var resList = listOf<Int>()
//    var n2 = n
//    while (n2 != 1) {
//        var numNow = 2
//        while (!(isPrime(numNow) && n2 % numNow == 0)) numNow++
//        resList += listOf(numNow)
//        n2 /= numNow
//    }
//    return resList
//}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String = factorize(n).joinToString(separator = "*")

/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    var n2 = n
    val resList = mutableListOf<Int>()
    do {
        resList.add(0, n2 % base)
        n2 /= base
    } while (n2 != 0)
    return resList
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 */
fun convertToString(n: Int, base: Int): String = convert(n, base).joinToString(
        separator = "",
        transform = { if (it > 9) ('a' + it - 10).toString() else "$it" }
)

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var sum = 0
    var multi = powIntNaturalBase(base, digits.size - 1)
    for (i in 0 until digits.size) {
        sum += digits[i] * multi
        multi /= base
    }
    return sum

}

fun powIntNaturalBase(a: Int, b: Int): Int {
    if (b < 0) return 0
    var res = 1
    repeat(b) { res *= a }
    return res
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 */
fun decimalFromString(str: String, base: Int): Int {
    val beta = str.toList().map { digitFromLetter(it) }
    return decimal(beta, base)
}

fun digitFromLetter(letter: Char): Int = if (letter < 'a') letter - '0' else letter - 'a' + 10

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String {
    val alpha = listOf("I", "V", "X", "L", "C", "D", "M", "", "")
    val k = Array(4) { 0 }
    var n2 = n % 1000
    var res = ""
    var temp1 = 0
    while (n2 != 0) {
        val temp2 = n2 % 10
        for (i in 0 until k.size) k[i] = 0
        when (temp2) {
            0 -> k[0] = 0
            in 1..3 -> k[0] = temp2
            4 -> {
                k[0] = 1
                k[1] = 1
            }
            in 5..8 -> {
                k[1] = 1
                k[2] = temp2 - 5
            }
            else -> {
                k[2] = 1
                k[3] = 1
            }
        }
        repeat(k[3]) { res = alpha[temp1 + 2] + res }
        repeat(k[2]) { res = alpha[temp1] + res }
        repeat(k[1]) { res = alpha[temp1 + 1] + res }
        repeat(k[0]) { res = alpha[temp1] + res }
        n2 /= 10
        temp1 += 2
    }
    val n3 = n / 1000
    repeat(n3) { res = alpha[6] + res }
    return res
}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russian(n: Int): String {
    val gamma = listOf("тысяча", "тысячи", "тысяч")
    var res = ""
    res += threeDigitInStr(n / 1000)
    if (n / 1000 > 0) {
        if ((n / 1000) % 10 > 0) {
            res = when (res.last()) {
                'н' -> res.substring(0, res.length - 2) + "на"
                'а' -> res.substring(0, res.length - 1) + "е"
                else -> res
            }
        }
        val temp1 = when {
            res.last() == 'ь' || (n / 1000) % 10 == 0 -> gamma[2]
            res.last() == 'а' -> gamma[0]
            else -> gamma[1]
        }
        res += " $temp1"
    }
    if (n % 1000 != 0) {
        res += " " + threeDigitInStr(n % 1000)
    }
    return res.trim()
}

fun threeDigitInStr(n: Int): String {
    var res = ""
    val temp1 = n / 100
    val temp2 = n % 100
    if (temp1 > 0) res += GLOBAL_BETA[temp1 - 1] + " "
    res += when (temp2) {
        in 11..19 -> GLOBAL_ALPHA[temp2 % 10 + 9] + GLOBAL_ALPHA[19] + " "
        in 1..9 -> GLOBAL_PHI[temp2 % 10] + " "
        else -> GLOBAL_ALPHA[temp2 / 10] + " " + GLOBAL_PHI[temp2 % 10] + " "
    }
    return res.trim()
}