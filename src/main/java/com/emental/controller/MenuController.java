package com.emental.controller;

import com.emental.dao.entity.EmDailyRecord;
import com.emental.dao.entity.EmMoodDiary;
import com.emental.dao.entity.EmUserInfo;
import com.emental.dao.mapper.EmDailyRecordMapper;
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
    @Autowired
    private EmDailyRecordMapper emDailyRecordMapper;

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

    @RequestMapping("/getRecord")
    @ResponseBody JsonResult getRecord(){
        String username = BaseInfoGenUtil.getUsername();
        String nowDate = BaseInfoGenUtil.getNowDate();
        EmDailyRecord dailyRecord = emDailyRecordMapper.getTodayRecord(username,nowDate);


        Map<String,Object> map = new HashMap<>();
        map.put("weight",dailyRecord.getWeightRecord());
        map.put("sleepHours",dailyRecord.getSleepRecord());
        map.put("weeklyExercise",formatWeeklyRecord(emDailyRecordMapper.getWeeklyExerciseRecord(username),"EXERCISE_RECORD","N"));
        map.put("weeklyWeight",formatWeeklyRecord(emDailyRecordMapper.getWeeklyWeightRecord(username),"WEIGHT_RECORD",null));
        map.put("weeklySleep",formatWeeklyRecord(emDailyRecordMapper.getWeeklySleepRecord(username),"SLEEP_RECORD",null));
        JsonResult jr = new JsonResult();
        jr.setData(map);
        jr.setMessage("success");
        return jr;
    }

    @RequestMapping("/recordWeight")
    @ResponseBody JsonResult recordWeight(String weight){
        String username = BaseInfoGenUtil.getUsername();
        String nowDate = BaseInfoGenUtil.getNowDate();
        emDailyRecordMapper.updateWeight(weight,username,nowDate);

        JsonResult jr = new JsonResult();
        jr.setData(null);
        jr.setMessage("success");
        return jr;
    }

    @RequestMapping("/recordSleep")
    @ResponseBody JsonResult recordSleep(String sleepHours){
        String username = BaseInfoGenUtil.getUsername();
        String nowDate = BaseInfoGenUtil.getNowDate();
        emDailyRecordMapper.updateSleep(sleepHours,username,nowDate);

        JsonResult jr = new JsonResult();
        jr.setData(null);
        jr.setMessage("success");
        return jr;
    }

    private List<String> formatWeeklyRecord(List<Map<String,String>> mapList,String keyValue,String nullValue){
        List<String> list = new ArrayList<>(7);
        Map<String,String> map = new LinkedHashMap<>();
        for (Map<String,String> m : mapList){
            map.put(m.get("CREATE_DATE"),m.get(keyValue));
        }
        Set<String> weekDays = getWeekDays();
        for (String weekDay:weekDays){
            if (map.get(weekDay)==null)
                list.add(nullValue);
            else
                list.add(map.get(weekDay));
        }
        return list;
    }

    private Set<String> getWeekDays() {
        Set<String> weekDays = new LinkedHashSet<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK );
        if (c.getFirstDayOfWeek() == Calendar.SUNDAY) {
            c.add(Calendar.DAY_OF_MONTH, 0);
        }
        c.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
        for (int i=0;i<7;i++) {
            c.add(Calendar.DAY_OF_MONTH, 1);
            weekDays.add(sdf.format(c.getTime()));
        }
        return weekDays;
    }

}
