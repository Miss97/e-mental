package com.emental.dao.mapper;

import com.emental.dao.entity.EmMoodDiary;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface EmMoodDiaryMapper {

    @Select("SELECT * FROM EM_MOOD_DIARY")
    List<EmMoodDiary> getAll();

    @Select("SELECT * FROM EM_MOOD_DIARY WHERE DATA_ID = #{dataId}")
    EmMoodDiary getOne(String dataId);

    @Select("SELECT DATA_ID,USERNAME,CONTENT,MOOD_STATUS,CREATE_DATE,CREATE_TIME FROM EM_MOOD_DIARY WHERE USERNAME = #{username} ORDER BY CREATE_TIME DESC")
    List<EmMoodDiary> getListByUsername(String username);

    @Select("SELECT REPLACE(CAST(Round(SUM(MOOD_STATUS)/COUNT(1)) AS TEXT),'.0','') AS MOOD_STATUS,CREATE_DATE FROM EM_MOOD_DIARY WHERE USERNAME = #{username} GROUP BY CREATE_DATE ORDER BY CREATE_DATE DESC LIMIT 7")
    List<EmMoodDiary> getLast7Mood(String username);

    @Insert("INSERT INTO EM_MOOD_DIARY(DATA_ID,USERNAME,CONTENT,MOOD_STATUS,CREATE_DATE,CREATE_TIME) VALUES(#{dataId},#{username},#{content},#{moodStatus},#{createDate},#{createTime})")
    void insert(EmMoodDiary moodDiary);

    @Update("UPDATE EM_MOOD_DIARY SET CONTENT=#{content},MOOD_STATUS=#{status} WHERE USERNAME = #{username} AND CREATE_DATE = #{createDate}")
    void update(String username,String createDate,String content,String status);

    @Delete("DELETE FROM EM_MOOD_DIARY WHERE WHERE USERNAME = #{username} AND CREATE_DATE = #{createDate}")
    void delete(String username,String createDate);

}
