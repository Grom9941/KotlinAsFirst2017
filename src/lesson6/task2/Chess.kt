@file:Suppress("UNUSED_PARAMETER")
package lesson6.task2

import lesson6.task3.Graph
import java.util.*

/**
 * Клетка шахматной доски. Шахматная доска квадратная и имеет 8 х 8 клеток.
 * Поэтому, обе координаты клетки (горизонталь row, вертикаль column) могут находиться в пределах от 1 до 8.
 * Горизонтали нумеруются снизу вверх, вертикали слева направо.
 */
data class Square(val column: Int, val row: Int) {
    /**
     * Пример
     *
     * Возвращает true, если клетка находится в пределах доски
     */
    fun inside(): Boolean = column in 1..8 && row in 1..8

    /**
     * Простая
     *
     * Возвращает строковую нотацию для клетки.
     * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
     * Для клетки не в пределах доски вернуть пустую строку
     */
    fun notation(): String = if (!inside()) "" else (column+96).toChar()+"$row"
}

/**
 * Простая
 *
 * Создаёт клетку по строковой нотации.
 * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
 * Если нотация некорректна, бросить IllegalArgumentException
 */
fun square(notation: String): Square {
    if (notation.length != 2) throw IllegalArgumentException() else {
        val column = notation[0]
        val row = notation[1]
        if ((column !in 'a' .. 'h') || (row !in '1' .. '8')) throw IllegalArgumentException()
        return Square(column.toInt() - 96, row.toInt() - 48)
    }
}

/**
 * Простая
 *
 * Определить число ходов, за которое шахматная ладья пройдёт из клетки start в клетку end.
 * Шахматная ладья может за один ход переместиться на любую другую клетку
 * по вертикали или горизонтали.
 * Ниже точками выделены возможные ходы ладьи, а крестиками -- невозможные:
 *
 * xx.xxххх
 * xх.хxххх
 * ..Л.....
 * xх.хxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: rookMoveNumber(Square(3, 1), Square(6, 3)) = 2
 * Ладья может пройти через клетку (3, 3) или через клетку (6, 1) к клетке (6, 3).
 */
fun rookMoveNumber(start: Square, end: Square): Int {
    if ((!start.inside()) || (!end.inside())) throw IllegalArgumentException() else
    return if ((start.column==end.column) && (start.row==end.row)) 0 else
        if ((start.column==end.column) || (start.row==end.row)) 1 else 2
}

/**
 * Средняя
 *
 * Вернуть список из клеток, по которым шахматная ладья может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов ладьи см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: rookTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможен ещё один вариант)
 *          rookTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(3, 3), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          rookTrajectory(Square(3, 5), Square(8, 5)) = listOf(Square(3, 5), Square(8, 5))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun rookTrajectory(start: Square, end: Square): List<Square> = when {
    (start.column==end.column) && (start.row==end.row) -> listOf(Square(start.column,start.row))
    (start.column==end.column) || (start.row==end.row) -> listOf(Square(start.column,start.row),
            Square(end.column,end.row))
    else -> listOf(Square(start.column,start.row), Square(start.column,end.row), Square(end.column,end.row))
}
/**
 * Простая
 *
 * Определить число ходов, за которое шахматный слон пройдёт из клетки start в клетку end.
 * Шахматный слон может за один ход переместиться на любую другую клетку по диагонали.
 * Ниже точками выделены возможные ходы слона, а крестиками -- невозможные:
 *
 * .xxx.ххх
 * x.x.xххх
 * xxСxxxxx
 * x.x.xххх
 * .xxx.ххх
 * xxxxx.хх
 * xxxxxх.х
 * xxxxxхх.
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если клетка end недостижима для слона, вернуть -1.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Примеры: bishopMoveNumber(Square(3, 1), Square(6, 3)) = -1; bishopMoveNumber(Square(3, 1), Square(3, 7)) = 2.
 * Слон может пройти через клетку (6, 4) к клетке (3, 7).
 */
fun bishopMoveNumber(start: Square, end: Square): Int {
    if ((!start.inside()) || (!end.inside())) throw IllegalArgumentException() else
    return when {
        ((start.column + end.column + start.row + end.row) % 2 == 1) -> -1
        (start.column == end.column) && (start.row == end.row) -> 0
        (Math.abs(start.column - end.column) == Math.abs(start.row - end.row)) -> 1
        else -> 2
    }
}
/**
 * Сложная
 *
 * Вернуть список из клеток, по которым шахматный слон может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов слона см. предыдущую задачу.
 *
 * Если клетка end недостижима для слона, вернуть пустой список.
 *
 * Если клетка достижима:
 * - список всегда включает в себя клетку start
 * - клетка end включается, если она не совпадает со start.
 * - между ними должны находиться промежуточные клетки, по порядку от start до end.
 *
 * Примеры: bishopTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          bishopTrajectory(Square(3, 1), Square(3, 7)) = listOf(Square(3, 1), Square(6, 4), Square(3, 7))
 *          bishopTrajectory(Square(1, 3), Square(6, 8)) = listOf(Square(1, 3), Square(6, 8))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun bishopTrajectory(start: Square, end: Square): List<Square> {
    when {
        ((start.column + end.column + start.row + end.row) % 2 == 1) -> return listOf()
        (start.column == end.column) && (start.row == end.row) -> return listOf(Square(start.column, start.row))
        Math.abs(start.column - end.column) == Math.abs(start.row - end.row) ->
            return listOf(Square(start.column, start.row), Square(end.column, end.row))
        else -> {
            val x11 = (end.row + start.column + end.column - start.row) / 2
            val x12 = (start.row + start.column + end.column - end.row) / 2
            val x21 = (start.row + start.column + end.row - end.column) / 2
            val x22 = (start.row + end.column + end.row - start.column) / 2
            val sq:Square
            sq = when {
                (Math.abs(start.column - x11) == Math.abs(start.row - x21)) &&
                    (Math.abs(x11-end.column) == Math.abs(x21-end.row)) && (Square(x11,x21).inside()) -> Square(x11,x21)
                (Math.abs(start.column - x12) == Math.abs(start.row - x21)) &&
                    (Math.abs(x12-end.column) == Math.abs(x21-end.row)) && (Square(x12,x21).inside()) -> Square(x12,x21)
                (Math.abs(start.column - x11) == Math.abs(start.row - x22)) &&
                    (Math.abs(x11-end.column) == Math.abs(x22-end.row)) && (Square(x11,x22).inside()) -> Square(x11,x22)
                else -> Square(x12,x22)
            }
            return listOf(Square(start.column, start.row), sq, Square(end.column, end.row))
        }
    }
}

/**
 * Средняя
 *
 * Определить число ходов, за которое шахматный король пройдёт из клетки start в клетку end.
 * Шахматный король одним ходом может переместиться из клетки, в которой стоит,
 * на любую соседнюю по вертикали, горизонтали или диагонали.
 * Ниже точками выделены возможные ходы короля, а крестиками -- невозможные:
 *
 * xxxxx
 * x...x
 * x.K.x
 * x...x
 * xxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: kingMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Король может последовательно пройти через клетки (4, 2) и (5, 2) к клетке (6, 3).
 */
fun kingMoveNumber(start: Square, end: Square): Int {
    if ((!start.inside()) || (!end.inside())) throw IllegalArgumentException() else
    return if (Math.abs(start.column-end.column)>Math.abs(start.row-end.row)) Math.abs(start.column-end.column) else
        Math.abs(start.row-end.row)
}

/**
 * Сложная
 *
 * Вернуть список из клеток, по которым шахматный король может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов короля см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: kingTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможны другие варианты)
 *          kingTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(4, 2), Square(5, 2), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          kingTrajectory(Square(3, 5), Square(6, 2)) = listOf(Square(3, 5), Square(4, 4), Square(5, 3), Square(6, 2))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun kingTrajectory(start: Square, end: Square): List<Square> {
    var sc=start.column
    var sr=start.row
    var list= listOf(Square(start.column,start.row))
    for (i in 0 until kingMoveNumber(start,end)) {
        if (sc>end.column) sc-=1 else if (sc<end.column) sc+=1
        if (sr>end.row) sr-=1 else if (sr<end.row) sr+=1
        list+=Square(sc,sr)
    }
    return list
}

/**
 * Сложная
 *
 * Определить число ходов, за которое шахматный конь пройдёт из клетки start в клетку end.
 * Шахматный конь одним ходом вначале передвигается ровно на 2 клетки по горизонтали или вертикали,
 * а затем ещё на 1 клетку под прямым углом, образуя букву "Г".
 * Ниже точками выделены возможные ходы коня, а крестиками -- невозможные:
 *
 * .xxx.xxx
 * xxKxxxxx
 * .xxx.xxx
 * x.x.xxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: knightMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Конь может последовательно пройти через клетки (5, 2) и (4, 4) к клетке (6, 3).
 */
fun graphh():Graph{
    val graph =Graph()
    for (i in 1 .. 8) for (j in 1 .. 8) graph.addVertex("$i$j")
    for (i in 1 .. 8)
        for (j in 1 .. 8) {
            if (Square(i + 1, j - 2).inside()) {val i1 = i + 1;val j1 = j - 2
                graph.connect("$i$j", "$i1$j1"); }
            if (Square(i + 2, j - 1).inside()) {val i1 = i + 2;val j1 = j - 1
                graph.connect("$i$j", "$i1$j1"); }
            if (Square(i + 1, j + 2).inside()) {val i1 = i + 1;val j1 = j + 2
                graph.connect("$i$j", "$i1$j1"); }
            if (Square(i + 2, j + 1).inside()) {val i1 = i + 2;val j1 = j + 1
                graph.connect("$i$j", "$i1$j1"); }
        }
    return graph
}

fun knightMoveNumber(start: Square, end: Square): Int {
    val graph= graphh()
    if ((!start.inside()) || (!end.inside())) throw IllegalArgumentException() else
    return graph.bfs("${start.column}${start.row}", "${end.column}${end.row}")
}
/**
 * Очень сложная
 *
 * Вернуть список из клеток, по которым шахматный конь может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов коня см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры:
 *
 * knightTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 * здесь возможны другие варианты)
 * knightTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(5, 2), Square(4, 4), Square(6, 3))
 * (здесь возможен единственный вариант)
 * knightTrajectory(Square(3, 5), Square(5, 6)) = listOf(Square(3, 5), Square(5, 6))
 * (здесь опять возможны другие варианты)
 * knightTrajectory(Square(7, 7), Square(8, 8)) =
 *     listOf(Square(7, 7), Square(5, 8), Square(4, 6), Square(6, 7), Square(8, 8))
 *
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun knightTrajectory(start: Square, end: Square): List<Square> = bfs1(start,end).reversed()

fun bfs1(start: Square, finish: Square): List<Square> {
    val queue = ArrayDeque<Square>()
    val list = mutableListOf<Square>()
    val list1 = mutableListOf<Square>()
    var distance = 0
    list.add(start)
    list1.add(Square(0,0))
    queue.add(start)
    val visited = mutableMapOf(start to 0)
    while (queue.isNotEmpty()) {
        val next = queue.poll()
        if (next != finish) {
            val i = next.column
            val j = next.row
            distance += 1

            if ((Square(i + 1, j - 2).inside()) && (Square(i + 1, j - 2) !in visited)) {
                queue.add(Square(i + 1, j - 2));list.add(Square(i + 1, j - 2));list1.add(Square(i, j));visited.put(Square(i + 1, j - 2), distance)
            }
            if ((Square(i + 2, j - 1).inside()) && (Square(i + 2, j - 1) !in visited)) {
                queue.add(Square(i + 2, j - 1));list.add(Square(i + 2, j - 1));list1.add(Square(i, j));visited.put(Square(i + 2, j - 1), distance)
            }
            if ((Square(i + 1, j + 2).inside()) && (Square(i + 1, j + 2) !in visited)) {
                queue.add(Square(i + 1, j + 2));list.add(Square(i + 1, j + 2));list1.add(Square(i, j));visited.put(Square(i + 1, j + 2), distance)
            }
            if ((Square(i + 2, j + 1).inside()) && (Square(i + 2, j + 1) !in visited)) {
                queue.add(Square(i + 2, j + 1));list.add(Square(i + 2, j + 1));list1.add(Square(i, j));visited.put(Square(i + 2, j + 1), distance)
            }
            if ((Square(i - 1, j - 2).inside()) && (Square(i - 1, j - 2) !in visited)) {
                queue.add(Square(i - 1, j - 2));list.add(Square(i - 1, j - 2));list1.add(Square(i, j));visited.put(Square(i - 1, j - 2), distance)
            }
            if ((Square(i - 2, j - 1).inside()) && (Square(i - 2, j - 1) !in visited)) {
                queue.add(Square(i - 2, j - 1));list.add(Square(i - 2, j - 1));list1.add(Square(i, j));visited.put(Square(i - 2, j - 1), distance)
            }
            if ((Square(i - 1, j + 2).inside()) && (Square(i - 1, j + 2) !in visited)) {
                queue.add(Square(i - 1, j + 2));list.add(Square(i - 1, j + 2));list1.add(Square(i, j));visited.put(Square(i - 1, j + 2), distance)
            }
            if ((Square(i - 2, j + 1).inside()) && (Square(i - 2, j + 1) !in visited)) {
                queue.add(Square(i - 2, j + 1));list.add(Square(i - 2, j + 1));list1.add(Square(i, j));visited.put(Square(i - 2, j + 1), distance)
            }
        } else {
            var parents= list.indexOf(finish)
            var ancestry= list1[parents]
            val list3 = mutableListOf<Square>(finish)
                while (ancestry != Square(0,0)) {
                list3.add(ancestry)
                parents = list.indexOf(ancestry)
                ancestry = list1[parents]

            }
            return list3
        }
    }
    return mutableListOf<Square>()
}