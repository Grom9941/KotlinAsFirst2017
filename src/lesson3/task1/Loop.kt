
@file:Suppress("UNUSED_PARAMETER")
package lesson3.task1

import lesson1.task1.sqr
import java.lang.Math.*
import kotlin.system.exitProcess

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result *= i // Please do not fix in master
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
    for (m in 2..Math.sqrt(n.toDouble()).toInt()) {
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
    for (m in 2..n/2) {
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
 */
fun digitNumber(n: Int): Int {
    var n1=abs(n)
    if (n == 0) return 1
    var num = 0
    while (n1 > 0) {
        n1 /= 10
        num++
    }
    return num
}

    /**
     * Простая
     *
     * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
     * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
     */

fun fib(n: Int): Int {
        var fib = 1
        var fib1 = 1
        for (i in 1..n - 2) {
            if (i % 2 == 1) fib += fib1 else fib1 += fib
        }
        return if ((n - 2) % 2 == 1) fib else fib1
    }


/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    var m1 = m
    var n1 = n
    while (m1 != n1)
        if (n1 > m1)
            n1 -= m1
        else
            m1 -= n1
    return m * n / m1
}

/**
  * Простая
  *
  * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
  */

fun minDivisor(n: Int): Int {
    var div = 1
    for (i in 2..Math.ceil(Math.sqrt(n.toDouble())).toInt())
        if (n % i == 0) {
            div = i
            break
        }
    if (div == 1) div = n
    return div
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    var div = 1
    for (i in n / 2 downTo 1)
        if (n % i == 0) {
            div = i
            break
        }
    return div
}

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean {
    var point = 0
    if ((n == 1) || (m == 1)) return true
    for (i in 2..min(n, m) / 2)
        if ((n % i == 0) && (m % i == 0)) {
            point = 1
            break
        }
    if ((n % min(n, m) == 0) && (m % min(n, m) == 0)) point = 1
    return point != 1
}

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean {
    var s = round(sqrt(m.toDouble()))
    if (s * s < m) s++
    return s <= sqrt(n.toDouble())
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun sin(x: Double, eps: Double): Double {
    var eps1 = x % (2 * PI)
    var i = 1
    var sin = x % (2 * PI)
    while (abs(eps1) >= eps) {
        eps1 = -eps1 * (x % (2 * PI)) / ((i * 2 + 1) * (i * 2)).toDouble() * (x % (2 * PI))
        i += 1
        sin += eps1
    }
    return sin
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun cos(x: Double, eps: Double): Double {
    var eps1 = 1.0
    var i = 1
    var cos = 1.0
    while (abs(eps1) >= eps) {
        eps1 = -eps1 * (x % (2 * PI)) / ((i * 2 - 1) * (i * 2)).toDouble() * (x % (2 * PI))
        i += 1
        cos += eps1
    }
    return cos
}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 * Не использовать строки при решении задачи.
 */
fun revert(n: Int): Int {
    var leng = 0
    var n1 = n
    while (n1 >= 1) {
        leng = leng * 10 + (n1 % 10)
        n1 /= 10
    }
    return leng
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 */
fun isPalindrome(n: Int): Boolean {
    val n1 = revert(n)
    return n1 == n
}

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 */
fun hasDifferentDigits(n: Int): Boolean = digitNumber(n) != digitCountInNumber(n,n % 10)


/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 */
fun squareSequenceDigit(n: Int): Int {
    var leng = 0
    var number = 0
    while (leng < n) {
        number += 1
        leng += digitNumber(number * number)
    }
    number *= number
    for (i in 1..leng - n) {
        number /= 10
    }
    return number % 10
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 */
fun fibSequenceDigit(n: Int): Int {
    var leng = 0
    var number = 0
    while (leng < n) {
        number += 1
        leng += digitNumber(fib(number))
    }
    number = fib(number)
    for (i in 1..leng - n) {
        number /= 10
    }
    return number % 10
}
