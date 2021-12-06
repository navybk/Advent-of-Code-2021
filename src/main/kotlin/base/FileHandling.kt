package base

class FileHandling(private val filename: String) {


    fun <T> iterate(translate: (String) -> T, function: (T) -> Unit) {
        val stream = bufferedReader
        var line = stream.readLine()
        while (line != null) {
            function(translate(line))
            line = stream.readLine()
        }
        bufferedReader.close()
    }

    fun iterate(function: (String) -> Unit) {
        val stream = bufferedReader
        var line = stream.readLine()
        while (line != null) {
            function(line)
            line = stream.readLine()
        }
        bufferedReader.close()
    }

    fun <T : Any> map(translate: (String) -> T?): List<T> =
        getStringList()
            .asSequence()
            .mapNotNull {
                translate(it.trim())
            }
            .toList()

    fun getStringList(): List<String> =
        bufferedReader.run {
            readLines().also {
                close()
            }
        }

    private val bufferedReader
        get() = FileHandling::class.java
            .getResourceAsStream(filename)!!
            .bufferedReader()

}