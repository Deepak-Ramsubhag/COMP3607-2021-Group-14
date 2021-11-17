package com.comp36062021group14;
import java.util.ArrayList;

public class StudentDataGenerator {

    ArrayList<StudentData> studentData;

    public StudentDataGenerator() {
        studentData = new ArrayList<StudentData>();
    }

    public void generateList() {
        StudentData student = new StudentData(123456, "Deepak Ramsubhag");
        studentData.add(student);

        StudentData student2 = new StudentData(654321, "Joe Doe Shmoe");
        studentData.add(student2);

        StudentData student3 = new StudentData(987654, "Jane Doe");
        studentData.add(student3);

    }

    public ArrayList<StudentData> getStudentData() {
        return studentData;
    }

}
