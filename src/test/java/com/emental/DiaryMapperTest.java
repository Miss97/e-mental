package com.emental;

import com.emental.dao.entity.EmMoodDiary;
import com.emental.dao.mapper.EmMoodDiaryMapper;
import com.emental.util.BaseInfoGenUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DiaryMapperTest {

    @Autowired
    private EmMoodDiaryMapper emMoodDiaryMapper;

    @Test
    public void getMoodTest() throws Exception {
        List<String> result = emMoodDiaryMapper.getMoodByDate("20990101");
        Assert.assertEquals(0,result.size());
    }

    @Test
    public void getListTest() throws Exception {
        List<EmMoodDiary> result = emMoodDiaryMapper.getListByUsername("test");
        Assert.assertEquals(0,result.size());
    }

    @Test
    public void insertRecordTest() throws Exception {
        EmMoodDiary diary = new EmMoodDiary();
        diary.setDataId(BaseInfoGenUtil.getDataId(32));
        diary.setCreateDate(BaseInfoGenUtil.getNowDate());
        diary.setUsername("admin");
        emMoodDiaryMapper.insert(diary);
    }

}
