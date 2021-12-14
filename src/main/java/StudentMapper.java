import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface StudentMapper {
    @Select("select * from student where sno=#{sno}")
    public Student getStudent(String sno);

    @Insert("insert into student (sno,sname,sage,sdept,scholarship,ssex) VALUES (#{sno},#{sname},#{sage},#{sdept},#{scholarship},#{ssex})")
    public void addStudent(@Param("sno") String sno, @Param("sname")String sname, @Param("sage")Integer sage, @Param("sdept")String sdept, @Param("scholarship")String scholarship, @Param("ssex")String ssex);

    @Update("update student set sname=#{sname} where sno=#{sno}")
    public void updateSname(@Param("sname")String sname,@Param("sno")String sno);

    @Update("update student set sage=#{sage} where sno=#{sno}")
    public void updateSage(@Param("sage")Integer sage,@Param("sno")String sno);

    @Update("update student set sdept=#{sdept} where sno=#{sno}")
    public void updateSdept(@Param("sdept")String sdept,@Param("sno")String sno);

    @Update("update student set scholarship=#{scholarship} where sno=#{sno}")
    public void updateScholarship(@Param("scholarship")String scholarship,@Param("sno")String sno);

    @Select("select sdept from student where sno in (select sno from sc)")
    public List<String> getSdeptWithGrade();

    @Select("select sno,sname from student where sdept=#{sdept} && sno in (select sno from sc)")
    public List<Student> getStudentWithGrade(String sdept);


}
