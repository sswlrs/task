package com.example.pinche2;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select; 
@Mapper  
public interface userMapper {  
	@Select("select * from user")  
    List<user> select1();
    @Select("select * from user where account = #{account}")  
    List<user> select2(String account);
    @Select("select * from user where account = #{account} and password = #{password}")  
    List<user> select3(@Param("account") String account, @Param("password") String password);
    @Insert("INSERT INTO user VALUES(#{account}, #{password})")
    boolean insert(@Param("account") String account, @Param("password") String password);
}  
