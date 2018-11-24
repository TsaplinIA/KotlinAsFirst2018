@file:Suppress("UNUSED_PARAMETER", "unused")
package lesson9.task1

import lesson7.task1.printDivisionProcess
import java.lang.IllegalArgumentException

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
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> =
        if (height <= 0 || width <= 0) throw IllegalArgumentException()
        else MatrixImpl(height, width, e)

/**
 * Средняя сложность
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(override val height: Int, override val width: Int, type: E) : Matrix<E> {
    private val matrixBody = MutableList(height) { MutableList(width) { type } }
    override fun get(row: Int, column: Int): E = matrixBody[row][column]

    override fun get(cell: Cell): E = matrixBody[cell.row][cell.column]

    override fun set(row: Int, column: Int, value: E) {
        matrixBody[row][column] = value
    }

    override fun set(cell: Cell, value: E) {
        matrixBody[cell.row][cell.column] = value
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Matrix<*>) return false
        for (i in 0 until height)
            for (j in 0 until width) if (matrixBody[i][j] != other[i, j]) return false
        return true
    }

    override fun toString(): String {
        val resList = mutableListOf<String>()
        for (i in 0 until height) {
            for (j in 0 until width) {
                resList.add(matrixBody[i][j].toString())
                if (j != width - 1) resList.add(" ")
            }
            if (i != height - 1) resList.add("\n")
        }
        return resList.joinToString("")
    }
}

fun main(args: Array<String>) {
    printDivisionProcess(52805, 68075, "C:\\Users\\AS\\Desktop\\out\\wtf2.txt")
}

