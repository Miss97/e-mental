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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @RequestMapping("/getLast7Mood")
    @ResponseBody
    public JsonResult getLast7Mood() throws ParseException {
        String username = BaseInfoGenUtil.getUsername();
        List<EmMoodDiary> moodDiaries = emMoodDiaryMapper.getLast7Mood(username);
        Map<String,String> last7MoodMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
        for (EmMoodDiary moodDiary:moodDiaries){
            Date date = sdf2.parse(moodDiary.getCreateDate());
            last7MoodMap.put(sdf.format(date),moodDiary.getMoodStatus());
        }
        JsonResult jr = new JsonResult();
        jr.setData(last7MoodMap);
        jr.setMessage("success");
        return jr;
    }

    @RequestMapping("/getUserInfoDtl")
    @ResponseBody
    public JsonResult getUserInfoDtl(){
        String username = BaseInfoGenUtil.getUsername();
        EmUserInfo userInfo = emUserInfoMapper.getUserInfo(username);
        Map<String,String> result = new HashMap<>();
        JsonResult jr = new JsonResult();
        jr.setData(userInfo);
        jr.setMessage("success");
        return jr;
    }


    @RequestMapping("/getMoodDiaryContent")
    @ResponseBody
    public JsonResult getMoodDiaryContent(){
        String username = BaseInfoGenUtil.getUsername();
        String nowDate = BaseInfoGenUtil.getNowDate();
        EmMoodDiary moodDiary = emMoodDiaryMapper.getOneByUsername(username,nowDate);
        Map<String,String> result = new HashMap<>();
        if (moodDiary == null){
            result.put("updateFlg","false");
        }else{
            result.put("updateFlg","true");
            result.put("content",moodDiary.getContent());
            result.put("status",moodDiary.getMoodStatus());
        }
        JsonResult jr = new JsonResult();
        jr.setData(result);
        jr.setMessage("success");
        return jr;
    }

    @RequestMapping("/saveMoodDiary")
    @ResponseBody
    public JsonResult saveMoodDiary(String content,String status,boolean updateFlg){
        String username = BaseInfoGenUtil.getUsername();
        String nowDate = BaseInfoGenUtil.getNowDate();

        if (updateFlg){
            emMoodDiaryMapper.update(username,nowDate,content,status);
        } else {
            String dataId = BaseInfoGenUtil.getDataId(32);
            EmMoodDiary moodDiary = new EmMoodDiary();
            moodDiary.setDataId(dataId);
            moodDiary.setUsername(username);
            moodDiary.setContent(content);
            moodDiary.setMoodStatus(status);
            moodDiary.setCreateDate(nowDate);
            emMoodDiaryMapper.insert(moodDiary);
        }

        JsonResult jr = new JsonResult();
        jr.setData(null);
        jr.setMessage("success");
        return jr;
    }
}
