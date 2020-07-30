package com.emental.dao.mapper;

import com.emental.dao.entity.EmUserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface EmUserInfoMapper {


        @Select("SELECT * FROM EM_USER_INFO")
        List<EmUserInfo> getAll();

        @Select("SELECT * FROM EM_USER_INFO WHERE DATA_ID = #{dataId}")
        EmUserInfo getOne(String dataId);

        @Select("SELECT COUNT(1) FROM EM_USER_INFO WHERE USERNAME = #{username} AND PASSWORD = #{password}")
        int signInVerify(String username,String password);

        @Select("SELECT COUNT(1) FROM EM_USER_INFO WHERE USERNAME = #{username}")
        int usernameVerify(String username);

        @Select("SELECT COUNT(1) FROM EM_USER_INFO WHERE EMAIL_ADDRESS = #{email}")
        int emailVerify(String email);

        @Select("SELECT * FROM EM_USER_INFO WHERE USERNAME = #{username}")
        EmUserInfo getUserInfo(String username);

        @Insert("INSERT INTO EM_USER_INFO(DATA_ID,USERNAME,PASSWORD,REAL_NAME,BIRTH_DATE,GENDER,EMAIL_ADDRESS,PHONE_NUMBER,PERSON_INFO,CREATE_DATE,STATUS) VALUES(#{dataId},#{username},#{password},#{realName},#{birthDate},#{gender},#{emailAddress},#{phoneNumber},#{personInfo},#{createDate},#{status})")
        void insert(EmUserInfo user);

        @Update("UPDATE EM_USER_INFO SET STATUS='1' WHERE DATA_ID =#{dataId}")
        void acctiveAccount(String dataId);

        @Update("UPDATE EM_USER_INFO SET USERNAME=#{username},PASSWORD=#{password},REAL_NAME=#{realName},BIRTH_DATE=#{birthDate},GENDER=#{gender},EMAIL_ADDRESS=#{emailAddress},PHONE_NUMBER=#{phoneNumber},PERSON_INFO=#{personInfo},CREATE_DATE=#{createDate},STATUS=#{status} WHERE DATA_ID =#{dataId}")
        void update(EmUserInfo user);

        @Delete("DELETE FROM EM_USER_INFO WHERE DATA_ID =#{dataId}")
        void delete(String dataId);

}
