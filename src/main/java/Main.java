import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        Scanner scanner = new Scanner(System.in);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            StudentMapper mapper = session.getMapper(StudentMapper.class);
            SCMapper scMapper = session.getMapper(SCMapper.class);
            CourseMapper courseMapper = session.getMapper(CourseMapper.class);
            while(true){
                System.out.println("请选择菜单项，输入一个数字");
                System.out.println("1.退出程序  2.添加学生  3.查看学生信息   4.修改学生信息   5.增加课程   6.修改课程信息   7.删除选课信息为空的课程\n"  +
                        "8.查看课程信息和选课信息   9.添加学生成绩   10.修改学生成绩   11.按系查看学生成绩   12.按系对学生成绩排名");
                int mode = 0;
                mode = scanner.nextInt();
                String sname,sno,sdept,scholarship,ssex;
                String cno,cname,cpno;
                Integer sage,ccredit,grade;
                HashSet<String> set;
                switch(mode){
                    case 1:
                        break;
                    case 2:
                        System.out.println("请输入学生信息，输入格式为学号 姓名 年龄 学院 是否奖学金 性别");
                        sno = scanner.next();
                        sname = scanner.next();
                        sage = scanner.nextInt();
                        sdept = scanner.next();
                        scholarship =  scanner.next();
                        ssex = scanner.next();
                        try{
                            mapper.addStudent(sno,sname,sage,sdept,scholarship,ssex);
                            session.commit();
                        }catch(Exception ex){
                            ex.printStackTrace();
                            session.rollback();
                        }
                        break;
                    case 3:
                        System.out.println("请输入学生学号");
                        sno = scanner.next();
                        try{
                            Student student = mapper.getStudent(sno);
                            if(student==null){
                                System.out.println("未查询到学生信息!");
                                break;
                            }
                            List<SC> scs = scMapper.getSCBySno(sno);
                            System.out.printf("学生信息为:学号=%s，姓名=%s，年龄=%d，学院=%s，是否获得奖学金:%s，性别=%s ",sno,student.getSname(),student.getSage(),student.getSdept(),student.getScholarship(),student.getSsex());
                            if(scs.size()==0){
                                System.out.print("选课信息为空\n");
                                break;
                            }else{
                                System.out.print("选课信息如下:");
                            }
                            for(SC sc:scs){
                                cno = sc.getCno();
                                Course course = courseMapper.getCourse(cno);
                                System.out.print(course.getCname()+" ");
                            }
                            System.out.println();
                        }catch(Exception ex){
                            ex.printStackTrace();
                        }
                        break;
                    case 4:
                        while(true){
                            System.out.println("请输入要修改的学生的学号");
                            sno = scanner.next();
                            System.out.println("请选择操作");
                            System.out.println("1.退出修改   2.修改姓名   3.修改年龄   4.修改学院   5.修改是否获得奖学金");
                            int op;
                            op = scanner.nextInt();
                            switch(op){
                                case 1:
                                    break;
                                case 2:
                                    System.out.println("请输入修改的姓名");
                                    sname = scanner.next();
                                    try{
                                        mapper.updateSname(sname,sno);
                                        session.commit();
                                    }catch(Exception ex){
                                        ex.printStackTrace();
                                        session.rollback();
                                    }
                                    break;
                                case 3:
                                    System.out.println("请输入修改后的年龄");
                                    sage = scanner.nextInt();
                                    try{
                                        mapper.updateSage(sage,sno);
                                        session.commit();
                                    }catch(Exception ex){
                                        ex.printStackTrace();
                                        session.rollback();
                                    }
                                    break;
                                case 4:
                                    System.out.println("请输入修改后的学院");
                                    sdept = scanner.next();
                                    try{
                                        mapper.updateSdept(sdept,sno);
                                        session.commit();
                                    }catch(Exception ex){
                                        ex.printStackTrace();
                                        session.rollback();
                                    }
                                    break;
                                case 5:
                                    System.out.println("请输入是否获得奖学金");
                                    scholarship = scanner.next();
                                    try{
                                        mapper.updateScholarship(scholarship,sno);
                                        session.commit();
                                    }catch(Exception ex){
                                        ex.printStackTrace();
                                        session.rollback();
                                    }
                                    break;
                            }
                            if(op==1){
                                break;
                            }
                        }
                        break;
                    case 5:
                        System.out.println("请输入课程序号 课程名 学分 先修课");
                        cno = scanner.next();
                        cname = scanner.next();
                        ccredit = scanner.nextInt();
                        cpno = scanner.next();
                        try{
                            courseMapper.addCourse(cno,cname,ccredit,cpno);
                            session.commit();
                        }catch(Exception ex){
                            ex.printStackTrace();
                            session.rollback();
                        }
                        break;
                    case 6:
                        while(true){
                            System.out.println("输入想要更改的课程序号");
                            cno = scanner.next();
                            System.out.println("请选择操作");
                            System.out.println("1.退出修改   2.修改课程学分  3.修改课程先修课");
                            int op;
                            op = scanner.nextInt();
                            switch(op){
                                case 1:
                                    break;
                                case 2:
                                    System.out.println("请输入要修改的课程学分");
                                    ccredit = scanner.nextInt();
                                    try{
                                        courseMapper.updateCcredit(ccredit,cno);
                                        session.commit();
                                    }catch(Exception ex){
                                        ex.printStackTrace();
                                        session.rollback();
                                    }
                                    break;
                                case 3:
                                    System.out.println("请输入要修改的课程先修课序号");
                                    cpno = scanner.next();
                                    try{
                                        courseMapper.updateCno(cpno,cno);
                                        session.commit();
                                    }catch(Exception ex){
                                        ex.printStackTrace();
                                        session.rollback();
                                    }
                                    break;
                                default:
                                    System.out.println("输入数字不在指定范围内!");
                                    break;
                            }
                            if(op==1){
                                break;
                            }
                        }
                        break;
                    case 7:
                        try{
                            courseMapper.deleteEmptyCourse();
                            session.commit();
                            System.out.println("删除选课信息为空的课程成功!");
                        }catch(Exception ex){
                            ex.printStackTrace();
                            session.rollback();
                        }
                        break;
                    case 8:
                        try{
                            List<Course> courses = courseMapper.getCourses();
                            if(courses.size()==0){
                                System.out.println("课程信息为空!");
                            }else{
                                System.out.printf("%6s %6s %6s %6s\n","课程序号","课程名","学分","先修课");
                                for(Course course:courses){
                                    System.out.printf("%6s %6s %6s %6s\n",course.getCno(),course.getCname(),course.getCcredit()==null?"NULL":course.getCcredit(),course.getCpno()==null?"NULL":course.getCpno());
                                }
                                List<SC> scs = scMapper.getSCs();
                                if(scs.size()==0){
                                    System.out.println("选课信息为空");
                                }else{
                                    System.out.printf("%6s %6s %6s\n","课程序号","学生序号","学生成绩");
                                    for(SC sc:scs){
                                        System.out.printf("%6s %6s %6s\n",sc.getCno(),sc.getSno(),sc.getGrade()==null?"null":sc.getGrade());
                                    }
                                }
                            }
                        }catch(Exception ex){
                            ex.printStackTrace();
                        }
                        break;
                    case 9:
                        System.out.println("请输入课程序号 学生序号 成绩");
                        cno = scanner.next();
                        sno = scanner.next();
                        grade = scanner.nextInt();
                        try{
                            scMapper.addGrade(cno,sno,grade);
                            session.commit();
                            System.out.println("插入成绩成功!");
                        }catch(Exception ex){
                            ex.printStackTrace();
                            session.rollback();
                        }
                        break;
                    case 10:
                        System.out.println("请输入课程序号 学生序号 成绩");
                        cno = scanner.next();
                        sno = scanner.next();
                        grade = scanner.nextInt();
                        try{
                            scMapper.updateGrade(grade,cno,sno);
                            session.commit();
                            System.out.println("修改学生成绩成功!");
                        }catch(Exception ex){
                            ex.printStackTrace();
                            session.rollback();
                        }
                        break;
                    case 11:
                        set = new HashSet<>();
                        try{
                            List<String> list = mapper.getSdeptWithGrade();
                            if(list.size()==0){
                                System.out.println("成绩信息为空!");
                            }else{
                                set.addAll(list);
                                for(String dept:set){
                                    System.out.println(dept+"系的学生成绩如下");
                                    List<Student> students = mapper.getStudentWithGrade(dept);
                                    int counter = 0;
                                    int execCounter = 0;
                                    int failCounter = 0;
                                    for(Student student:students){
                                        List<Integer> grades = scMapper.getSCBySno(student.getSno()).stream().map(el->el.getGrade()).collect(Collectors.toList());
                                        counter += grades.size();
                                        execCounter += grades.stream().reduce(0,(cur,el)->{
                                           if(el>=90){
                                               cur++;
                                           }
                                           return cur;
                                        });
                                        failCounter += grades.stream().reduce(0,(cur,el)->{
                                            if(el<60){
                                                cur++;
                                            }
                                            return cur;
                                        });
                                        int max = grades.stream().max(Integer::compareTo).get();
                                        int min = grades.stream().min(Integer::compareTo).get();
                                        int sum = grades.stream().reduce(0,(cur,el)->cur+el);
                                        System.out.println(student.getSname()+"最好成绩为"+max+" 最差成绩为"+min+" 平均成绩为"+sum*1.0/grades.size());
                                    }
                                    System.out.println(dept+"优秀率为"+execCounter*1.0/counter+" 不及格率为"+failCounter*1.0/counter);
                                }
                            }
                        }catch(Exception ex){
                            ex.printStackTrace();
                        }
                        break;
                    case 12:
                        set = new HashSet<>();
                        try{
                            List<String> list = mapper.getSdeptWithGrade();
                            if(list.size()==0){
                                System.out.println("成绩信息为空!");
                            }else{
                                set.addAll(list);
                                for(String dept:set){
                                    System.out.println(dept+"系的学生排名如下");
                                    List<Student> students = mapper.getStudentWithGrade(dept);
                                    for(Student student:students){
                                        List<Integer> grades = scMapper.getSCBySno(student.getSno()).stream().map(el->el.getGrade()).collect(Collectors.toList());
                                        double average = grades.stream().reduce(0,(cur,el)->cur+el) / grades.size();
                                        student.setAverage(average);
                                    }
                                    Collections.sort(students);
                                    for(int i=0;i<students.size();i++){
                                        Student student = students.get(i);
                                        System.out.printf("第%d名 %s 平均成绩为%f，各科成绩如下:\n",i+1,student.getSname(),student.getAverage());
                                        System.out.printf("%6s %6s\n","课程名","成绩");
                                        for(SC sc:scMapper.getSCBySno(student.getSno())){
                                            Course course = courseMapper.getCourse(sc.getCno());
                                            System.out.printf("%6s %d\n",course.getCname(),sc.getGrade());
                                        }
                                    }
                                }
                            }
                        }catch(Exception ex){
                            ex.printStackTrace();
                        }
                        break;
                }
                if(mode==1){
                    break;
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
