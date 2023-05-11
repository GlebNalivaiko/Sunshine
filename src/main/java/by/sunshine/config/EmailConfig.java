package by.sunshine.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    public static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
    public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    public static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    public static final String MAIL_DEBUG = "mail.debug";
    public static final String SMTP = "smtp";
    public static final String TRUE = "true";
    @Value("${email.sender.name}")
    private String emailSender;
    @Value("${email.sender.password}")
    private String password;
    @Value("${email.port}")
    private int port;
    @Value("${email.host}")
    private String host;


    @Bean
    public JavaMailSenderImpl sendEmailNumber() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(emailSender);
        javaMailSender.setPassword(password);
        javaMailSender.setJavaMailProperties(getProperties());
        return javaMailSender;
    }

    @Bean
    @Scope(scopeName = "prototype")
    public Properties getProperties() {
        Properties properties = new Properties();
        properties.put(MAIL_TRANSPORT_PROTOCOL, SMTP);
        properties.put(MAIL_SMTP_AUTH, TRUE);
        properties.put(MAIL_SMTP_STARTTLS_ENABLE, TRUE);
        properties.put(MAIL_DEBUG, TRUE);
        return properties;
    }

    public String getEmailSender() {
        return emailSender;
    }
}
