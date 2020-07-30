package com.emental;

import com.emental.dao.entity.EmUserInfo;
import com.emental.dao.mapper.EmUserInfoMapper;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private EmUserInfoMapper emUserInfoMapper;

    @Value("${mail.fromMail.addr}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    @Test
    public void testQuery() throws Exception {
        int flag = emUserInfoMapper.signInVerify("admin","admin");
        System.out.println(flag);
    }

    @Test
    public void sendTextMail() {
        // 纯文本邮件对象
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo("mu20101@126.com");
        message.setSubject("无题");
        message.setText("测试");
        mailSender.send(message);

    }
}
