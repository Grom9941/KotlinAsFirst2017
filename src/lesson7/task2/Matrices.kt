@file:Suppress("UNUSED_PARAMETER")

package lesson7.task2

import java.util.Collections.swap
import lesson7.task1.Matrix
import lesson7.task1.createMatrix
import java.lang.Math.abs

// Все задачи в этом файле требуют наличия реализации интерфейса "Матрица" в Matrix.kt

/**
 * Пример
 *
 * Транспонировать заданную матрицу matrix.
 * При транспонировании строки матрицы становятся столбцами и наоборот:
 *
 * 1 2 3      1 4 6 3
 * 4 5 6  ==> 2 5 5 2
 * 6 5 4      3 6 4 1
 * 3 2 1
 */
fun <E> transpose(matrix: Matrix<E>): Matrix<E> {
    if (matrix.width < 1 || matrix.height < 1) return matrix
    val result = createMatrix(height = matrix.width, width = matrix.height, e = matrix[0, 0])
    for (i in 0 until matrix.width) {
        for (j in 0 until matrix.height) {
            result[i, j] = matrix[j, i]
        }
    }
    return result
}

/**
 * Пример
 *
 * Сложить две заданные матрицы друг с другом.
 * Складывать можно только матрицы совпадающего размера -- в противном случае бросить IllegalArgumentException.
 * При сложении попарно складываются соответствующие элементы матриц
 */
operator fun Matrix<Int>.plus(other: Matrix<Int>): Matrix<Int> {
    if (width != other.width || height != other.height) throw IllegalArgumentException()
    if (width < 1 || height < 1) return this
    val result = createMatrix(height, width, this[0, 0])
    for (i in 0 until height) {
        for (j in 0 until width) {
            result[i, j] = this[i, j] + other[i, j]
        }
    }
    return result
}

/**
 * Сложная
 *
 * Заполнить матрицу заданной высоты height и ширины width
 * натуральными числами от 1 до m*n по спирали,
 * начинающейся в левом верхнем углу и закрученной по часовой стрелке.
 *
 * Пример для height = 3, width = 4:
 *  1  2  3  4
 * 10 11 12  5
 *  9  8  7  6
 */
fun generateSpiral(height: Int, width: Int): Matrix<Int> {
    var number = 0
    var i = 0
    var j = 0
    val result = createMatrix(height, width, 0)
    while (height * width > number) {
        while ((height * width > number) && (result[j, i] == 0)) {
            number += 1
            result[j, i] = number
            i += 1
            if (i == width) break
        }
        i -= 1
        j += 1
        if (j !in 0 until height) break

        while ((result[j, i] == 0) && (height * width > number)) {
            number += 1
            result[j, i] = number
            j += 1
            if (j == height) break
        }
        j -= 1
        i -= 1
        if (i !in 0 until width) break

        while (((i >= 0) && (result[j, i] == 0)) && (height * width > number)) {
            number += 1
            result[j, i] = number
            i -= 1
            if (i == -1) break
        }
        i += 1
        j -= 1
        if (j !in 0 until height) break

        while (((j >= 0) && (result[j, i] == 0)) && (height * width > number)) {
            number += 1
            result[j, i] = number
            j -= 1
            if (j == -1) break
        }
        j += 1
        i += 1
        if (i !in 0 until width) break
    }
    return result
}

/**
 * Сложная
 *
 * Заполнить матрицу заданной высоты height и ширины width следующим образом.
 * Элементам, находящимся на периферии (по периметру матрицы), присвоить значение 1;
 * периметру оставшейся подматрицы – значение 2 и так далее до заполнения всей матрицы.
 *
 * Пример для height = 5, width = 6:
 *  1  1  1  1  1  1
 *  1  2  2  2  2  1
 *  1  2  3  3  2  1
 *  1  2  2  2  2  1
 *  1  1  1  1  1  1
 */
fun generateRectangles(height: Int, width: Int): Matrix<Int> {
    var height0 = height - 1
    var width0 = width - 1
    var height1 = 0
    var width1 = 0
    var kol = 1
    val result = createMatrix(height, width, 0)
    while ((width0 >= width1) && (height0 >= height1)) {
        for (i in width1..width0) {
            result[height1, i] = kol
            result[height0, i] = kol
        }
        for (i in height1..height0) {
            result[i, width0] = kol
            result[i, width1] = kol
        }
        height0 -= 1;width0 -= 1;height1 += 1;width1 += 1;kol += 1
    }
    return result
}

/**
 * Сложная
 *
 * Заполнить матрицу заданной высоты height и ширины width диагональной змейкой:
 * в левый верхний угол 1, во вторую от угла диагональ 2 и 3 сверху вниз, в третью 4-6 сверху вниз и так далее.
 *
 * Пример для height = 5, width = 4:
 *  1  2  4  7
 *  3  5  8 11
 *  6  9 12 15
 * 10 13 16 18
 * 14 17 19 20
 */
fun generateSnake(height: Int, width: Int): Matrix<Int> {
    val result = createMatrix(height, width, 0)
    var width0 = 0
    var height0 = 0
    var kol = 1
    var width1 = 0
    var height1: Int
    while (width0 <= width - 1) {
        result[height0, width0] = kol
        kol += 1
        height0 += 1
        width0 -= 1
        if ((width0 !in 0 until width) || (height0 !in 0 until height)) {
            height0 = 0
            width0 = width1 + 1
            width1 += 1
        }
    }
    height0 = 1;width0 = width - 1;height1 = height0
    while (height0 <= height - 1) {
        result[height0, width0] = kol
        kol += 1
        height0 += 1
        width0 -= 1
        if ((width0 !in 0 until width) || (height0 !in 0 until height)) {
            width0 = width - 1
            height0 = height1 + 1
            height1 += 1
        }
    }
    return result
}

/**
 * Средняя
 *
 * Содержимое квадратной матрицы matrix (с произвольным содержимым) повернуть на 90 градусов по часовой стрелке.
 * Если height != width, бросить IllegalArgumentException.
 *
 * Пример:    Станет:
 * 1 2 3      7 4 1
 * 4 5 6      8 5 2
 * 7 8 9      9 6 3
 */
fun <E> rotate(matrix: Matrix<E>): Matrix<E> {
    if (matrix.height != matrix.width) throw IllegalArgumentException()
    val result = createMatrix(matrix.height, matrix.width, matrix[0, 0])
    for (i in 0 until matrix.width)
        for (j in 0 until matrix.height) {
            result[j, matrix.height - i - 1] = matrix[i, j]
        }
    return result
}

/**
 * Сложная
 *
 * Проверить, является ли квадратная целочисленная матрица matrix латинским квадратом.
 * Латинским квадратом называется матрица размером n x n,
 * каждая строка и каждый столбец которой содержат все числа от 1 до n.
 * Если height != width, вернуть false.
 *
 * Пример латинского квадрата 3х3:
 * 2 3 1
 * 1 2 3
 * 3 1 2
 */
fun isLatinSquare(matrix: Matrix<Int>): Boolean {
    if (matrix.height != matrix.width) return false
    if ((matrix.height == 1) && (matrix.width == 1)) return matrix[0, 0] == 1

    for (h in 0 until matrix.height)
        for (i in 0 until matrix.height)
            for (j in i + 1 until matrix.height) {
                if ((matrix[h, i] == matrix[h, j]) || (matrix[h, i] !in 1..matrix.height) || (matrix[h, j] !in 1..matrix.height)) return false

                if ((matrix[i, h] == matrix[j, h]) || (matrix[i, h] !in 1..matrix.height) || (matrix[j, h] !in 1..matrix.height)) return false
            }
    return true
}

/**
 * Средняя
 *
 * В матрице matrix каждый элемент заменить суммой непосредственно примыкающих к нему
 * элементов по вертикали, горизонтали и диагоналям.
 *
 * Пример для матрицы 4 x 3: (11=2+4+5, 19=1+3+4+5+6, ...)
 * 1 2 3       11 19 13
 * 4 5 6  ===> 19 31 19
 * 6 5 4       19 31 19
 * 3 2 1       13 19 11
 *
 * Поскольку в матрице 1 х 1 примыкающие элементы отсутствуют,
 * для неё следует вернуть как результат нулевую матрицу:
 *
 * 42 ===> 0
 */
fun sumNeighbours(matrix: Matrix<Int>): Matrix<Int> {
    val result = createMatrix(matrix.height, matrix.width, 0)
    if ((matrix.height == 1) && (matrix.width == 1)) return result

    for (i in 0 until matrix.height) for (j in 0 until matrix.width) {
        if (i - 1 in 0 until matrix.height) result[i, j] += matrix[i - 1, j]
        if (i + 1 in 0 until matrix.height) result[i, j] += matrix[i + 1, j]
        if (j - 1 in 0 until matrix.width) result[i, j] += matrix[i, j - 1]
        if (j + 1 in 0 until matrix.width) result[i, j] += matrix[i, j + 1]
        if ((i - 1 in 0 until matrix.height) && (j - 1 in 0 until matrix.width)) result[i, j] += matrix[i - 1, j - 1]
        if ((i - 1 in 0 until matrix.height) && (j + 1 in 0 until matrix.width)) result[i, j] += matrix[i - 1, j + 1]
        if ((i + 1 in 0 until matrix.height) && (j - 1 in 0 until matrix.width)) result[i, j] += matrix[i + 1, j - 1]
        if ((i + 1 in 0 until matrix.height) && (j + 1 in 0 until matrix.width)) result[i, j] += matrix[i + 1, j + 1]
    }
    return result
}

/**
 * Средняя
 *
 * Целочисленная матрица matrix состоит из "дырок" (на их месте стоит 0) и "кирпичей" (на их месте стоит 1).
 * Найти в этой матрице все ряды и колонки, целиком состоящие из "дырок".
 * Результат вернуть в виде Holes(rows = список дырчатых рядов, columns = список дырчатых колонок).
 * Ряды и колонки нумеруются с нуля. Любой из спискоов rows / columns может оказаться пустым.
 *
 * Пример для матрицы 5 х 4:
 * 1 0 1 0
 * 0 0 1 0
 * 1 0 0 0 ==> результат: Holes(rows = listOf(4), columns = listOf(1, 3)): 4-й ряд, 1-я и 3-я колонки
 * 0 0 1 0
 * 0 0 0 0
 */
fun findHoles(matrix: Matrix<Int>): Holes {
    val row = mutableListOf<Int>()
    val column = mutableListOf<Int>()
    var width1= 0
    var height1 = 0
    for (i in 0 until matrix.height) {
        for (j in 0 until matrix.width) {
            if (matrix[i, j] == 0) width1 += 1
        }
        if (width1 == matrix.width) row.add(i)
        width1 = 0
    }
    for (j in 0 until matrix.width) {
        for (i in 0 until matrix.height) {
            if (matrix[i, j] == 0) height1 += 1
        }
        if (height1 == matrix.height) column.add(j)
        height1 = 0
    }
    return Holes(row, column)
}

/**
 * Класс для описания местонахождения "дырок" в матрице
 */
data class Holes(val rows: List<Int>, val columns: List<Int>)

/**
 * Средняя
 *
 * В целочисленной матрице matrix каждый элемент заменить суммой элементов подматрицы,
 * расположенной в левом верхнем углу матрицы matrix и ограниченной справа-снизу данным элементом.
 *
 * Пример для матрицы 3 х 3:
 *
 * 1  2  3      1  3  6
 * 4  5  6  =>  5 12 21
 * 7  8  9     12 27 45
 *
 * К примеру, центральный элемент 12 = 1 + 2 + 4 + 5, элемент в левом нижнем углу 12 = 1 + 4 + 7 и так далее.
 */
fun sumSubMatrix(matrix: Matrix<Int>): Matrix<Int> {
    for (i in 0 until matrix.height)
        for (j in 1 until matrix.width) matrix[i, j]+=matrix[i,j-1]
    for (i in 1 until matrix.height)
        for (j in 0 until matrix.width) matrix[i, j]+=matrix[i-1,j]
    return matrix
}

/**
 * Сложная
 *
 * Даны мозаичные изображения замочной скважины и ключа. Пройдет ли ключ в скважину?
 * То есть даны две матрицы key и lock, key.height <= lock.height, key.width <= lock.width, состоящие из нулей и единиц.
 *
 * Проверить, можно ли наложить матрицу key на матрицу lock (без поворота, разрешается только сдвиг) так,
 * чтобы каждой единице в матрице lock (штырь) соответствовал ноль в матрице key (прорезь),
 * а каждому нулю в матрице lock (дырка) соответствовала, наоборот, единица в матрице key (штырь).
 * Ключ при сдвиге не может выходить за пределы замка.
 *
 * Пример: ключ подойдёт, если его сдвинуть на 1 по ширине
 * lock    key
 * 1 0 1   1 0
 * 0 1 0   0 1
 * 1 1 1
 *
 * Вернуть тройку (Triple) -- (да/нет, требуемый сдвиг по высоте, требуемый сдвиг по ширине).
 * Если наложение невозможно, то первый элемент тройки "нет" и сдвиги могут быть любыми.
 */
fun canOpenLock(key: Matrix<Int>, lock: Matrix<Int>): Triple<Boolean, Int, Int> {
    for (i in 0 until key.height)
        for (j in 0 until key.width)
            if (key[i, j] == 1) key[i, j] = 0 else key[i, j] = 1
    var sum = 0
    for (i in 0..lock.height - key.height)
        for (j in 0..lock.width - key.width) {
            for (i1 in 0 until key.height)
                for (j1 in 0 until key.width)
                    if (key[i1, j1] == lock[i + i1, j + j1]) sum += 1
            if (sum == key.width * key.height) return Triple(true, i, j) else sum = 0
        }
    return Triple(false, 0, 0)
}

/**
 * Простая
 *
 * Инвертировать заданную матрицу.
 * При инвертировании знак каждого элемента матрицы следует заменить на обратный
 */
operator fun Matrix<Int>.unaryMinus(): Matrix<Int> {
    for (i in 0 until this.height)
        for (j in 0 until this.width) this[i, j] = -this[i, j]
    return this
}

/**
 * Средняя
 *
 * Перемножить две заданные матрицы друг с другом.
 * Матрицы можно умножать, только если ширина первой матрицы совпадает с высотой второй матрицы.
 * В противном случае бросить IllegalArgumentException.
 * Подробно про порядок умножения см. статью Википедии "Умножение матриц".
 */
operator fun Matrix<Int>.times(other: Matrix<Int>): Matrix<Int> {
    if (this.width != other.height) throw IllegalArgumentException()
    val result = createMatrix(this.height, other.width, 0)
    for (i in 0 until this.height)
        for (j in 0 until other.width)
            for (z in 0 until other.height)
                result[i, j] += this[i, z] * other[z, j]
    return result
}

/**
 * Сложная
 *
 * В матрице matrix размером 4х4 дана исходная позиция для игры в 15, например
 *  5  7  9  1
 *  2 12 14 15
 *  3  4  6  8
 * 10 11 13  0
 *
 * Здесь 0 обозначает пустую клетку, а 1-15 – фишки с соответствующими номерами.
 * Напомним, что "игра в 15" имеет квадратное поле 4х4, по которому двигается 15 фишек,
 * одна клетка всегда остаётся пустой. Цель игры -- упорядочить фишки на игровом поле.
 *
 * В списке moves задана последовательность ходов, например [8, 6, 13, 11, 10, 3].
 * Ход задаётся номером фишки, которая передвигается на пустое место (то есть, меняется местами с нулём).
 * Фишка должна примыкать к пустому месту по горизонтали или вертикали, иначе ход не будет возможным.
 * Все номера должны быть в пределах от 1 до 15.
 * Определить финальную позицию после выполнения всех ходов и вернуть её.
 * Если какой-либо ход является невозможным или список содержит неверные номера,
 * бросить IllegalStateException.
 *
 * В данном случае должно получиться
 * 5  7  9  1
 * 2 12 14 15
 * 0  4 13  6
 * 3 10 11  8
 */
fun swap(matrix: Matrix<Int>, INull: Int, JNull: Int, INull1: Int, JNull1: Int) :Matrix<Int> {
    val number = matrix[INull, JNull]
    matrix[INull, JNull] = matrix[INull1, JNull1]
    matrix[INull1, JNull1] = number
    return  matrix
}
fun fifteenGameMoves(matrix: Matrix<Int>, moves: List<Int>): Matrix<Int> {
    var matrix1=matrix
    var INull=-1
    var JNull=-1
    val list= MutableList(16) {0}
    for (i in 0..3)
        for (j in 0..3) {
            if (matrix[i, j] !in 0..15) throw IllegalStateException()
            list[matrix[i,j]]+=1
            if (list[matrix[i,j]]>1) throw IllegalStateException()
            if (matrix[i, j] == 0) {
                INull = i
                JNull = j
            }
        }
    if (INull==-1) throw IllegalStateException()
    for (i in 0 until moves.size) {
        if (INull - 1 in 0..3) if (matrix1[INull - 1, JNull] == moves[i]) {
            matrix1 = swap(matrix1, INull, JNull, INull - 1, JNull)
            INull -= 1
            continue
        }
        if (INull + 1 in 0..3) if (matrix1[INull + 1, JNull] == moves[i]) {
            matrix1 = swap(matrix1, INull, JNull, INull + 1, JNull)
            INull += 1
            continue
        }
        if (JNull - 1 in 0..3) if (matrix1[INull, JNull - 1] == moves[i]) {
            matrix1 = swap(matrix1, INull, JNull, INull, JNull - 1)
            JNull -= 1
            continue
        }
        if (JNull + 1 in 0..3) if (matrix1[INull, JNull + 1] == moves[i]) {
            matrix1 = swap(matrix1, INull, JNull, INull, JNull + 1)
            JNull += 1
            continue
        }
        throw IllegalStateException()
    }
return matrix1
}
/**
 * Очень сложная
 *
 * В матрице matrix размером 4х4 дана исходная позиция для игры в 15, например
 *  5  7  9  2
 *  1 12 14 15
 *  3  4  6  8
 * 10 11 13  0
 *
 * Здесь 0 обозначает пустую клетку, а 1-15 – фишки с соответствующими номерами.
 * Напомним, что "игра в 15" имеет квадратное поле 4х4, по которому двигается 15 фишек,
 * одна клетка всегда остаётся пустой.
 *
 * Цель игры -- упорядочить фишки на игровом поле, приведя позицию к одному из следующих двух состояний:
 *
 *  1  2  3  4          1  2  3  4
 *  5  6  7  8   ИЛИ    5  6  7  8
 *  9 10 11 12          9 10 11 12
 * 13 14 15  0         13 15 14  0
 *
 * Можно математически доказать, что РОВНО ОДНО из этих двух состояний достижимо из любой исходной позиции.
 *
 * Вернуть решение -- список ходов, приводящих исходную позицию к одной из двух упорядоченных.
 * Каждый ход -- это перемена мест фишки с заданным номером с пустой клеткой (0),
 * при этом заданная фишка должна по горизонтали или по вертикали примыкать к пустой клетке (но НЕ по диагонали).
 * К примеру, ход 13 в исходной позиции меняет местами 13 и 0, а ход 11 в той же позиции невозможен.
 *
 * Одно из решений исходной позиции:
 *
 * [8, 6, 14, 12, 4, 11, 13, 14, 12, 4,
 * 7, 5, 1, 3, 11, 7, 3, 11, 7, 12, 6,
 * 15, 4, 9, 2, 4, 9, 3, 5, 2, 3, 9,
 * 15, 8, 14, 13, 12, 7, 11, 5, 7, 6,
 * 9, 15, 8, 14, 13, 9, 15, 7, 6, 12,
 * 9, 13, 14, 15, 12, 11, 10, 9, 13, 14,
 * 15, 12, 11, 10, 9, 13, 14, 15]
 *
 * Перед решением этой задачи НЕОБХОДИМО решить предыдущую
 */
fun fifteenGameSolution(matrix: Matrix<Int>): List<Int> = TODO()
//fun nul(matrix: Matrix<Int>): List<Int> //{//нахожу 0
/**    for (i in 0..3) for (j in 0..3) if (matrix[i, j] == 0) return listOf(i, j)
    return listOf(-1)              //исключение
}

fun next(matrix: Matrix<Int>, number: Int): List<Int> {//следующая цифра
    for (i in 0..3) for (j in 0..3) if (matrix[i, j] == number) return listOf(i, j)
    return listOf(-1)
}

fun near(matrix: Matrix<Int>, nul: List<Int>, number: List<Int>): List<Int> {
    var nulI= nul[0]
    var nulJ= nul[1]
    val numberI= number[0]
    val numberJ = number[1]
    val moves = mutableListOf<Int>()
    while (abs(nulI-numberI) !=1 || abs(nulJ-numberJ) != 1) {


    }
}

... {
    //нахожу 0
    val i1 = nul(matrix)[0]
    val j1 = nul(matrix)[1]
    //
    for (number in 1..15) {
        //находим место следующей ячейки
        val INumber=next(matrix, number)[0]
        val JNumber=next(matrix, number)[1]
        //
        //подвожу '0' к number

    }

*/
//}
