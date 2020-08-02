package com.emental.dao.mapper;

import com.emental.dao.entity.EmDailyRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface EmDailyRecordMapper {


        @Select("SELECT * FROM EM_DAILY_RECORD")
        List<EmDailyRecord> getAll();

        @Select("SELECT * FROM EM_DAILY_RECORD WHERE DATA_ID = #{dataId}")
        EmDailyRecord getOne(String dataId);

        @Select("SELECT EXERCISE_RECORD FROM EM_DAILY_RECORD WHERE USERNAME = #{username} AND CREATE_DATE>=strftime('%Y%m%d','now','+0 day','weekday 0')")
        List<String> getWeeklyExerciseRecord(String username);

        @Select("SELECT WEIGHT_RECORD FROM EM_DAILY_RECORD WHERE USERNAME = #{username} AND CREATE_DATE>=strftime('%Y%m%d','now','+0 day','weekday 0')")
        List<String> getWeeklyWeightRecord(String username);

        @Select("SELECT SLEEP_RECORD FROM EM_DAILY_RECORD WHERE USERNAME = #{username} AND CREATE_DATE>=strftime('%Y%m%d','now','+0 day','weekday 0')")
        List<String> getWeeklySleepRecord(String username);

        @Select("SELECT * FROM EM_DAILY_RECORD WHERE USERNAME = #{username} AND CREATE_DATE = #{createDate}")
        List<EmDailyRecord> getTodayRecord(String username,String createDate);

        @Insert("INSERT INTO EM_DAILY_RECORD(DATA_ID,USERNAME,WEIGHT_RECORD,EXERCISE_RECORD,SLEEP_RECORD,CREATE_DATE) VALUES(#{dataId},#{username},#{weightRecord},#{exerciseRecord},#{sleepRecord},#{createDate})")
        void insertRecord(EmDailyRecord record);

        @Update("UPDATE EM_DAILY_RECORD SET EXERCISE_RECORD='Y' WHERE USERNAME =#{username} AND CREATE_DATE = #{createDate}")
        void updateExercise(String exerciseRecord,String username,String createDate);

        @Update("UPDATE EM_DAILY_RECORD SET WEIGHT_RECORD=#{weightRecord} WHERE USERNAME = #{username} AND CREATE_DATE = #{createDate}")
        void updateWeight(String weightRecord,String username,String createDate);

        @Update("UPDATE EM_DAILY_RECORD SET SLEEP_RECORD=#{sleepRecord} WHERE USERNAME = #{username} AND CREATE_DATE = #{createDate}")
        void updateSleep(String sleepRecord,String username,String createDate);

        @Delete("DELETE FROM EM_DAILY_RECORD WHERE DATA_ID =#{dataId}")
        void delete(String dataId);

}
