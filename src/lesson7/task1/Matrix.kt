@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson7.task1

import lesson7.task2.canOpenLock

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E

    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: Cell, value: E)
}

/**
 * Простая
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> {
    if ((height <= 0) || (width <= 0)) throw IllegalArgumentException()
    val result = MatrixImpl(height, width, e)
    for (i in 0 until height) for (j in 0 until width) result[i, j] = e
    return result
}

/**
 * Средняя сложность
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(override val height: Int, override val width: Int, e: E) : Matrix<E> {
    private val list = mutableListOf<E>()

    init {
        for (i in 0 until height * width) {
            list.add(e)
        }
    }

    override fun get(row: Int, column: Int): E = list[row * width + column]

    override fun get(cell: Cell): E = get(cell.row, cell.column)

    override fun set(row: Int, column: Int, value: E) {
        list[row * width + column]=value
    }

    override fun set(cell: Cell, value: E) {
        set(cell.row, cell.column, value)
    }

    override fun equals(other: Any?):Boolean {
        var point1=0
        if (other !is MatrixImpl<*>) return false
        if ((height != other.height) || (width != other.width)) return false

        for (i in 0 until this.height)
            for (j in 0 until this.width)
                if (this[i, j] == other[i, j])
                    point1+=1

        return (point1==this.width*this.height)
    }

    override fun hashCode(): Int {
        var result = 5
        result = result * 31 + height
        result = result * 31 + width
        return result
    }

    override fun toString(): String {
        val sb = StringBuilder()
        for (row in 0 until height) {
            for (column in 0 until width) {
                sb.append(this[row, column])
            }
        }
        return "$sb" // or, sb.toString()
    }
}

