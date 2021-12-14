import lombok.Data;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseMapper {
    @Select("select * from course where cno = #{cno}")
    public Course getCourse(String cno);

    @Insert("insert into course (cno,cname,ccredit,cpno) VALUES(#{cno},#{cname},#{ccredit},#{cpno})")
    public void addCourse(@Param("cno")String cno,@Param("cname")String cname,@Param("ccredit")Integer ccredit,@Param("cpno")String cpno);

    @Update("update course set ccredit =#{ccredit} where cno=#{cno}")
    public void updateCcredit(@Param("ccredit")Integer ccredit,@Param("cno")String cno);

    @Update("update course set cpno=#{cpno} where cno=#{cno}")
    public void updateCno(@Param("cpno")String cpno,@Param("cno")String cno);

    @Delete("delete from course where cno not in (select cno from sc)")
    public void deleteEmptyCourse();

    @Select("select * from course")
    public List<Course> getCourses();
}
