package com.emental.controller;

import com.emental.dao.entity.EmMoodDiary;
import com.emental.dao.entity.EmUserInfo;
import com.emental.dao.mapper.EmMoodDiaryMapper;
import com.emental.dao.mapper.EmUserInfoMapper;
import com.emental.util.BaseInfoGenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MenuController {

    @Autowired
    private EmUserInfoMapper emUserInfoMapper;
    @Autowired
    private EmMoodDiaryMapper emMoodDiaryMapper;

    @RequestMapping("/welcome")
    public String welcome(){
        return "welcome";
    }

    @RequestMapping("/getUserInfo")
    public String getUserInfo(){
        return "user_info";
    }

    @RequestMapping("/getDashboard")
    public String getDashboard(){
        return "dashboard";
    }

    @RequestMapping("/getMoodDiary")
    public String getMoodDiary(){
        return "mood_diary";
    }

    @RequestMapping("/getCalender")
    public String getCalender(){
        return "calender";
    }

    @RequestMapping("/getReflection")
    public String getReflection(){
        return "reflection";
    }

    @RequestMapping("/getLifestyleHelper")
    public String getLifestyleHelper(){
        return "lifestyle_helper";
    }

    @RequestMapping("/getRecommendation")
    public String getRecommendation(){
        return "recommendation";
    }

    @RequestMapping("/getWeeklyMood")
    public String getWeeklyMood(){
        return "weekly_mood";
    }

    @RequestMapping("/getSelfRatDepScale")
    public String getSelfRatDepScale(){
        return "self_rat_dep_scale";
    }

    @RequestMapping("/getInspirationQuiz")
    public String getInspirationQuiz(){
        return "inspiration_quiz";
    }

    @RequestMapping("/getMoodByDate")
    @ResponseBody
    public JsonResult getMoodByDate(String createDate) {
        List<String> moods = emMoodDiaryMapper.getMoodByDate(createDate);
        JsonResult jr = new JsonResult();
        jr.setData(moods);
        jr.setMessage("success");
        return jr;
    }

    @RequestMapping("/getLast7Mood")
    @ResponseBody
    public JsonResult getLast7Mood() throws ParseException {
        String username = BaseInfoGenUtil.getUsername();
        List<EmMoodDiary> moodDiaries = emMoodDiaryMapper.getLast7Mood(username);
        Map<String,String> last7MoodMap = new LinkedHashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM",Locale.ENGLISH);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
        for (EmMoodDiary moodDiary:moodDiaries){
            if (null!=moodDiary.getCreateDate())
                last7MoodMap.put(sdf.format(sdf2.parse(moodDiary.getCreateDate())),moodDiary.getMoodStatus());
        }
        JsonResult jr = new JsonResult();
        jr.setData(last7MoodMap);
        jr.setMessage("success");
        return jr;
    }

    @RequestMapping("/saveUserInfo")
    @ResponseBody
    public JsonResult saveUserInfo(String username,String realName,String gender,String birthDate,String emailAddress,String phoneNumber,String personInfo){
        EmUserInfo userInfo = new EmUserInfo();
        userInfo.setUsername(username);
        userInfo.setRealName(realName);
        userInfo.setGender(gender);
        userInfo.setBirthDate(birthDate);
        userInfo.setEmailAddress(emailAddress);
        userInfo.setPhoneNumber(phoneNumber);
        userInfo.setPersonInfo(personInfo);
        emUserInfoMapper.saveUserInfo(userInfo);
        JsonResult jr = new JsonResult();
        jr.setMessage("success");
        return jr;
    }

    @RequestMapping("/getUserInfoDtl")
    @ResponseBody
    public JsonResult getUserInfoDtl(){
        String username = BaseInfoGenUtil.getUsername();
        EmUserInfo userInfo = emUserInfoMapper.getUserInfo(username);
        JsonResult jr = new JsonResult();
        jr.setData(userInfo);
        jr.setMessage("success");
        return jr;
    }


    @RequestMapping("/getMoodDiaryContent")
    @ResponseBody
    public JsonResult getMoodDiaryContent() throws ParseException {
        String username = BaseInfoGenUtil.getUsername();
        List<EmMoodDiary> moodDiaries = emMoodDiaryMapper.getListByUsername(username);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
        for (EmMoodDiary diary:moodDiaries){
            if (null!=diary.getCreateTime())
                diary.setCreateTime(sdf.format(sdf2.parse(diary.getCreateTime())));
        }
        JsonResult jr = new JsonResult();
        jr.setData(moodDiaries);
        jr.setMessage("success");
        return jr;
    }

    @RequestMapping("/saveMoodDiary")
    @ResponseBody
    public JsonResult saveMoodDiary(String content,String status){
        String username = BaseInfoGenUtil.getUsername();
        String nowDate = BaseInfoGenUtil.getNowDate();
        String nowTime = BaseInfoGenUtil.getNowTimestamp();
        String dataId = BaseInfoGenUtil.getDataId(32);
        EmMoodDiary moodDiary = new EmMoodDiary();
        moodDiary.setDataId(dataId);
        moodDiary.setUsername(username);
        moodDiary.setContent(content);
        moodDiary.setMoodStatus(status);
        moodDiary.setCreateDate(nowDate);
        moodDiary.setCreateTime(nowTime);
        emMoodDiaryMapper.insert(moodDiary);

        JsonResult jr = new JsonResult();
        jr.setData(null);
        jr.setMessage("success");
        return jr;
    }
}
