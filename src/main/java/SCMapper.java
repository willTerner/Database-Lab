import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SCMapper {
    @Select("select * from sc where sno = #{sno}")
    public List<SC> getSCBySno(String sno);

    @Select("select * from sc")
    public List<SC> getSCs();

    @Insert("insert into sc (cno,sno,grade) VALUES (#{cno},#{sno},#{grade})")
    public void addGrade(@Param("cno")String cno,@Param("sno")String sno,@Param("grade")Integer grade);

    @Update("update sc set grade = #{grade} where cno=#{cno} && sno=#{sno}")
    public void updateGrade(@Param("grade")Integer grade,@Param("cno")String cno,@Param("sno")String sno);

}
