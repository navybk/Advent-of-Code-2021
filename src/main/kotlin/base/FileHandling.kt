package base

class FileHandling(private val filename: String) {


    fun <T> iterate(translate: (String) -> T, function: (T) -> Unit) {
        val stream = bufferedReader
        var line = stream.readLine()
        while (line != null) {
            function(translate(line))
            line = stream.readLine()
        }
    }

    fun iterate(function: (String) -> Unit) {
        val stream = bufferedReader
        var line = stream.readLine()
        while (line != null) {
            function(line)
            line = stream.readLine()
        }
    }

    fun <T : Any> map(translate: (String) -> T?): List<T> =
        getStringList()
            .asSequence()
            .mapNotNull {
                translate(it.trim())
            }
            .toList()

    fun getStringList(): List<String> =
        bufferedReader.readLines()

    private val bufferedReader
        get() = FileHandling::class.java
            .getResourceAsStream(filename)!!
            .bufferedReader()
}