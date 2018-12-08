import java.lang.StringBuilder

fun main(args: Array<String>) {

    char2ASCII()

    CaeserCryption().run()

    DESCryption().run()

    AEScryption().run()

    RSAcryption().encryption()

    Digest().run()

    SignatureUtil().run()

}

private fun char2ASCII() {
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