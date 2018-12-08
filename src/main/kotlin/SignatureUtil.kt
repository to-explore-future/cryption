import sun.misc.BASE64Encoder
import java.security.Signature

class SignatureUtil {

    private val message = "good=iphoneX&price=8888"
    private val message1 = "good=iphoneX&price=8"

    fun run() {
        val sign = sign()
        println("${message}的签名：\t" + BASE64Encoder().encode(sign))

        val verify = verify(sign)
        println("verify:$verify")
    }

    private fun sign(): ByteArray {
        val signature = Signature.getInstance("SHA256withRSA")
        signature.initSign(RSAcryption().generatePrivateKey())
        signature.update(message.toByteArray())
        return signature.sign()
    }

    private fun verify(sign: ByteArray): Boolean {
        val signature = Signature.getInstance("SHA256withRSA")
        signature.initVerify(RSAcryption().generatePublicKey())
        signature.update(message1.toByteArray())
        return signature.verify(sign)
    }
}