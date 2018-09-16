@file:Suppress("UNUSED_PARAMETER")
package lesson3.task1

import java.lang.Math.pow
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}



/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
        when {
            n == m -> 1
            n < 10 -> 0
            else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
        }

/**
 * Тривиальная
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int {
    var count = 0
    var n2: Int = n
    if (n == 0) count++
    while (n2 != 0) {
        count++
        n2 /= 10
    }
    return count
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    val arr = arrayOf(1, 1)
    var temp = 0
    if (n == 1 || n == 2) return 1
    for (i in 3..n) {
        temp = arr[0] + arr[1]
        arr[0] = arr[1]
        arr[1] = temp
    }
    return temp
}

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    var m2: Int = m
    var n2: Int = n
    var res = 1
    while (n2 != 1) {
        val temp: Int = minDivisor(n2)
        if (n2 % temp == 0) n2 /= temp
        if (m2 % temp == 0) m2 /= temp
        res *= temp
    }
    while(m2 != 1) {
        val temp: Int = minDivisor(m2)
        if (n2 % temp == 0) n2 /= temp
        if (m2 % temp == 0) m2 /= temp
        res *= temp
    }
    return res
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    if (!isPrime(n)) {
        for (i in 1..n / 2) {
            if (isPrime(i) && n % i == 0) return i
        }
    }
    return n
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    if (isPrime(n)) return 1
    for (i in n / 2 + 1 downTo 2) {
        if (n % i == 0) return i
    }
    return 1
}

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean {

    if (isPrime(m) || isPrime(n)) {
        return !((m % n == 0 || n % m == 0) && n != 1 && m != 1)
    }
    for (i in 2..minOf(n, m) / 2) {
        if (m % i == 0 && n % i == 0) return false
    }
    return true
}

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean {
    for (k in sqrt(m.toDouble()).toInt()..sqrt(n.toDouble()).toInt()) {
        if (k * k >= m && k * k <= n) return true
    }
    return false
}

/**
 * Средняя
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
    var countSteps = 0
    var x2: Int = x
    while (x2 != 1) {
        countSteps++
        if (x2 % 2 == 0) x2 /= 2 else x2 = x2 * 3 + 1
    }
    return countSteps
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun sin(x: Double, eps: Double): Double {
    var temp: Int = 1
    val x2: Double = x % (2 * PI)
    var res: Double = 0.0
    var k: Int = 1
    while (abs(pow(x2, temp.toDouble()) / factorial(temp)) >= eps) {
        res += pow(x2, temp.toDouble()) / factorial(temp) * k
        temp += 2
        k *= -1
    }
    return res
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun cos(x: Double, eps: Double): Double {
    val x2 = x % (2 * PI)
    var res = 1.0
    var coef = 2
    var k: Int = -1
    val xNow: Double = x2
    val temp: Int = abs((x2 / PI * 2).toInt())
    if (abs((x2 / PI * 2)) - temp < eps) {
        return when {
            temp % 4 == 0 -> 1.0
            temp % 4 == 2 -> -1.0
            else -> 0.0
        }
    }
    while (pow(xNow, coef.toDouble()) / factorial(coef) >= eps) {
        res += pow(xNow, coef.toDouble()) / factorial(coef) * k
        coef += 2
        k *= -1
    }
    res += (pow(xNow, coef.toDouble()) / factorial(coef) * k)
    return res
}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int {
    val myNumber: MutableList<Int> = mutableListOf(0)
    var n2: Int = n
    val digitN: Int = digitNumber(n)
    if (n2 == 0) return 0
    while (n2 != 0) {
        myNumber.add(n2 % 10)
        n2 /= 10
    }
    for (i in digitN downTo 1) {
        n2 += myNumber[i] * pow(10.0, (digitN - i).toDouble()).toInt()
    }
    return n2
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean {
    val myNumber: MutableList<Int> = mutableListOf(0)
    var n2: Int = n
    val digitN: Int = digitNumber(n)
    var temp = 0
    if (digitN == 1) return true
    while (n2 != 0) {
        myNumber.add(n2 % 10)
        n2 /= 10
    }
    for (i in 1..digitN / 2) {
        if(myNumber[i] != myNumber[digitN - i + 1]) temp++
    }
    return temp == 0
}

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean {
    val myNumber: MutableList<Int> = mutableListOf(0)
    var n2: Int = n
    val digitN: Int = digitNumber(n)
    var temp = 0
    if (digitN == 1) return false
    while (n2 != 0) {
        myNumber.add(n2 % 10)
        n2 /= 10
    }
    for (i in 2..digitN) {
        if (myNumber[i] != myNumber[i - 1]) temp++
    }
    return temp != 0
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int {
    var temp1: Int = n
    var temp2: Int = 0
    var temp3: Int = 0
    var numberNow: Int = 0
    while (temp1 != 0) {
        if (temp3 == 0) {
            temp2++
            temp3 = temp2 * temp2
        }
        if (temp1 > digitNumber(temp3)){
            temp1 -= digitNumber(temp3)
            temp3 = 0
        } else {
            numberNow = (temp3 / pow(10.0, (digitNumber(temp3) - temp1).toDouble())).toInt() % 10
            temp1 = 0
        }
    }
    return numberNow
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int {
    var temp1: Int = n
    var temp2: Int = 0
    var temp3: Int = 0
    var numberNow: Int = 0
    while (temp1 != 0) {
        if (temp3 == 0) {
            temp2++
            temp3 = fib(temp2)
        }
        if (temp1 > digitNumber(temp3)){
            temp1 -= digitNumber(temp3)
            temp3 = 0
        } else {
            numberNow = (temp3 / pow(10.0, (digitNumber(temp3) - temp1).toDouble())).toInt() % 10
            temp1 = 0
        }
    }
    return numberNow
}
