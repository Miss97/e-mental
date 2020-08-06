package com.emental;

import com.emental.dao.entity.EmDailyRecord;
import com.emental.dao.mapper.EmDailyRecordMapper;
import com.emental.util.BaseInfoGenUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DailyRecordMapperTest {

    @Autowired
    EmDailyRecordMapper dailyRecordMapper;

    @Test
    public void weeklyExerciseRecordTest() throws Exception {
        EmDailyRecord dailyRecord = dailyRecordMapper.getOne("RWRWAFXCEL1ONKJ7KNMY85UHIAMG1GKG");
        Assert.assertEquals("49.1",dailyRecord.getWeightRecord());
    }

    @Test
    public void insertRecordTest() throws Exception {
        EmDailyRecord dailyRecord = new EmDailyRecord();
        dailyRecord.setDataId(BaseInfoGenUtil.getDataId(32));
        dailyRecord.setCreateDate(BaseInfoGenUtil.getNowDate());
        dailyRecord.setUsername("test");
        dailyRecordMapper.insertRecord(dailyRecord);
    }

}
