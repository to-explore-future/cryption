import com.sun.org.apache.xml.internal.security.utils.Base64
import sun.misc.BASE64Encoder
import java.io.ByteArrayOutputStream
import java.security.Key
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

class RSAcryption {

    private val rsaMaxEncryptionLength = 117
    private val rsaMaxDecryptionLength = 128


    fun encryption() {

        val privateKeyStr = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL3GC6P2xGFU9nR+ITOVERIyGG5B\n" +
                "kyRnf4cKEwLmzSqrOXXzN0zTJPNb0EJ+o8N8UajcMYBofeETAw46RUab60vjDev5cR7mjDsSVMBP\n" +
                "54SvM6Z2ZkjzLbvvdbHNAyHx8CzghgRvbMAasjMMGcdwjjSbOVOOdVhzUtHOKHoqs3oFAgMBAAEC\n" +
                "gYAgERDyecYhNDwY3x85Gh0yV+is6MO2SQ7RgLBJszpD1X72IUzc3GjH/mGoesGtTJp30A1FR2LN\n" +
                "32qKwunlzn7pnqPgjrTyY07euBHH7YLPRpOJzxbFjunedhM/CAoiarUROmNHA/X2K/doYZxN+JYT\n" +
                "YRmWcs90rcqxGV8FXGMAGQJBAPvLU9ADKAdqSN8psgirOeIB/83mrDEn62jotCjhlQHEnNMOUOd8\n" +
                "f9mmHxQg8volpYpa7RP6ycc6YtTU5f1W6R8CQQDA8YSLgRSAggVF4a6LC1ulItjashXaZshX6C+L\n" +
                "ajhyy1mxirep2hoFF0I5y0ChZ8YOxA6fsx0jTWXFll7h8eRbAkEAiNpGP3S3uPAHk2NyJEwMNc6o\n" +
                "XhzV0hHEXUy9Psr81e0q33uUdXyxcHZdxe7yoQr9ImmxGX7hVKtjNBJSvpMSOQJBAIJxxv7aIhZP\n" +
                "0WjRj8QJxcf7q0kQJZ4m50Qngh1rjwZtXRKfilSXVAglFW0lcWiuVEUfYBOwcP6xESFo7HUtqQkC\n" +
                "QA6Tt46ulGcle+UzKZZpMxCXnWjvnwLSIoW1AxpPr/BbP+PAG1mz3ZF96m5mN2+TylFg+UgnsdC/\n" +
                "nmISE/0nsZo="
        val publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC9xguj9sRhVPZ0fiEzlRESMhhuQZMkZ3+HChMC\n" +
                "5s0qqzl18zdM0yTzW9BCfqPDfFGo3DGAaH3hEwMOOkVGm+tL4w3r+XEe5ow7ElTAT+eErzOmdmZI\n" +
                "8y2773WxzQMh8fAs4IYEb2zAGrIzDBnHcI40mzlTjnVYc1LRzih6KrN6BQIDAQAB"

        val keyFactory = KeyFactory.getInstance("RSA")
        val privateKey = keyFactory.generatePrivate(PKCS8EncodedKeySpec(Base64.decode(privateKeyStr)))
        val publicKey = keyFactory.generatePublic(X509EncodedKeySpec(Base64.decode(publicKeyStr)))


        val input = "今天晚上九点老地方见面今天晚上九点老地方见面今天晚上九点老地方见面今天晚上九点老地方见面今天晚上九点老地方见面今天晚上九点老地方见面"
        val rSAPrivateKeyEncryptionResult = encryption(privateKey, input)
        println("RSA private encryption result: \n" + BASE64Encoder().encode(rSAPrivateKeyEncryptionResult))

        val rsaDecryptionResult = decryption(publicKey, rSAPrivateKeyEncryptionResult)
        println("RSA public decryption result: \n" + String(rsaDecryptionResult!!))

    }

    private fun generatePrivatePublicKey() {
        val pairGenerator = KeyPairGenerator.getInstance("RSA")
        val keypair = pairGenerator.genKeyPair()
        val privateKey = keypair.private
        val publicKey = keypair.public
        println("私钥是：\n" + BASE64Encoder().encode(privateKey.encoded))
        println("公钥是：\n" + BASE64Encoder().encode(publicKey.encoded))
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