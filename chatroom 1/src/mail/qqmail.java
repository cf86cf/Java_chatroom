package mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class qqmail {
    private static final String USERNAME = "*******@qq.com";//用户名
    private static final String PASSWORD = "************";//qq邮箱授权码
    public qqmail(String username, String data, String receiver) {
        Properties props = new Properties();

        // 开启debug调试
        props.setProperty("mail.debug", "false");
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        // 设置邮件服务器主机名
        props.setProperty("mail.host", "smtp.qq.com");
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");

        Session session = Session.getInstance(props);
        session.setDebug(false);

        MimeMessage mm = new MimeMessage(session);

        StringBuilder builder = new StringBuilder();
        builder.append("Hi！"+username+"\n");
        builder.append("欢迎加入Java聊天室，您的验证码是：\n");
        builder.append(data);
        try {
            InternetAddress from = new InternetAddress(USERNAME);
            mm.setSubject("Gaoooyh的Java聊天室注册验证消息");
            mm.setContent(builder.toString(), "text/html;charset=utf-8");
            mm.setFrom(from);

            Transport transport = session.getTransport();
            transport.connect("smtp.qq.com", USERNAME, PASSWORD);
            transport.sendMessage(mm, new Address[]{new InternetAddress(receiver)});
            transport.close();
            System.out.println("Send message successfully....");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

}
