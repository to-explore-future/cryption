import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class AEScryption {

    //private val password = "1234567812345678"// 注意密码长度要求 128 byte
    //这个ide默认编码是 utf-8 就是这个password不管你是什么字符，最终占16字节就对了
    private val password = "我是中国人b"
    private val input = "今天晚上九点老地方见面"
    //加密方式
    private val DES = "DES"
    private val AES = "AES"
    //工作模式
    private val CBC = "CBC"
    private val ECB = "ECB"

    //填充方式 NoPadding / PKCS5Padding
    private val NoPadding = "NoPadding"
    private val PKCS5Padding = "PKCS5Padding"

    fun run() {

        val encryptionResult = encryption(input.toByteArray(), password.toByteArray())
        println("加密后的结果${String(encryptionResult)}")

        val decryptionResult = decryption(encryptionResult, password.toByteArray())
        println("解密后的结果:${String(decryptionResult)}")

    }

    private fun encryption(input: ByteArray, password: ByteArray): ByteArray {
        val transformation = "$AES"
        val cipher = Cipher.getInstance("AES")
        val keySpec = SecretKeySpec(password, "AES")
        cipher.init(Cipher.ENCRYPT_MODE, keySpec)
        return cipher.doFinal(input)
    }

    private fun decryption(input: ByteArray, password: ByteArray): ByteArray {
        val cipher = Cipher.getInstance("AES")
        val keySpec = SecretKeySpec(password, "AES")
        cipher.init(Cipher.DECRYPT_MODE, keySpec)
        return cipher.doFinal(input)
    }

}