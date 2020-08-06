package com.emental;

import com.emental.dao.entity.EmUserInfo;
import com.emental.dao.mapper.EmUserInfoMapper;
import com.emental.util.BaseInfoGenUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;


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
    public void signInVerfiyTest() throws Exception {
        int flag = emUserInfoMapper.signInVerify("admin","admin");
        Assert.assertEquals(1,flag);
    }

    @Test
    public void usernameVerifyTest() throws Exception {
        int flag = emUserInfoMapper.usernameVerify("admin");
        Assert.assertEquals(1,flag);
    }

    @Test
    public void emailVerifyTest() throws Exception {
        int flag = emUserInfoMapper.emailVerify("test@test.com");
        Assert.assertEquals(1,flag);
    }

    @Test
    public void sendTextMailText() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo("test@test.com");
        message.setSubject("TEST");
        message.setText("TEST");
        mailSender.send(message);
    }

    @Test
    public void insertRecordTest() throws Exception {
        EmUserInfo userInfo = new EmUserInfo();
        userInfo.setDataId(BaseInfoGenUtil.getDataId(32));
        userInfo.setUsername("test");
        userInfo.setPassword("test");
        userInfo.setCreateDate(BaseInfoGenUtil.getNowDate());
        emUserInfoMapper.insert(userInfo);
    }
}
