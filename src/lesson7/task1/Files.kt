@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import java.io.File
import kotlin.math.max

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                } else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val resMap = mutableMapOf<String, Int>()
    val line = File(inputName).readLines().joinToString("\n") { it.toLowerCase() }
    val muteSubs = substrings.toMutableList()
    var res = 0
    var indexStart = 0
    while (muteSubs.isNotEmpty()) {
        val temp = Regex(muteSubs[0].toLowerCase()).find(line, indexStart)
        if (temp == null) {
            resMap[muteSubs[0]] = res
            indexStart = 0
            res = 0
            muteSubs.removeAt(0)
        } else {
            res++
            indexStart = temp.range.first + 1
        }
    }
    return resMap
}


/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    val wrongs = listOf("Ж", "Ч", "Ш", "Щ").flatMap { listOf(it, it.toLowerCase()) }
    val rules = listOf("Ы" to "И", "Я" to "А", "Ю" to "У")
            .flatMap { (f, s) -> listOf(f to s, f.toLowerCase() to s.toLowerCase()) }
    val outputStream = File(outputName).bufferedWriter()
    val lines = File(inputName).readLines()
    val linesCount = lines.lastIndex
    lines.withIndex().forEach {
        var resStr = it.value
        for (firstCh in wrongs)
            for ((err, corr) in rules) resStr = resStr.replace("$firstCh$err", "$firstCh$corr")
        outputStream.write(resStr)
        if (it.index < linesCount) outputStream.newLine()
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    val lines = File(inputName).readLines().map { it.trim() }
    val writer = File(outputName).bufferedWriter()
    if (lines.isNotEmpty()) {
        val maxL = lines.maxBy { it.length }!!.length
        val linesCount = lines.size
        lines.withIndex().forEach {
            writer.write(" ".repeat((maxL - it.value.trim().length) / 2))
            writer.write(it.value.trim())
            if (it.index < linesCount) writer.newLine()
        }
    }
    writer.close()
}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    val lines = File(inputName).readLines()
    val writer = File(outputName).bufferedWriter()
    if (lines.isNotEmpty()) {
        val maxL = lines
                .maxBy { it.trim().length }!!
                .trim()
                .split(Regex("""\s+"""))
                .fold(0) { total, next -> total + next.length + 1 } - 1 // max кол-во символов в строке
        var strCount = 0
        for (parStr in lines) {
            val words = parStr.trim().split(Regex("""\s+"""))
            if (parStr.isBlank() || words.size == 1)
                writer.write(parStr.replace(Regex("""\s+"""), ""))
            else {
                val strList = mutableListOf<String>()
                var needS = maxL - words.fold(0) { total, next -> total + next.length }
                val countS = needS / (words.size - 1)
                needS -= countS * (words.size - 1)
                for (word in words) {
                    strList.add(word)
                    var plus = 0
                    if (needS > 0) {
                        plus++
                        needS--
                    }
                    if (strList.size < words.size * 2 - 1)
                        strList.add(" ".repeat(countS + plus))
                }
                writer.write(strList.joinToString(""))
            }
            strCount++
            if (strCount < lines.size) writer.newLine()
        }
    }
    writer.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    val lines = File(inputName).readLines()
    val resMap = mutableMapOf<String, Int>()
    for (str in lines) {
        val tempMap = mutableMapOf<String, Int>()
        val allWords = Regex("""[а-яА-ЯёЁa-zA-Z]+""")
                .findAll(str.toLowerCase().trim())
                .map { it.value }
                .toList()
        val words = allWords
                .toSet()
                .toList()
        for (word in words) tempMap[word] = allWords.count { it == word }
        tempMap.forEach { word, count -> resMap[word] = resMap.getOrDefault(word, 0) + count }
    }
    return resMap.toList().sortedBy { -it.second }.take(20).toMap()
}

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    val newDictionary = dictionary
            .map { it.key.toLowerCase() to it.value.toLowerCase() }
            .toMap() + dictionary
            .map { it.key.toUpperCase() to it.value.toLowerCase().capitalize() }
            .filter { it.first != it.first.toLowerCase() }
            .toMap()
    val reader = File(inputName).reader()
    val writer = File(outputName).writer()
    var charNow = reader.read()
    while (charNow != -1) {
        writer.write(newDictionary.getOrDefault(charNow.toChar(), charNow.toChar().toString()).toCharArray())
        charNow = reader.read()
    }
    writer.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    val words = File(inputName)
            .readLines()
            .filter {
                val check = it.toLowerCase().toCharArray()
                check.size == check.toSet().size
            }
    if (words.isNotEmpty()) {
        val maxLenght = words.maxBy { it.length }!!.length
        val outWords = words.filter { it.length == maxLenght }
        writer.write(outWords.joinToString())
    }
    writer.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    TODO()
}
//{
//    val writer = File(outputName).bufferedWriter()
//    var reader = File(inputName).reader()
//    var str = mutableListOf<Char>()
//    var charNow = reader.read()
//    while (charNow != -1) {
//        str.add(charNow.toChar())
//        charNow = reader.read()
//    }
//    var b = str.joinToString("")
//    b = b.replace(Regex("""\n\n"""), "</p><p>")
//    while (Regex("""\*\*(.|\n)*\*\*""").containsMatchIn(b)) {
//        var nextTag = "<b>"
//        repeat(2) {
//            val tempRange = Regex("""\*\*""").findAll(b).first().range
//            b = b.replaceRange(tempRange, nextTag)
//            nextTag = nextTag.replace("<", "</")
//        }
//    }
//    while (Regex("""\*(.|\n)*\*""").containsMatchIn(b)) {
//        var nextTag = "<i>"
//        repeat(2) {
//            val tempRange = Regex("""\*""").findAll(b).first().range
//            b = b.replaceRange(tempRange, nextTag)
//            nextTag = nextTag.replace("<", "</")
//        }
//    }
//    while (Regex("""~~(.|\n)*~~""").containsMatchIn(b)) {
//        var nextTag = "<s>"
//        repeat(2) {
//            val tempRange = Regex("""~~""").findAll(b).first().range
//            b = b.replaceRange(tempRange, nextTag)
//            nextTag = nextTag.replace("<", "</")
//        }
//    }
//    val extraLine = mutableListOf("<html><body>")
//    if (b.isNotEmpty()) {
//        extraLine.addAll(listOf("<p>", b, "</p>", "</body></html>"))
//    } else extraLine.add("</body></html>")
//    writer.write(extraLine.joinToString(""))
//    writer.close()
//}



//    writer.write("<html><body>")
//    var extraLine = mutableListOf<String>()
//    if (lines.isNotEmpty()) {
//        var popen = false
//        var bopen = false
//        var iopen = false
//        var sopen = false
//        var nopen = false
//        for (line in lines) {
//            var resLine = line.replace(Regex("""\s+"""), "")
//            val lineP = Regex("""\s*""").matches(resLine)
//            if (nopen && !lineP) extraLine.add("\n")
//            if (!lineP) nopen = true
//            if (popen == lineP) {
//                extraLine.add(if (popen) "</p>" else "<p>")
//                popen = !popen
//            }
//            if (lineP) continue
//            fun correct(op: Boolean, reg: Regex, rep: String, func: (Boolean) -> (Unit)) {
//                var x = op
//                while (reg.containsMatchIn(resLine)) {
//                    val tempRange = reg.find(resLine)!!.range
//                    val nextTag = if (x) rep.replace("<", "</") else rep
//                    resLine = resLine.replaceRange(tempRange, nextTag)
//                    x = !x
//                }
//                func(x)
//            }
////            while (Regex("""\*\*""").containsMatchIn(resLine)) {
////                val tempRange = Regex("""\*\*""").find(resLine)!!.range
////                val nextTag = if (bopen) "</b>" else "<b>"
////                resLine = resLine.replaceRange(tempRange, nextTag)
////                bopen = !bopen
////            }
////            while (Regex("""\*""").containsMatchIn(resLine)) {
////                val tempRange = Regex("""\*""").find(resLine)!!.range
////                val nextTag = if (iopen) "</i>" else "<i>"
////                resLine = resLine.replaceRange(tempRange, nextTag)
////                iopen = !iopen
////            }
////            while (Regex("""~~""").containsMatchIn(resLine)) {
////                val tempRange = Regex("""~~""").find(resLine)!!.range
////                val nextTag = if (sopen) "</s>" else "<s>"
////                resLine = resLine.replaceRange(tempRange, nextTag)
////                sopen = !sopen
////            }
//            correct(bopen, Regex("""\*\*"""), "<b>") { bopen = it }
//            correct(iopen, Regex("""\*"""), "<i>") { iopen = it }
//            correct(sopen, Regex("""~~"""), "<s>") { sopen = it }
//            extraLine.add(resLine)
//        }
//        if (popen) extraLine.add("</p>")
//    }
//    writer.write(extraLine.joinToString(""))
//    writer.write("</body></html>")
//    writer.close()
//}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>
Или колбаса
</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>
Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ul>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    TODO()
}

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    var rhvNow = rhv
    val steps = mutableListOf<Int>()
    val strSteps = mutableListOf<String>()
    var sCount = 0
    steps.addAll(listOf(lhv, rhv))
    do {
        steps.add(lhv * (rhvNow % 10))
        rhvNow /= 10
    } while (rhvNow != 0)
    steps.add(rhv * lhv)
    val maxL = steps.last().toString().length
    for (i in 0 until steps.size) {
        val temp = steps[i].toString()
        val lNow = temp.length
        sCount = if (i in 0..2 || i == steps.lastIndex) maxL - lNow
        else strSteps[i - 1].length - steps[i].toString().length - 2
        strSteps.add(
                listOf(
                        when (i) {
                            1 -> "*"
                            in 0..2, steps.lastIndex -> " "
                            else -> "+"
                        },
                        " ".repeat(sCount),
                        temp
                ).joinToString("")
        )
    }
    val line = "-".repeat(maxL + 1)
    for (i in 0..strSteps.lastIndex) {
        writer.write(strSteps[i])
        writer.newLine()
        if (i == 1 || i == strSteps.lastIndex - 1) {
            writer.write(line)
            writer.newLine()
        }
    }
    writer.close()
}


/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    val listSteps = lhv.toString().toList().map { it.toString().toInt() }.toMutableList()
    val writer = File(outputName).bufferedWriter()
    var digNow = 0
    var sCount = 1
    var isFirst = true
    while (digNow < rhv && listSteps.isNotEmpty()) {
        digNow = digNow * 10 + listSteps[0]
        listSteps.removeAt(0)
    }
    writer.write(" $lhv | $rhv")
    val rep = lhv.toString().length - digNow.toString().length + 1
    var dCount = 0
    repeat(rep) {
        var ost = digNow % rhv
        if (isFirst) dCount = digNow.toString().length
        val minus = digNow - ost
        sCount += dCount - minus.toString().length - 1
        writer.newLine()
        writer.write(" ".repeat(sCount))
        writer.write("-$minus")
        println(sCount)
        val temp = if (isFirst) sCount + minus.toString().length + 1
        else max(minus.toString().length + 1, digNow.toString().length)
        println(temp)
        if (isFirst) {
            writer.write(" ".repeat(lhv.toString().length + 3 - minus.toString().length - sCount))
            writer.write("${lhv / rhv}")
        }
        writer.newLine()
        if (!isFirst) writer.write(" ".repeat(1 + sCount + minus.toString().length - temp))
        writer.write("-".repeat(temp))
        var ostStr = ost.toString()
        sCount += minus.toString().length - ostStr.length + 1
        if (listSteps.isNotEmpty()) {
            ost = ost * 10 + listSteps[0]
            ostStr += listSteps[0].toString()
            listSteps.removeAt(0)
        }
        writer.newLine()
        writer.write(" ".repeat(sCount))
        writer.write(ostStr)
        digNow = ost
        dCount = ostStr.length
        isFirst = false
    }
    writer.close()
}


fun main(args: Array<String>) {
    printDivisionProcess(173128, 91295, "C:\\Users\\AS\\Desktop\\out\\wtf.txt")
    val lhv = 173128
    val rhv = 91295
    val outputName = "C:\\Users\\AS\\Desktop\\out\\wtf2.txt"
    val listSteps = lhv.toString().toList().map { it.toString().toInt() }.toMutableList()
    val writer = File(outputName).bufferedWriter()
    var digNow = 0
    var sCount = 1
    var isFirst = true
    while (digNow < rhv && listSteps.isNotEmpty()) {
        digNow = digNow * 10 + listSteps[0]
        listSteps.removeAt(0)
    }
    val rep = lhv.toString().length - digNow.toString().length + 1
    var dCount = 0
    repeat(rep) {
        var ost = digNow % rhv
        if (isFirst) dCount = digNow.toString().length
        val minus = digNow - ost
        if (isFirst && digNow.toString().length > minus.toString().length) {
            sCount = 0
        }
        if (isFirst) {
            writer.write(" ".repeat(sCount))
            writer.write("$lhv | $rhv")
        }
        val tempS = sCount
        sCount += dCount - minus.toString().length - 1
        writer.newLine()
        writer.write(" ".repeat(sCount))
        writer.write("-$minus")
        println(sCount)
        val temp = if (isFirst) sCount + minus.toString().length + 1
        else max(minus.toString().length + 1, digNow.toString().length)
        println(temp)
        if (isFirst) {
            writer.write(" ".repeat(lhv.toString().length + 2 - minus.toString().length - sCount + tempS))
            writer.write("${lhv / rhv}")
        }
        writer.newLine()
        if (!isFirst) writer.write(" ".repeat(1 + sCount + minus.toString().length - temp))
        writer.write("-".repeat(temp))
        var ostStr = ost.toString()
        sCount += minus.toString().length - ostStr.length + 1
        if (listSteps.isNotEmpty()) {
            ost = ost * 10 + listSteps[0]
            ostStr += listSteps[0].toString()
            listSteps.removeAt(0)
        }
        writer.newLine()
        writer.write(" ".repeat(sCount))
        writer.write(ostStr)
        digNow = ost
        dCount = ostStr.length
        isFirst = false
    }
    writer.close()
}

