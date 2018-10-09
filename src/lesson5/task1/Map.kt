@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson5.task1

/**
 * Пример
 *
 * Для заданного списка покупок `shoppingList` посчитать его общую стоимость
 * на основе цен из `costs`. В случае неизвестной цены считать, что товар
 * игнорируется.
 */
fun shoppingListCost(
        shoppingList: List<String>,
        costs: Map<String, Double>): Double {
    var totalCost = 0.0

    for (item in shoppingList) {
        val itemCost = costs[item]
        if (itemCost != null) {
            totalCost += itemCost
        }
    }

    return totalCost
}

/**
 * Пример
 *
 * Для набора "имя"-"номер телефона" `phoneBook` оставить только такие пары,
 * для которых телефон начинается с заданного кода страны `countryCode`
 */
fun filterByCountryCode(
        phoneBook: MutableMap<String, String>,
        countryCode: String) {
    val namesToRemove = mutableListOf<String>()

    for ((name, phone) in phoneBook) {
        if (!phone.startsWith(countryCode)) {
            namesToRemove.add(name)
        }
    }

    for (name in namesToRemove) {
        phoneBook.remove(name)
    }
}

/**
 * Пример
 *
 * Для заданного текста `text` убрать заданные слова-паразиты `fillerWords`
 * и вернуть отфильтрованный текст
 */
fun removeFillerWords(
        text: List<String>,
        vararg fillerWords: String): List<String> {
    val fillerWordSet = setOf(*fillerWords)

    val res = mutableListOf<String>()
    for (word in text) {
        if (word !in fillerWordSet) {
            res += word
        }
    }
    return res
}

/**
 * Пример
 *
 * Для заданного текста `text` построить множество встречающихся в нем слов
 */
fun buildWordSet(text: List<String>): MutableSet<String> {
    val res = mutableSetOf<String>()
    for (word in text) res.add(word)
    return res
}

/**
 * Средняя
 *
 * Объединить два ассоциативных массива `mapA` и `mapB` с парами
 * "имя"-"номер телефона" в итоговый ассоциативный массив, склеивая
 * значения для повторяющихся ключей через запятую.
 * В случае повторяющихся *ключей* значение из mapA должно быть
 * перед значением из mapB.
 *
 * Повторяющиеся *значения* следует добавлять только один раз.
 *
 * Например:
 *   mergePhoneBooks(
 *     mapOf("Emergency" to "112", "Police" to "02"),
 *     mapOf("Emergency" to "911", "Police" to "02")
 *   ) -> mapOf("Emergency" to "112, 911", "Police" to "02")
 */
fun mergePhoneBooks(mapA: Map<String, String>, mapB: Map<String, String>): Map<String, String> {
    val resMap = mapB + mapA
    val alphaMap: MutableMap<String, String> = mutableMapOf()
    for ((key, value) in mapB) if (value != resMap[key]) alphaMap[key] = "${resMap[key]}, $value"
    return resMap + alphaMap
}

/**
 * Простая
 *
 * По заданному ассоциативному массиву "студент"-"оценка за экзамен" построить
 * обратный массив "оценка за экзамен"-"список студентов с этой оценкой".
 *
 * Например:
 *   buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
 *     -> mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат"))
 */
fun buildGrades(grades: Map<String, Int>): Map<Int, List<String>> {
    val resMutMap: MutableMap<Int, MutableList<String>> = mutableMapOf()
    val resMap: MutableMap<Int, List<String>> = mutableMapOf()
    for ((name, grade) in grades)
        if (grade in resMutMap) resMutMap[grade]!!.add(name)
        else resMutMap[grade] = mutableListOf(name)
    for ((key, value) in resMutMap) resMap[key] = value.toList().sortedDescending()
    return resMap
}

/**
 * Простая
 *
 * Определить, входит ли ассоциативный массив a в ассоциативный массив b;
 * это выполняется, если все ключи из a содержатся в b с такими же значениями.
 *
 * Например:
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")) -> true
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")) -> false
 */
fun containsIn(a: Map<String, String>, b: Map<String, String>): Boolean {
    for ((key, value) in a) if (value != b[key]) return false
    return true
}

/**
 * Средняя
 *
 * Для заданного списка пар "акция"-"стоимость" вернуть ассоциативный массив,
 * содержащий для каждой акции ее усредненную стоимость.
 *
 * Например:
 *   averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
 *     -> mapOf("MSFT" to 150.0, "NFLX" to 40.0)
 */
fun averageStockPrice(stockPrices: List<Pair<String, Double>>): Map<String, Double> {
    val tempMap: MutableMap<String, MutableList<Double>> = mutableMapOf()
    for ((first, second) in stockPrices)
        if (first in tempMap) tempMap[first]?.add(second)
        else tempMap[first] = mutableListOf(second)
    val resMap: MutableMap<String, Double> = mutableMapOf()
    for ((first, second) in tempMap) resMap[first] = second.sum() / second.size
    return resMap
}

/**
 * Средняя
 *
 * Входными данными является ассоциативный массив
 * "название товара"-"пара (тип товара, цена товара)"
 * и тип интересующего нас товара.
 * Необходимо вернуть название товара заданного типа с минимальной стоимостью
 * или null в случае, если товаров такого типа нет.
 *
 * Например:
 *   findCheapestStuff(
 *     mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
 *     "печенье"
 *   ) -> "Мария"
 */
fun findCheapestStuff(stuff: Map<String, Pair<String, Double>>, kind: String): String? {
    var min: Double = Double.MAX_VALUE
    var resStr = ""
    for ((name, typePrice) in stuff) if (typePrice.first == kind && typePrice.second < min) {
        min = typePrice.second
        resStr = name
    }
    return if (resStr == "") null else resStr
}

/**
 * Сложная
 *
 * Для заданного ассоциативного массива знакомых через одно рукопожатие `friends`
 * необходимо построить его максимальное расширение по рукопожатиям, то есть,
 * для каждого человека найти всех людей, с которыми он знаком через любое
 * количество рукопожатий.
 * Считать, что все имена людей являются уникальными, а также что рукопожатия
 * являются направленными, то есть, если Марат знает Свету, то это не означает,
 * что Света знает Марата.
 *
 * Например:
 *   propagateHandshakes(
 *     mapOf(
 *       "Marat" to setOf("Mikhail", "Sveta"),
 *       "Sveta" to setOf("Marat"),
 *       "Mikhail" to setOf("Sveta")
 *     )
 *   ) -> mapOf(
 *          "Marat" to setOf("Mikhail", "Sveta"),
 *          "Sveta" to setOf("Marat", "Mikhail"),
 *          "Mikhail" to setOf("Sveta", "Marat")
 *        )
 */
fun propagateHandshakes(friends: Map<String, Set<String>>): Map<String, Set<String>> {
    val mapTemp = mutableMapOf<String, MutableSet<String>>()
    var mapNow: MutableMap<String, MutableSet<String>>
    for ((key) in friends) mapTemp[key] = friends[key]!!.toMutableSet()
    do {
        mapNow = mapTemp
        val mapAdd = mutableMapOf<String, MutableSet<String>>()
        for ((name, set) in mapNow)
            for (el in set) if (mapNow.contains(el)) mapAdd[name] = mapNow[el]!! else mapTemp[el] = mutableSetOf()
        for ((name, set) in mapAdd) mapTemp[name]?.addAll(set)
    } while (mapTemp != mapNow)
    val resMap = mutableMapOf<String, Set<String>>()
    for ((name, set) in mapNow) {
        if (name in set) set.remove(name)
        resMap[name] = set.toList().sorted().toSet()
    }
    return resMap
}

/**
 * Простая
 *
 * Удалить из изменяемого ассоциативного массива все записи,
 * которые встречаются в заданном ассоциативном массиве.
 * Записи считать одинаковыми, если и ключи, и значения совпадают.
 *
 * ВАЖНО: необходимо изменить переданный в качестве аргумента
 *        изменяемый ассоциативный массив
 *
 * Например:
 *   subtractOf(a = mutableMapOf("a" to "z"), mapOf("a" to "z"))
 *     -> a changes to mutableMapOf() aka becomes empty
 */
fun subtractOf(a: MutableMap<String, String>, b: Map<String, String>): Unit {
    for ((key, value) in b) if (a[key] == value) a.remove(key)
}

/**
 * Простая
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> = a.toSet().intersect(b.toSet()).toList()

/**
 * Средняя
 *
 * Для заданного набора символов определить, можно ли составить из него
 * указанное слово (регистр символов игнорируется)
 *
 * Например:
 *   canBuildFrom(listOf('a', 'b', 'o'), "baobab") -> true
 */
fun canBuildFrom(chars: List<Char>, word: String): Boolean {
    val setNow = chars.toSet()
    for (element in word) if (element !in setNow) return false
    return true
}

/**
 * Средняя
 *
 * Найти в заданном списке повторяющиеся элементы и вернуть
 * ассоциативный массив с информацией о числе повторений
 * для каждого повторяющегося элемента.
 * Если элемент встречается только один раз, включать его в результат
 * не следует.
 *
 * Например:
 *   extractRepeats(listOf("a", "b", "a")) -> mapOf("a" to 2)
 */
fun extractRepeats(list: List<String>): Map<String, Int> {
    val copyList = list.toMutableList()
    val monoList = list.toSet().toList()
    val resultMap: MutableMap<String, Int> = mutableMapOf()
    for (elem in monoList) copyList.remove(elem)
    for (elem in copyList) {
        var temp = 0
        for (element in list) if (elem == element) temp++
        resultMap += elem to temp
    }
    return resultMap
}

/**
 * Средняя
 *
 * Для заданного списка слов определить, содержит ли он анаграммы
 * (два слова являются анаграммами, если одно можно составить из второго)
 *
 * Например:
 *   hasAnagrams(listOf("тор", "свет", "рот")) -> true
 */
fun hasAnagrams(words: List<String>): Boolean {
    val tempMap: MutableMap<List<Char>, String> = mutableMapOf()
    for (elem in words) if (elem.toList().sorted() in tempMap) return true
    else tempMap += elem.toList().sorted() to elem
    return false
}

/**
 * Сложная
 *
 * Для заданного списка неотрицательных чисел и числа определить,
 * есть ли в списке пара чисел таких, что их сумма равна заданному числу.
 * Если да, верните их индексы в виде Pair<Int, Int>;
 * если нет, верните пару Pair(-1, -1).
 *
 * Индексы в результате должны следовать в порядке (меньший, больший).
 *
 * Постарайтесь сделать ваше решение как можно более эффективным,
 * используя то, что вы узнали в данном уроке.
 *
 * Например:
 *   findSumOfTwo(listOf(1, 2, 3), 4) -> Pair(0, 2)
 *   findSumOfTwo(listOf(1, 2, 3), 6) -> Pair(-1, -1)
 */
fun findSumOfTwo(list: List<Int>, number: Int): Pair<Int, Int> {
    val copyList = list.toMutableList()
    for (elem in copyList) {
        val tempList = copyList - elem
        if (number - elem in tempList) return list.indexOf(elem) to tempList.indexOf(number - elem) + 1
    }
    return -1 to -1
}

/**
 * Очень сложная
 *
 * Входными данными является ассоциативный массив
 * "название сокровища"-"пара (вес сокровища, цена сокровища)"
 * и вместимость вашего рюкзака.
 * Необходимо вернуть множество сокровищ с максимальной суммарной стоимостью,
 * которые вы можете унести в рюкзаке.
 *
 * Например:
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     850
 *   ) -> setOf("Кубок")
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     450
 *   ) -> emptySet()
 */
fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> {
    val copyTrs = treasures.toMutableMap()
    var resList: List<String> = mutableListOf()
    do {
        val removeList = mutableListOf<String>()
        for ((first, second) in copyTrs) if (second.first > capacity) removeList.add(first)
        copyTrs -= removeList
        var capAll = 0
        for (el in copyTrs) capAll += el.value.first
        val loseList = mutableListOf<String>()
        while (capAll > capacity) {
            var mNow = Int.MAX_VALUE
            var pNow = Int.MIN_VALUE
            for ((name, prm) in copyTrs) when {
                prm.second < pNow -> pNow = prm.second
                prm.second == pNow && prm.first > mNow -> mNow = prm.first
                prm.first == mNow && prm.second == pNow -> {
                    loseList.add(name)
                    capAll - mNow
                    mNow = Int.MAX_VALUE
                    pNow = Int.MIN_VALUE
                }
            }
        }
        val list2names = mutableListOf<String>()
        for ((key) in copyTrs) list2names.add(key)
        resList += list2names - loseList
        copyTrs -= resList
    } while (copyTrs.isNotEmpty())
    return resList.toSet()
}

//val copyTrs = treasures.toMutableMap()
//var resList: List<String> = mutableListOf()
//do {
//    val removeList = mutableListOf<String>()
//    for ((first, second) in copyTrs) if (second.first > capacity) removeList.add(first)
//    copyTrs -= removeList
//    var capAll = 0
//    for ((name, param) in copyTrs) capAll += param.first
//    val loseList = mutableListOf<String>()
//    while (capAll > capacity) {
//        var mNow = Int.MAX_VALUE
//        var pNow = Int.MIN_VALUE
//        for ((name, prm) in copyTrs) if (prm.second < pNow) pNow = prm.second
//        for ((name, prm) in copyTrs) if (prm.second == pNow && prm.first > mNow) mNow = prm.first
//        for ((name, prm) in copyTrs) if (prm.first == mNow && prm.second == pNow) {
//            loseList.add(name)
//            capAll - mNow
//            mNow = Int.MAX_VALUE
//            pNow = Int.MIN_VALUE
//        }
//    }
//    val list2names = mutableListOf<String>()
//    for ((key) in copyTrs) list2names.add(key)
//    resList += list2names - loseList
//    copyTrs -= resList
//} while (copyTrs.isNotEmpty())
//return resList.toSet()

//fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> {
//    val list2 = treasures.toMutableMap()
//    var resList: List<String> = mutableListOf()
//    val capacityNow = capacity
//    do {
//        val removeList = mutableListOf<String>()
//        for ((first, second) in list2) if (second.first > capacity) removeList.add(first)
//        list2 -= removeList
//        removeList.clear()
//        var capAll = 0
//        for ((name, param) in list2) capAll += param.first
//        val loseList = mutableListOf<String>()
//        while (capAll > capacityNow) {
//            var mNow = Int.MAX_VALUE
//            var pNow = Int.MIN_VALUE
//            for ((name, param) in list2) if (param.second < pNow) pNow = param.second
//            for ((name, param) in list2) if (param.second == pNow && param.first > mNow) mNow = param.first
//            for ((name, param) in list2) if (param.first == mNow && param.second == pNow) {
//                loseList.add(name)
//                capAll - mNow
//                mNow = Int.MAX_VALUE
//                pNow = Int.MIN_VALUE
//            }
//        }
//        val list2names = mutableListOf<String>()
//        for ((key) in list2) list2names.add(key)
//        resList += list2names - loseList
//        list2 -= resList
//    } while (list2.isNotEmpty())
//    return resList.toSet()
//
//}
