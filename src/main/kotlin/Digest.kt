import java.lang.StringBuilder
import java.security.MessageDigest

class Digest {

    private val message = "黑马程序员"

    fun run() {
        println("$message: MD5:\t\t" + getMD5())
        println("$message: SHA-1:\t" + getSHA1())
        println("$message: SHA-256:\t" + getSHA256())
    }

    private fun getMD5(): String {
        val messageDigest = MessageDigest.getInstance("MD5")
        val digest = messageDigest.digest(message.toByteArray())
        return toHex(digest)
    }

    private fun getSHA1(): String {
        val messageDigest = MessageDigest.getInstance("SHA-1")
        val digest = messageDigest.digest(message.toByteArray())
        return toHex(digest)
    }

    private fun getSHA256(): String {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val digest = messageDigest.digest(message.toByteArray())
        return toHex(digest)
    }

    private fun toHex(digest: ByteArray): String {
        return with(StringBuilder()) {
            digest.forEach {
                val result = it.toInt() and 0xFF
                val hexStr = Integer.toHexString(result)
                if (hexStr.length == 1) {
                    append("0").append(hexStr)
                } else {
                    append(hexStr)
                }
            }
            toString()
        }
    }
}