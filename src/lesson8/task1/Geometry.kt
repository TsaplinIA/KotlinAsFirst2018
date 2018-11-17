@file:Suppress("UNUSED_PARAMETER")

package lesson8.task1

import lesson1.task1.sqr
import java.lang.IllegalArgumentException
import kotlin.math.*

/**
 * Точка на плоскости
 */
data class Point(val x: Double, val y: Double) {
    /**
     * Пример
     *
     * Рассчитать (по известной формуле) расстояние между двумя точками
     */
    fun distance(other: Point): Double = sqrt(sqr(x - other.x) + sqr(y - other.y))
}

/**
 * Треугольник, заданный тремя точками (a, b, c, см. constructor ниже).
 * Эти три точки хранятся в множестве points, их порядок не имеет значения.
 */
class Triangle private constructor(private val points: Set<Point>) {

    private val pointList = points.toList()

    val a: Point get() = pointList[0]

    val b: Point get() = pointList[1]

    val c: Point get() = pointList[2]

    constructor(a: Point, b: Point, c: Point) : this(linkedSetOf(a, b, c))

    /**
     * Пример: полупериметр
     */
    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0

    /**
     * Пример: площадь
     */
    fun area(): Double {
        val p = halfPerimeter()
        return sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }

    /**
     * Пример: треугольник содержит точку
     */
    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area()
    }

    override fun equals(other: Any?) = other is Triangle && points == other.points

    override fun hashCode() = points.hashCode()

    override fun toString() = "Triangle(a = $a, b = $b, c = $c)"
}

/**
 * Окружность с заданным центром и радиусом
 */
data class Circle(val center: Point, val radius: Double) {
    /**
     * Простая
     *
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0.
     */
    fun distance(other: Circle): Double {
        val temp = center.distance(other.center) - (radius + other.radius)
        return if (temp > 0) temp else 0.0
    }

    /**
     * Тривиальная
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    fun contains(p: Point): Boolean = center.distance(p) <= radius
}

/**
 * Отрезок между двумя точками
 */
data class Segment(val begin: Point, val end: Point) {
    override fun equals(other: Any?) =
            other is Segment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
            begin.hashCode() + end.hashCode()

    val length = begin.distance(end)
}

/**
 * Средняя
 *
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException
 */
fun diameter(vararg points: Point): Segment {
    val pointsList = points.toMutableList()
    val segmentList = mutableListOf<Segment>()
    if (points.size < 2) throw IllegalArgumentException()
    repeat(points.size - 1) {
        for (i in 1..pointsList.lastIndex) segmentList.add(Segment(pointsList[0], pointsList[i]))
        pointsList.removeAt(0)
    }
    segmentList.forEach { println(it.length) }
    return segmentList.maxBy { it.length }!!
}

/**
 * Простая
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
fun circleByDiameter(diameter: Segment): Circle {
    val x = (diameter.begin.x + diameter.end.x) / 2
    val y = (diameter.begin.y + diameter.end.y) / 2
    val rad = diameter.length / 2
    return Circle(Point(x, y), rad)
}

/**
 * Прямая, заданная точкой point и углом наклона angle (в радианах) по отношению к оси X.
 * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
 * или: y * cos(angle) = x * sin(angle) + b, где b = point.y * cos(angle) - point.x * sin(angle).
 * Угол наклона обязан находиться в диапазоне от 0 (включительно) до PI (исключительно).
 */
class Line private constructor(val b: Double, val angle: Double) {
    init {
        require(angle >= 0 && angle < PI) { "Incorrect line angle: $angle" }
    }

    constructor(point: Point, angle: Double) : this(point.y * cos(angle) - point.x * sin(angle), angle)

    /**
     * Средняя
     *
     * Найти точку пересечения с другой линией.
     * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой)
     */
    fun crossPoint(other: Line): Point {
        val x = (other.b * cos(angle) - b * cos(other.angle)) / sin(angle - other.angle)
        val y = (other.b * sin(angle) - b * sin(other.angle)) / sin(angle - other.angle)
        return Point(x, y)
    }

    override fun equals(other: Any?) = other is Line && angle == other.angle && b == other.b

    override fun hashCode(): Int {
        var result = b.hashCode()
        result = 31 * result + angle.hashCode()
        return result
    }

    override fun toString() = "Line(${cos(angle)} * y = ${sin(angle)} * x + $b)"
}

/**
 * Средняя
 *
 * Построить прямую по отрезку
 */
fun lineBySegment(s: Segment): Line = lineByPoints(s.begin, s.end)


/**
 * Средняя
 *
 * Построить прямую по двум точкам
 */
fun lineByPoints(a: Point, b: Point): Line {
    val angle = atan2(b.y - a.y, b.x - a.x) % PI
    return Line(a, if (angle >= 0) angle else (PI + angle) % PI)
}

/**
 * Сложная
 *
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */
fun bisectorByPoints(a: Point, b: Point): Line {
    val o = Point(a.x / 2 + b.x / 2, a.y / 2 + b.y / 2)
    val angle = (lineByPoints(a, b).angle + PI / 2) % PI
    return Line(o, if (angle >= 0) angle else (PI + angle) % PI)
}

fun main(args: Array<String>) {
    val p1 = Point(0.17881987294608181, -632.0)
    val p2 = Point(0.2872732593441374, 0.7218265148398424)
    val p3 = Point(-632.0, 0.21005159415743402)
    val p4 = Point(-2.220446049250313e-16, 0.7751566578724906)
    val p5 = Point(0.20564502010276142, 0.9155827183461711)
    val p6 = Point(0.983939703604162, 0.8410946988921123)
    val p7 = Point(-2.220446049250313e-16, 0.45600466970764564)
    val p8 = Point(5e-324, 0.27484140434411486)
    val p9 = Point(0.2338835157812158, 0.3003284730266935)
    val p10 = Point(-5e-324, 0.3699706919019775)
    val p11 = Point(5e-324, 0.16147622369422454)
    val p12 = Point(0.5505475789627361, -2.220446049250313e-16)
    val p13 = Point(-632.0, 0.0)
    val p14 = Point(2.220446049250313e-16, 0.983773589099756)
    val p15 = Point(0.8737139714518672, 0.49409623952655357)
    val p16 = Point(5e-324, 0.0)
    val p17 = Point(0.9547678809240446, 2.220446049250313e-16)
    val p18 = Point(0.8908264353843057, 0.019278339493119967)
    val p19 = Point(0.9297932618768963, 0.5677331488095688)
    val p20 = Point(0.0, 0.2080649813509965)
    println(diameter(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20).length)
}

/**
 * Средняя
 *
 * Задан список из n окружностей на плоскости. Найти пару наименее удалённых из них.
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> {
    if (circles.size < 2) throw IllegalArgumentException()
    val cirList = circles.toMutableList()
    val pList = mutableListOf<Pair<Circle, Circle>>()
    repeat(circles.size - 1) {
        for (i in 1..cirList.lastIndex) pList.add(cirList[0] to cirList[i])
        cirList.removeAt(0)
    }
    return pList.minBy { it.first.distance(it.second) }!!
}

/**
 * Сложная
 *
 * Дано три различные точки. Построить окружность, проходящую через них
 * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
 * Описание алгоритмов см. в Интернете
 * (построить окружность по трём точкам, или
 * построить окружность, описанную вокруг треугольника - эквивалентная задача).
 */
fun circleByThreePoints(a: Point, b: Point, c: Point): Circle {
    val center = bisectorByPoints(a, b).crossPoint(bisectorByPoints(b, c))
    println(center)
    return Circle(center, max(a.distance(center), b.distance(center)))
}

/**
 * Очень сложная
 *
 * Дано множество точек на плоскости. Найти круг минимального радиуса,
 * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
 *
 * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
 * три точки данного множества, либо иметь своим диаметром отрезок,
 * соединяющий две самые удалённые точки в данном множестве.
 */
fun minContainingCircle(vararg points: Point): Circle = TODO()
//{
//    when (points.size) {
//        0 -> throw IllegalArgumentException()
//        1 -> return Circle(points[0], 0.0)
//    }
//
//}

