package com.emental.controller;

import com.emental.dao.entity.EmDailyRecord;
import com.emental.dao.entity.EmUserInfo;
import com.emental.dao.mapper.EmDailyRecordMapper;
import com.emental.dao.mapper.EmUserInfoMapper;
import com.emental.util.BaseInfoGenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class SignInController {
    @Autowired
    private EmUserInfoMapper emUserInfoMapper;
    @Autowired
    private EmDailyRecordMapper emDailyRecordMapper;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.fromMail.addr}")
    private String from;

    @RequestMapping("/")
    public String index(HttpServletRequest request, HttpServletResponse response){
        String username = (String) request.getSession().getAttribute("username");
        if (username!=null&&!"".equals(username))
            return "redirect:/welcome";
        return "signIn";
    }

    @RequestMapping("/signInVerify")
    @ResponseBody
    public JsonResult signInVerify(HttpServletRequest request, HttpServletResponse response, String username, String password){
        JsonResult jr = new JsonResult();
        int flag = emUserInfoMapper.signInVerify(username,password);
        if (flag==0){
            jr.setMessage("failed");
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("username",username);
            jr.setMessage("success");
        }
        return jr;
    }

    @RequestMapping("/signIn")
    public String signIn(){
        String username = BaseInfoGenUtil.getUsername();
        String createDate = BaseInfoGenUtil.getNowDate();
        int count = emDailyRecordMapper.verifyExists(username,createDate);
        if (count<1&&username!=null){
            EmDailyRecord dailyRecord = new EmDailyRecord();
            dailyRecord.setDataId(BaseInfoGenUtil.getDataId(32));
            dailyRecord.setUsername(username);
            dailyRecord.setCreateDate(createDate);
            emDailyRecordMapper.insertRecord(dailyRecord);
        }
        return "/welcome";
    }

    @RequestMapping("/usernameVerify")
    @ResponseBody
    public JsonResult usernameVerify(String username){
        JsonResult jr = new JsonResult();
        int count = emUserInfoMapper.usernameVerify(username);
        if (count>0)
            jr.setMessage("failed");
        else
            jr.setMessage("success");
        return jr;
    }

    @RequestMapping("/emailVerify")
    @ResponseBody
    public JsonResult emailVerify(String email){
        JsonResult jr = new JsonResult();
        int count = emUserInfoMapper.emailVerify(email);
        if (count>0)
            jr.setMessage("failed");
        else
            jr.setMessage("success");
        return jr;
    }

    @RequestMapping("/signUpVerify")
    @ResponseBody
    public JsonResult signUpVerify(String username,String password,String email) throws MessagingException {
        EmUserInfo userInfo = new EmUserInfo();
        userInfo.setDataId(BaseInfoGenUtil.getDataId(32));
        userInfo.setCreateDate(BaseInfoGenUtil.getNowDate());
        userInfo.setUsername(username);
        userInfo.setPassword(password);
        userInfo.setEmailAddress(email);
        userInfo.setStatus("0");
        JsonResult jr = new JsonResult();
        sendTextMail(username,userInfo.getDataId(),email);
        emUserInfoMapper.insert(userInfo);
        jr.setMessage("success");
        return jr;
    }

    @RequestMapping("/accountActivation")
    public String accountActivation(HttpServletRequest request, HttpServletResponse response,String id){
        emUserInfoMapper.acctiveAccount(id);
        EmUserInfo userInfo = emUserInfoMapper.getOne(id);
        HttpSession session = request.getSession();
        session.setAttribute("username",userInfo.getUsername());
        return "account_activation";
    }

    @RequestMapping("/signUp")
    public String signUp(){
        return "email_verify";
    }

    @RequestMapping("/signOut")
    public String signOut(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        session.removeAttribute("username");
        return "redirect:/";
    }

    @RequestMapping("/resetPass")
    @ResponseBody
    public JsonResult resetPass(String nameOrMail) throws MessagingException {
        JsonResult jr = new JsonResult();
        Map<String,String> map = emUserInfoMapper.getEmailAddress(nameOrMail);
        if (null==map||map.size()==0){
            jr.setMessage("failed");
        }else{
            sendRstPwdMail(map.get("USERNAME"),map.get("DATA_ID"),map.get("EMAIL_ADDRESS"));
            jr.setMessage("success");
        }
        return jr;
    }

    @RequestMapping("/resetPassAlertPage")
    public String resetPassAlertPage(){
        return "reset_pass_alert";
    }

    @RequestMapping("/resetPassword")
    public String resetPassword(){
        return "reset_pass";
    }

    @RequestMapping("/doResetPassword")
    @ResponseBody
    public JsonResult doResetPassword(String id,String password){
        emUserInfoMapper.resetPassword(id,password);
        JsonResult jr = new JsonResult();
        jr.setMessage("success");
        return jr;
    }

    public void sendTextMail(String username,String id,String email) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(from);
        helper.setTo(email);
        helper.setSubject("Account activation");
        helper.setText("Hi "+username+"<br><br>" +
                "Click the link below to activate your account.<br>" +
                "<a href='http://localhost:8080/accountActivation?id="+id+"'>http://localhost:8080/accountActivation?id="+id+"</a>",true);
        mailSender.send(mimeMessage);
    }


    public void sendRstPwdMail(String username,String id,String email) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(from);
        helper.setTo(email);
        helper.setSubject("Account activation");
        helper.setText("Hi "+username+"<br><br>" +
                "Click the link below to reset your password.<br>" +
                "<a href='http://localhost:8080/resetPassword?id="+id+"'>http://localhost:8080/resetPassword?id="+id+"</a>",true);
        mailSender.send(mimeMessage);
    }
}
