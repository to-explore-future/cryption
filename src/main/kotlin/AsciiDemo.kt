import java.lang.StringBuilder

fun main(args: Array<String>) {

    val str = "I Love You!"
    val charArray = str.toCharArray()
    val result = with(StringBuilder()) {
        for (char in charArray) {
            val toInt = char.toInt()
            append("$toInt ")
        }
        toString()
    }
    println(result)
}