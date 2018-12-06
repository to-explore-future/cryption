import java.security.Key
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec

/**
 * DES：data encryption standard
 * 所有的数据都是0和1，这种数据加密算法就是在0和1这种层面，按照某种
 * 加密逻辑对数据进行加密
 */
class DESCryption {

    private val input = "今天晚上九点老地方见面"
    private val password = "12345678"

    fun run() {
        val result = encryption(input.toByteArray(), password)
        println("加密结果:${String(result)}")
        val desDecryptionResult = decryption(result, password)
        //加密解密结果 要用 String() 构造方法，才能不会乱码
        println("解密结果:${String(desDecryptionResult)}")
    }

    private fun encryption(input: ByteArray, password: String): ByteArray {
        val cipher = Cipher.getInstance("DES")
        val secretKeyFactory = SecretKeyFactory.getInstance("DES")
        val keySpec = DESKeySpec(password.toByteArray())
        val key: Key = secretKeyFactory.generateSecret(keySpec)
        cipher.init(Cipher.ENCRYPT_MODE, key, SecureRandom())
        return cipher.doFinal(input)
    }

    private fun decryption(input: ByteArray, password: String): ByteArray {
        val cipher = Cipher.getInstance("DES")
        val secretKeyFactory = SecretKeyFactory.getInstance("DES")
        val keySpec = DESKeySpec(password.toByteArray())
        val key = secretKeyFactory.generateSecret(keySpec)
        cipher.init(Cipher.DECRYPT_MODE, key, SecureRandom())
        return cipher.doFinal(input)
    }

}