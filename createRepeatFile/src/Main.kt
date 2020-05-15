import java.io.File

fun main(args: Array<String>) {
    if (args.size < 2) {
        println("Usage: templateFile repeatNumber/replaceFile")
        return
    }
    val templatePath = args[0]

    var repeatNumber = 0
    var isFileMode = false
    try {
        repeatNumber = args[1].toInt()
    } catch (e: NumberFormatException) {
        isFileMode = true
    }
    if (isFileMode) {
        createRepeatFile(File(templatePath), args[1]);
    } else {
        createRepeatFile(File(templatePath), repeatNumber);
    }
}

fun createRepeatFile(templateFile: File, repeatNumber: Int) {}
fun createRepeatFile(templateFile: File, valuesFile: String) {
    val templateStr = templateFile.readText()
    val pattern = """\$\{.*}"""
    val findResultList = Regex(pattern).findAll(templateStr).toList()

    val values = File(valuesFile).readLines().map { value ->
        var resultStr = templateStr
        findResultList.forEach {
            val valueArray = value.split(',')
            val toBeReplace = it.value
            val index = toBeReplace.substring(2, toBeReplace.length - 1).toInt()
            resultStr = resultStr.replace(toBeReplace, valueArray[index])
        }
        resultStr
    }

    save(templateFile, values)
}

fun save(orgFile: File, values: List<String>) {
    val newFilePath = orgFile.parent + "/" + orgFile.nameWithoutExtension + "_new." + orgFile.extension
    val newFile = File(newFilePath)
    values.forEach { newFile.appendText(it) }
}



