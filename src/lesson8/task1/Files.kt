@file:Suppress("UNUSED_PARAMETER")
package lesson8.task1

import java.io.File
import java.lang.Math.ceil

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
                }
                else {
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
    val map= mutableMapOf<String,Int>()
    val inputName1=File(inputName).readText().toLowerCase()
    var index:Int
    var sum:Int
    for (i in 0 until substrings.size) {
        index=inputName1.indexOf(substrings[i].toLowerCase(),0)
        sum=0
        while (index!=-1) {
            index=inputName1.indexOf(substrings[i].toLowerCase(),index+1)
            sum+=1
        }
        map.put(substrings[i],sum)
    }
    return map
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
    val output=File(outputName).bufferedWriter()
    val map= mutableMapOf("ы" to "и","Ы" to "И","я" to "а","Я" to "А","ю" to "у","Ю" to "У")
    var i=-1
    val inputName1=File(inputName).readText()
    if (inputName1.isEmpty()) output.write("") else {
        i+=1
        while (i < inputName1.length - 1) {
            output.write(inputName1[i].toString())
            if ((inputName1[i + 1] in "ыяюЫЯЮ") && (inputName1[i] in "жчшщЖЧШЩ")) {
                output.write(map[inputName1[i + 1].toString()])
                i += 1
            }
            i += 1
        }
    }
    if (i!=-1)
        if ((inputName1[i] !in "ыяюЫЯЮ") || (inputName1[i-1] !in "жчшщЖЧШЩ")) output.write(inputName1[i].toString())
    output.close()
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
    var space:Int
    val output=File(outputName).bufferedWriter()
    var max=-1
    for (line in File(inputName).readLines())
        if (line.trim().length>max) max=line.trim().length
    max/=2
    for (line in File(inputName).readLines()) {
            space=max-line.trim().length/2
            if (space+line.trim().length>255) space=255-line.trim().length
            for (i in 0 until space) output.write(" ")
        output.write(line.trim())
        output.newLine()
    }
    output.close()
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
fun alignFileByWidth(inputName: String, outputName: String)
{
    var sum:Int
    val output=File(outputName).bufferedWriter()
    var max=-1
    for (line in File(inputName).readLines())
        if (line.trim().length>max) max=line.trim().length
    for (line in File(inputName).readLines())
        if (line.trim().length==max) {
            output.write(line.trim())
            output.newLine()
        }else {
            sum=0
            val line1= line.trim().split(' ') as MutableList<String>
            for (i in 0 until line1.size)
                if (line1.size>i)
                    if (line1[i].isEmpty()) line1.removeAt(i)

            for (i in 0 until line1.size) sum+=line1[i].length

            if (line1.size==1) output.write(line1[0]) else {
                val space = ceil((max - sum) / (line1.size - 1).toDouble()).toInt()    //количество пробелов
                val space1 = line1.size-(space*(line1.size-1)-(max - sum))-1
                for (i in 0 until line1.size)
                    if (i < space1) {
                    output.write(line1[i])
                    if (i != line1.size - 1)
                        for (j in 0 until space) output.write(" ")
                } else {
                    output.write(line1[i])
                    if (i != line1.size - 1)
                        for (j in 0 until space-1) output.write(" ")
                }
            }
            output.newLine()
        }
    output.close()
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
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    val map= mutableMapOf<String,Int>()
    val inputName1=File(inputName).readText().toLowerCase()
    var sum:Int

    val string = Regex("""[а-яa-zё]+""").findAll(inputName1) //нахожу все слова
    val word= mutableListOf<String>()
    for (str in string) word.add(str.value)
    for (i in 0 until word.size)
        if (word[i] !in map) {
            sum=0
            for(j in 0 until word.size)
                if (word[i]==word[j]) sum+=1
            map.put(word[i],sum)
        }
    //return map.toSortedMap(Comparator<Value>() )
    val list = map.toList().sortedByDescending { it.second }
    return if (list.size<=20) list.toMap() else list.take(20).toMap()
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
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    val output=File(outputName).bufferedWriter()
    val inputName1 =File(inputName).readText()
    val dictionary1 = mutableMapOf<Char,String>()

    for (i in 0 until dictionary.size) //перевожу map в нижний регистер
    dictionary1.put(dictionary.keys.toList()[i].toLowerCase(),dictionary.values.toList()[i].toLowerCase())

    for (i in 0 until inputName1.toLowerCase().length)
        if (inputName1.toLowerCase()[i] in dictionary1) {
            if (inputName1.toLowerCase()[i] == inputName1[i]) output.write(dictionary1[inputName1.toLowerCase()[i]])
            else { //ниже (если во входном файле буква большая то после ствалю тоже большую букву)
                if (dictionary1[inputName1.toLowerCase()[i]].toString().isNotEmpty())
                output.write(dictionary1[inputName1.toLowerCase()[i]].toString().toUpperCase()[0].toString())
                else output.write("")
                for (j in 1 until dictionary1[inputName1.toLowerCase()[i]].toString().length)
                    output.write(dictionary1[inputName1.toLowerCase()[i]].toString()[j].toString())
            }
        } else output.write(inputName1[i].toString())
    output.close()
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
    val output=File(outputName).bufferedWriter()
    val different= mutableListOf<Boolean>()
    var point:Boolean
    var max=-1

    for (line in File(inputName).readLines()) {
        val line1=line.toLowerCase()
        point=true
        for (i in 0 until line1.length) {
            for (j in i + 1 until line1.length) {
                if (line1[i] == line1[j]) point = false
                if (!point) break
            }
        }
        if ((point) && (max<line1.length)) max=line1.length
        different.add(point)                         //на позиции слова(его номере в строке) стоит true-если все буквы в слове различны
    }
    point=true
    var j=0
    for (line in File(inputName).readLines()) {
        if ((max==line.length) && (different[j])) if (point) {
            output.write(line)
            point = false
        } else {
            output.write(", ")
            output.write(line)
        }
        j+=1
    }
    output.close()
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
    var k:Int
    val output=File(outputName).bufferedWriter()
    if (File(inputName).readText().isEmpty()) {//это я исправлял очень странный random test
        output.write("<html><body><p></p></body></html>")
        output.close()
    } else {
        var pointi = 0
        var pointb = 0
        var points = 0
        var space = 12
        var str1: String
        var str2: String
        var str3: String
        //вывод начала
        output.write("""<html>
                <body>
                        <p>

        """.trimMargin())
        space += 12

        for (line in File(inputName).readLines()) {
            if (line.isEmpty()) {
                output.write("        ")
                output.write("</p>")
                output.newLine()
                output.write("        ")
                output.write("<p>")
                output.newLine()
            } else {
                for (i in 0 until space) output.write(" ")
                k = 0

                for (i in k until line.length) if ((line[i] != '*') && (line[i] != '~')) {
                    output.write(line[i].toString());k += 1
                } else {
                    str2 = "";str3 = ""
                    if (k + 2 < line.length) str3 = "" + line[k] + line[k + 1] + line[k + 2]
                    if (k + 1 < line.length) str2 = "" + line[k] + line[k + 1]
                    str1 = line[k].toString()
                    when {
                        (str3 == "***") && (pointb == 0) -> {
                            output.write("<b><i>")
                            pointb = 1
                            pointi = 1
                            k += 3
                        }
                        (str3 == "***") && (pointb == 1) -> {
                            output.write("</b></i>")
                            pointb = 0
                            pointi = 0
                            k += 3
                        }
                        (str2 == "~~") && (points == 0) -> {
                            output.write("<s>")
                            points = 1
                            k += 2
                        }
                        (str2 == "~~") && (points == 1) -> {
                            output.write("</s>")
                            points = 0
                            k += 2
                        }
                        (str2 == "**") && (pointb == 0) -> {
                            output.write("<b>")
                            pointb = 1
                            k += 2
                        }
                        (str2 == "**") && (pointb == 1) -> {
                            output.write("</b>")
                            pointb = 0
                            k += 2
                        }
                        (str1 == "*") && (pointi == 0) -> {
                            output.write("<i>")
                            pointi = 1
                            k += 1
                        }
                        (str1 == "*") && (pointi == 1) -> {
                            output.write("</i>")
                            pointi = 0
                            k += 1
                        }
                    }
                }
                output.newLine()
            }
        }//вывод конца
        output.write("""        </p>
                </body>
                </html>
        """.trimMargin())
        output.close()
    }
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой ст``роки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
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
  /**  var pointul=0;var pointol=0
    val output=File(outputName).bufferedWriter()
    output.write("<html>")
    output.newLine()
    output.write("    ")
    output.write("<body>")
    output.newLine()
    var space=4

    for (line in File(inputName).readLines()) {
        if ((line.trim()[0]=='*') && (pointul==0)) {
            for (i in 0 until space) output.write(" ")
            output.write("<ul>");pointul = 1;space += 2
        } else
            if ((line.trim()[0]=='*') && (pointul==1)) {
                for (i in 0 until space) output.write(" ")
                output.write("</ul>");pointul = 0;space -= 2
            }
    }

}*/

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
    val output=File(outputName).bufferedWriter()
    var space=lhv.toString().length+rhv.toString().length
    val list= mutableListOf<Int>()
    var rhv1=rhv
    while (rhv1>0) {list.add(rhv1%10);rhv1/=10}
    val max=(list[list.size-1]*lhv).toString().length

    if (space<max+rhv.toString().length) space=max+rhv.toString().length
    for (i in 0 until space-lhv.toString().length) output.write(" ")
    output.write(lhv.toString())
    output.newLine()
    output.write("*")
    for (i in 0 until space-rhv.toString().length-1) output.write(" ")
    output.write(rhv.toString())
    output.newLine()
    for (i in 0 until space) output.write("-")
    output.newLine()
            //выше был вывод до первых черточек
    for (j in 0 until rhv.toString().length) {
        val number=list[j]*lhv
        if (j==0) {
            for (i in 0 until space-number.toString().length) output.write(" ")
            output.write(number.toString())
            output.newLine()
        } else {
            output.write("+")
            for (i in 0 until space-number.toString().length-1-j) output.write(" ")
            output.write(number.toString())
            output.newLine()
        }
    } //выше вывод до вторых черточек
    for (i in 0 until space) output.write("-")
    output.newLine()
    val numb=lhv*rhv
    for (i in 0 until space-numb.toString().length) output.write(" ")
    output.write(numb.toString())
    output.close()
}


/**
 * Очень сложная
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
    TODO()
}

