
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
    var s=abs(n)
    var s1=1
    while (s>=10) {
        s/=10
        s1++
    }
    return s1
}

    /**
     * Простая
     *
     * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
     * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
     */

fun fib(n: Int): Int {
        if (n in 1..2) return 1
           return fib(n-1)+ fib(n-2)
    }

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    var s=m
    var s1=n
    while (s!=s1)
    if (s1 > s)
        s1-=s
    else
        s-=s1

    return m*n/s
}

/**
  * Простая
  *
  * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
  */

fun minDivisor(n: Int): Int {
    var s=1
    for (i in 2..Math.ceil(Math.sqrt(n.toDouble())).toInt())
        if (n % i == 0) {
            s=i; break
        }
    if (s== 1) s=n
    return s
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    var s=1
    for (i in n-1 downTo 1)
        if (n % i == 0) {
            s=i; break
        }
    return s
}

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean {
    var s=0
    if ((n==1) || (m==1)) return true
    if (m>n) s=n else s=m
    for (i in 2..s)
        if ((n % i == 0) && (m % i == 0)) {
        s=1; break
    }
   return s!=1
}

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean {
    var s :Int = Math.sqrt(m.toDouble()).toInt()
    if (s*s < m) s++
    return s<=Math.sqrt(n.toDouble())
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun sin(x: Double, eps: Double): Double {
    var s=x
    var i=1
    var sin=x%(2* PI)
    while (abs(s)>=eps){
        s=-s*(x%(2* PI))/((i*2+1)*(i*2)).toDouble()*(x%(2* PI))
        i+=1
        sin+=s
    }
    return(sin)
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun cos(x: Double, eps: Double): Double {
    var s=1.0
    var i=1
    var cos=1.0
    while (abs(s)>=eps){
        s= -s*(x%(2* PI))/((i*2-1)*(i*2)).toDouble()*(x%(2* PI))
        i+=1
        cos+=s
    }
    return(cos)
}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 * Не использовать строки при решении задачи.
 */
fun revert(n: Int): Int {
    var s=0
    var n1=n
    while (n1>=1) {
        s=s*10+(n1%10)
        n1 /= 10
    }
    return s
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 */
fun isPalindrome(n: Int): Boolean {
    var n1=n
    var s=1
    while (n1>=10) { s=s*10; n1=n1/10 }

    n1=n
  while (n1>=10) {
      if ((n1/s) != (n1%10)) return false
      n1=(n1%s)/10
      s /= 100

  }
 return true
}

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 */
fun hasDifferentDigits(n: Int): Boolean {

  var n1=n
  var leng=1
  var ch=0
    var kol=0
  while (n1>=10) {n1/=10; kol+=1; leng*=10}
    n1=kol
    var k=leng

    for (i in 1..kol) { k=leng
        for (j in 1..n1) {
            k = leng / 10
            ch = n / k % 10

            if (ch!=(n/leng%10)) return true
        }
        leng/=10
        n1-=1
    }
    return false
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 */
fun squareSequenceDigit(n: Int): Int {
 var leng=0
    var chis=0;
    while (leng<n) {
        chis+=1
        leng+=digitNumber(chis*chis)
    }
    var s=0
    chis*=chis
    var ch1=chis
for (i in 1..leng-n) {
    ch1/=10
}
    return ch1%10
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 */
fun fibSequenceDigit(n: Int): Int {
    var k=0
    var n1=n
    var k1=1
    var k2=0
    while (n1>k1) {
        k+=1
        k2= fib(k)
        n1--
        k1=1
        while (k2>9){
            n1--
            k2/=10
            k1++
        }
    }
    k++
    k1=fib(k)
    var s=0
    while (k1>=1) {
        s=s*10+(k%10)
        k1 /= 10
    }
    while (n1>1){
        n1--
        s/=10
    }
    return s%10
}
