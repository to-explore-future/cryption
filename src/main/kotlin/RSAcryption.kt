import sun.misc.BASE64Encoder
import java.io.ByteArrayOutputStream
import java.security.Key
import java.security.KeyPairGenerator
import javax.crypto.Cipher

class RSAcryption {

    private val rsaMaxEncryptionLength = 117
    private val rsaMaxDecryptionLength = 128


    fun encryption() {

        val pairGenerator = KeyPairGenerator.getInstance("RSA")
        val keypair = pairGenerator.genKeyPair()
        val privateKey = keypair.private
        val publicKey = keypair.public
        println("私钥是：\n" + BASE64Encoder().encode(privateKey.encoded))
        println("公钥是：\n" + BASE64Encoder().encode(publicKey.encoded))

        val input = "今天晚上九点老地方见面今天晚上九点老地方见面今天晚上九点老地方见面今天晚上九点老地方见面今天晚上九点老地方见面今天晚上九点老地方见面"
        val rSAPrivateKeyEncryptionResult = encryption(privateKey, input)
        println("RSA private encryption result: \n" + BASE64Encoder().encode(rSAPrivateKeyEncryptionResult))

        val rsaDecryptionResult = decryption(publicKey, rSAPrivateKeyEncryptionResult)
        println("RSA public decryption result: \n" + String(rsaDecryptionResult!!))

    }

    /**
     * RSA 由于加密速度比较慢，api要求一次加密的长度不超过117个byte
     * 如果要加密的内容超过了 117，那么就是用分段加密
     */
    private fun encryption(key: Key, input: String?): ByteArray? {
        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.ENCRYPT_MODE, key)

        var temp: ByteArray
        val byteArrayOutputStream = ByteArrayOutputStream()

        if (input != null) {
            val inputByteArray = input.toByteArray()
            var times = inputByteArray.size / rsaMaxEncryptionLength
            if (inputByteArray.size % rsaMaxEncryptionLength > 0) {
                times += 1
            }
            var offset = 0
            for (i in 0 until times) {
                if (i == times - 1) {
                    temp = cipher.doFinal(inputByteArray, offset, inputByteArray.size - offset)
                    offset = inputByteArray.size

                } else {
                    temp = cipher.doFinal(inputByteArray, offset, rsaMaxEncryptionLength)
                    offset += rsaMaxEncryptionLength
                }
                byteArrayOutputStream.write(temp)
            }
        }
        return byteArrayOutputStream.toByteArray()
    }

    private fun decryption(key: Key, input: ByteArray?): ByteArray? {

        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.DECRYPT_MODE, key)
        val byteArrayOutputStream = ByteArrayOutputStream()

        var offset = 0
        var temp: ByteArray

        if (input != null) {
            var times = input.size / rsaMaxDecryptionLength
            if (input.size % rsaMaxDecryptionLength > 0) {
                times += 1
            }
            for (i in 0 until times) {
                if (i == times - 1) {
                    temp = cipher.doFinal(input, offset, input.size - offset)
                    offset = input.size
                } else {
                    temp = cipher.doFinal(input, offset, rsaMaxDecryptionLength)
                    offset += rsaMaxDecryptionLength
                }
                byteArrayOutputStream.write(temp)
            }
        }
        return byteArrayOutputStream.toByteArray()
    }
}