@file:Suppress("UNUSED_PARAMETER")
package lesson5.task1

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main(args: Array<String>) {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        }
        else {
            println("Прошло секунд с начала суток: $seconds")
        }
    }
    else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку
 */
fun dateStrToDigit(str: String): String {
    val mou = listOf("января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября",
            "октября", "ноября", "декабря")
    return try {
        val s = str.split(" ")
        if (s.size > 2) {
            val day = s[0].toInt()
            val mo = mou.indexOf(s[1]) + 1
            val year = s[2].toInt()
            if (mo == 0) "" else
                String.format("%02d.%02d.%d", day, mo, year)
        } else ""
    } catch (e:NumberFormatException) {
        ""
    }
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 */
fun dateDigitToStr(digital: String): String {
    val mou = listOf("января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября",
            "октября", "ноября", "декабря")
    return try {
        val s = digital.split(".")
        if (s.size>2) {
            if ((s.size == 3) and (s[1].toInt() != 0)) {
                val day = s[0].toInt()
                val mo = mou[s[1].toInt() - 1]
                val year = s[2].toInt()
                String.format("%d %s %d", day, mo, year)
            } else ""
        } else ""
    } catch (e: NumberFormatException) {
        ""
    }
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -98 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */
fun flattenPhoneNumber(phone: String): String {
    val rig="0123456789"
    val list= mutableListOf<Char>()
    for (i in 0 until phone.length)
        if ((phone[i]!=' ') and (phone[i]!='-') and (phone[i]!='(') and (phone[i]!=')'))
    list.add(phone[i])
    var j=0
    return if (list.size>0) {
        if ((list[0] == '+') or (list[1] in rig)) {
            j = 1
            for (i in 0 until list.size)
                if (list[i] in rig) {
                    j += 1
                }
        }
        if (j >= list.size) list.joinToString(separator = "") else
            ""
    } else ""
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
   try {
       var res = ""
       var max = 0
       var met=0
       for (i in 0 until jumps.length)
           if ((jumps[i] != '%') and (jumps[i] != '-'))
               res += jumps[i]
       val res1 = res.split(delimiters = ' ')
       for (part in res1) {
           if (part != "") {
               val numb = part.toInt()
               met=1
               if (numb > max) max = numb
           }
       }
       return if ((max == 0) and (met == 0))-1 else max
   }
   catch (e:NumberFormatException){
       return -1
   }
}


/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки вернуть -1.
 */

fun bestHighJump(jumps: String): Int {
    var res = ""
    var max = 0
    var met=0
    for (i in 0 until jumps.length) {
        if (jumps[i] != '%')
            res += jumps[i]
    }
    val res1 = res.split(' ')
    for (i in 0 until res1.size - 1) {
        if ((res[i].toInt()>=0) and (res[i+1]=='%')) met=1
        if ((res1[i] != "") and (res1[i + 1] == "+")) {
            val numb = res1[i].toInt()
            if (numb > max) max = numb
        }
    }
    return if ((max == 0) and (met == 0)) -1 else max
}


/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
   try {
       var sum = 0
       val res1 = expression.split(' ')
       sum += res1[0].toInt()
       for (i in 1 until res1.size - 1 step 2)
           if (res1[i] == "+") sum += res1[i + 1].toInt() else
               sum -= res1[i + 1].toInt()
       return sum
   } catch (e:NumberFormatException) {
       throw IllegalArgumentException("For input string: $expression")
   }
}
/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val str1 = str.toLowerCase().split(' ')
    var ri = -1
    var place = 0
    for (i in 0 until str1.size - 1)
        if ((ri == -1) and (str1[i] == str1[i + 1])) {
            ri = i
        }
    return if (ri == -1) -1 else {
        for (i in 0 until ri)
            place += str1[i].length
        place + ri
    }
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62.5; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть положительными
 */
fun mostExpensive(description: String): String {

    try {
        var max = 0.0
        var prod = ""
        var str = description.split(';')
        val r = str.joinToString(separator = " ")
        str = r.split(' ')
        if ((str.size == 1) and (str[0] == "")) return "" else
            for (i in 0 until str.size step 3)
                if (max < str[i + 1].toDouble()) {
                    max = str[i + 1].toDouble()
                    prod = str[i]
                }
        if (max==0.0) prod="Any good with price 0.0"
        return prod
    } catch (e: NumberFormatException) {
        return ""
    }
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    val rim = listOf(1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1)
    val rim1 = listOf("M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I")
    var k = 0
    var sum = 0
    var i = 1000
    var per: Int
    var per1: Int
    while (k < roman.length) {

        per = rim1.indexOf(roman[k].toString())
        if (per != -1) per = rim[per]
        if (roman.length - k == 1) {
            sum += per
            i = per
            k += 1
        }
        if (roman.length - k >= 2) {
            per1 = rim1.indexOf(roman[k].toString() + roman[k + 1].toString())
            if (per1 != -1) per1 = rim[per1]
            if ((per > per1) and (per <= i)) {
                i = per
                sum += per
                k += 1
            } else {
                i = per1
                sum += per1
                k += 2
            }
        }
    }
    return sum
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    val list= mutableListOf<Int>()
    var met=0
    for (i in 0 until cells)
        list.add(0)
   var point=Math.floor((cells/2).toDouble()).toInt()
    var limit1:Int
    var point1=0
    var point2=0
if (limit>commands.length) limit1=commands.length-1 else
    limit1=limit
    var kol=0
    for (i in 0 until limit1) {
        point1=point
        point2=i
        if (commands[i]=='+') list[point]+=1
        if (commands[i]=='-') list[point]-=1
        if (commands[i]=='>') point+=1
        if (commands[i]=='<') point-=1
        if ((commands[i]=='[') and (list[point1]==0))
            while ((commands[point2]!=']') and (kol!=0)){
                point2+=1
                if (commands[point2] == '[') kol += 1
                if (commands[point2] == ']') kol -=1
            }
    }

}
