import java.lang.StringBuilder

/**
 * 凯撒加密的原理：
 * 每个字符都有唯一对应的ASCII码，将每个字符都移动指定的距离实现加密
 */
class CaeserCryption {

    private val content = "I Love You"
    private val key = 2

    fun run() {
        val encryptionResult = encryption(content, key)
        println("\"$content\"加密后：\"$encryptionResult\"")

        val decryptionResult = decryption(encryptionResult, key)
        println("\"$encryptionResult\"解密后：\"$decryptionResult\"")

    }


    private fun encryption(content: String, key: Int): String {
        return with(StringBuilder()) {
            content.toCharArray().forEach {
                append((it.toInt() + key).toChar())
            }
            toString()
        }
    }

    private fun decryption(content: String, key: Int): String {
        return with(StringBuilder()) {
            content.toCharArray().forEach {
                append((it.toInt() - key).toChar())
            }
            toString()
        }
    }

}