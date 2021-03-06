@file:Suppress("UNUSED_PARAMETER")

package lesson2.task1

import lesson1.task1.discriminant
import lesson1.task1.sqr

/**
 * Пример
 *
 * Найти наименьший корень биквадратного уравнения ax^4 + bx^2 + c = 0
 */
fun minBiRoot(a: Double, b: Double, c: Double): Double {
    // 1: в главной ветке if выполняется НЕСКОЛЬКО операторов
    if (a == 0.0) {
        if (b == 0.0) return Double.NaN // ... и ничего больше не делать
        val bc = -c / b
        if (bc < 0.0) return Double.NaN // ... и ничего больше не делать
        return -Math.sqrt(bc)
        // Дальше функция при a == 0.0 не идёт
    }
    val d = discriminant(a, b, c)   // 2
    if (d < 0.0) return Double.NaN  // 3
    // 4
    val y1 = (-b + Math.sqrt(d)) / (2 * a)
    val y2 = (-b - Math.sqrt(d)) / (2 * a)
    val y3 = Math.max(y1, y2)       // 5
    if (y3 < 0.0) return Double.NaN // 6
    return -Math.sqrt(y3)           // 7
}

/**
 * Простая
 *
 * Мой возраст. Для заданного 0 < n < 200, рассматриваемого как возраст человека,
 * вернуть строку вида: «21 год», «32 года», «12 лет».
 */
fun ageDescription(age: Int): String {
    return if (age % 100 in 5..20) {
        "$age лет"
    } else
        if (age % 10 == 1) {
            "$age год"
        } else
            if (age % 10 in 2..4) {
                "$age года"
            } else {
                "$age лет"
            }
}

/**
 * Простая
 *
 * Путник двигался t1 часов со скоростью v1 км/час, затем t2 часов — со скоростью v2 км/час
 * и t3 часов — со скоростью v3 км/час.
 * Определить, за какое время он одолел первую половину пути?
 */
fun timeForHalfWay(t1: Double, v1: Double,
                   t2: Double, v2: Double,
                   t3: Double, v3: Double): Double {
    val s = ((t1 * v1) + (t2 * v2) + (t3 * v3)) / 2
    return if (t1 * v1 >= s) s / v1 else
        if ((t1 * v1) + (t2 * v2) >= s) t1 + ((s - t1 * v1) / v2) else
            (t1 + t2 + ((s - (t1 * v1) - (t2 * v2)) / v3))
}

/**
 * Простая
 *
 * Нa шахматной доске стоят черный король и две белые ладьи (ладья бьет по горизонтали и вертикали).
 * Определить, не находится ли король под боем, а если есть угроза, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от первой ладьи, 2, если только от второй ладьи,
 * и 3, если угроза от обеих ладей.
 * Считать, что ладьи не могут загораживать друг друга
 */
fun whichRookThreatens(kingX: Int, kingY: Int,
                       rookX1: Int, rookY1: Int,
                       rookX2: Int, rookY2: Int): Int {
    var numb = 0
    val risk = (kingX == rookX1)
    val risk1 = (kingY == rookY1)
    val risk2 = (kingX == rookX2)
    val risk3 = (kingY == rookY2)
    if (risk || risk1) numb++
    if (risk2 || risk3) numb++
    return if (numb == 2) 3 else
        if (numb == 0) 0 else
            if (risk || risk1) 1 else 2
}

/**
 * Простая
 *
 * На шахматной доске стоят черный король и белые ладья и слон
 * (ладья бьет по горизонтали и вертикали, слон — по диагоналям).
 * Проверить, есть ли угроза королю и если есть, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от ладьи, 2, если только от слона,
 * и 3, если угроза есть и от ладьи и от слона.
 * Считать, что ладья и слон не могут загораживать друг друга.
 */
fun rookOrBishopThreatens(kingX: Int, kingY: Int,
                          rookX: Int, rookY: Int,
                          bishopX: Int, bishopY: Int): Int {
    var s = 0
    if ((kingX == rookX) || (kingY == rookY)) s++
    if (((bishopX - bishopY) == (kingX - kingY)) || ((bishopX + bishopY) == (kingX + kingY))) s++
    return if (s == 0) 0 else
        if (s == 2) 3 else
            if ((kingX == rookX) || (kingY == rookY)) 1 else 2
}

/**
 * Простая
 *
 * Треугольник задан длинами своих сторон a, b, c.
 * Проверить, является ли данный треугольник остроугольным (вернуть 0),
 * прямоугольным (вернуть 1) или тупоугольным (вернуть 2).
 * Если такой треугольник не существует, вернуть -1.
 */
fun triangleKind(a: Double, b: Double, c: Double): Int {
    var a1 = a
    var b1 = b
    var c1 = c
    if ((a > b + c) || (b > a + c) || (c > b + a)) return -1 else {

        if (maxOf(a,b,c) ==b) {
            a1 = b
            b1 = a
            c1 = c
        }
        if (maxOf(a,b,c)==c) {
            a1 = c
            b1 = b
            c1 = a
        }
        return if (sqr(a1) == sqr(b1) + sqr(c1)) 1 else
            if (sqr(a1) < sqr(b1) + sqr(c1)) 0 else 2
    }
}

/**
 * Средняя
 *
 * Даны четыре точки на одной прямой: A, B, C и D.
 * Координаты точек a, b, c, d соответственно, b >= a, d >= c.
 * Найти длину пересечения отрезков AB и CD.
 * Если пересечения нет, вернуть -1.
 */
fun segmentLength(a: Int, b: Int, c: Int, d: Int): Int =
        if ((b < c) || (d < a)) -1 else
            if ((a <= c) && (d <= b)) d - c else
                if ((c <= b) && (b <= d) && (a < c)) b - c else
                    if ((c <= a) && (b <= d)) b - a else
                        d - a
