package by.sunshine.service;

import by.sunshine.config.EmailConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private static final String TITLE = "Sunshine Security";
    private static final String MESSAGE = "<h2 style='color:blue;'>bold-red email</h2>";
    private static final int LOWER_RANGE = 100_000;
    private static final int UPPER_BOUND = 999_999;
    private static final String ENCODING = "UTF-8";

    private final EmailConfig emailConfig;
    private final JavaMailSenderImpl javaMailSender;

    public int getAccessNumber(String emailReceiver) {
        log.error("email1{}", emailConfig.getEmailSender());
        int accessNumber = new Random().nextInt(LOWER_RANGE, UPPER_BOUND);
        javaMailSender.send(mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, ENCODING);
            message.setTo(emailReceiver);
            message.setFrom(emailConfig.getEmailSender());
            message.setSubject(TITLE);
            message.setText(MESSAGE + accessNumber, true);
        });
        return accessNumber;
    }
}
