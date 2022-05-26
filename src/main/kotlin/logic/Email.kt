import com.mailslurp.apis.InboxControllerApi
import com.mailslurp.models.SendEmailOptions

const val MAILSLURP_KEY = "aaf108d556d4541252ce61eac57fbbf005ead6255ec2702c95da14d59ae41d96"

fun sendNewPassword(newPassword: String) {
//    thread {
    println(System.currentTimeMillis())
        try {
            val html = """
                <!DOCTYPE html>
                <html>
                    <head>
                        <meta charset="utf-8">
                    </head>
                    <body>
                        <p>XXX先生，您好！</p>
                                <p>您的新密码为 <strong><code>${newPassword}</code></strong>，为了您的账户安全，请尽快修改密码并删除此封邮件。</p>
                                <p>祝您生活愉快！</p>
                                <blockquote>
                                <p>请不要向任何人透露以上新密码，官方人员不会以任何理由向您索要密码信息。</p>
                                <p>若重设密码并非由您本人发起，请忽略本邮件，十分抱歉打扰到您。</p>
                                </blockquote>
                                <p>xx团队致上</p>
                        <img src="https://source.android.com/setup/images/Android_symbol_green_RGB.png" alt="">
                    </body>
                </html>
            """.trimIndent()

            val inboxController = InboxControllerApi(MAILSLURP_KEY)
            val inbox = inboxController.createInbox(null, null, null, null, null, null, null, null, null, null)
            inboxController.sendEmail(
                inboxId = inbox.id,
                sendEmailOptions = SendEmailOptions(
                    to = listOf("hi@bqliang.com"),
                    body = html,
                    subject = "请查收您的新密码",
                    isHTML = true
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
//    }
}

fun main() {
    sendNewPassword("")
    println(System.currentTimeMillis())
}