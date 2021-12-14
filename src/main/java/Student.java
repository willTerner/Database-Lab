import lombok.Data;

@Data
public class Student implements Comparable<Student>{
    private String sname;

    private String sno;

    private Integer sage;

    private String sdept;

    private String scholarship;

    private String ssex;

    private Double average;


    @Override
    public int compareTo(Student o) {
        return -1*(int)(average - o.average);
    }
}
