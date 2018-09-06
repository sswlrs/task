package com.example.pinche2;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update; 
@Mapper  
public interface taskMapper {  
	@Select("select * from task")
	List<task> select1();
	@Select("select * from task where gathertime = #{gathertime} and "
			+ "startpoint = #{startpoint} and destination = #{destination}")  
	List<task> select2(@Param("gathertime") String gathertime,
    		@Param("startpoint") String startpoint, 
    		@Param("destination") String destination);
	@Select("select * from task where gathertime = #{gathertime}")
	List<task> select3(String gathertime);
	@Select("select * from task where startpoint = #{startpoint}")
	List<task> select4(String gathertime);
	@Select("select * from task where destination = #{destination}")
	List<task> select5(String gathertime);
	@Select("select * from task where gathertime = #{gathertime} and "
			+ "startpoint = #{startpoint}")  
	List<task> select6(@Param("gathertime") String gathertime,
    		@Param("startpoint") String startpoint);
	@Select("select * from task where gathertime = #{gathertime} and "
			+ "destination = #{destination}")  
	List<task> select7(@Param("gathertime") String gathertime,
    		@Param("destination") String destination);
	@Select("select * from task where startpoint = #{startpoint} and "
			+ "destination = #{destination}")  
	List<task> select8(@Param("startpoint") String startpoint, 
    		@Param("destination") String destination);
	@Select("select * from task where identification = #{identification}")
	List<task> select9(int identification);
    @Insert("INSERT INTO task VALUES(null, #{gathertime}, #{account}, null, null, null ,"
    		+ "#{startpoint}, #{destination}, #{number})")
    boolean insert( @Param("gathertime") String gathertime,
    		@Param("account") String account, @Param("startpoint") String startpoint, 
    		@Param("destination") String destination, @Param("number") int number);
    @Update("UPDATE task SET number = number+1 where identification = #{identification}")
    boolean update1(int identification);
    @Update("UPDATE task SET account2 = #{account} where identification = #{identification}")
    boolean update2(@Param("identification") int identification, @Param("account") String account);
    @Update("UPDATE task SET account3 = #{account} where identification = #{identification}")
    boolean update3(@Param("identification") int identification, @Param("account") String account);
    @Update("UPDATE task SET account4 = #{account} where identification = #{identification}")
    boolean update4(@Param("identification") int identification, @Param("account") String account);
    @Update("UPDATE task SET account1 = #{account} where identification = #{identification}")
    boolean update5(@Param("identification") int identification, @Param("account") String account);
    @Update("UPDATE task SET number = number-1 where identification = #{identification}")
    boolean update6(int identification);
    @Delete("delete from task where identification = #{identification}")
    int delete(int identification);
    
}
