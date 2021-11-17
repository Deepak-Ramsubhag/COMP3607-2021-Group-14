package com.comp36062021group14;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CSVReader {

    String source;
    private ArrayList<StudentData> studentData;

    public CSVReader(String source) {
        studentData = new ArrayList<StudentData>();
        this.source = source;
    }

    public boolean readCSV() {

        try {

            FileReader csvFile = new FileReader(source + "/Sample 5 CSV.csv");
            Scanner scanner = new Scanner(csvFile);
            scanner.useDelimiter(",");

            scanner.nextLine();

            while (scanner.hasNext()) {

                StudentData student = new StudentData();

                String identifier = scanner.next();
                identifier = identifier.substring(12);
                student.setIdentifier(identifier);
                student.setFullName(scanner.next());
                student.setIDNumber(scanner.next());
                student.setEmail(scanner.next());
                student.setStatus(scanner.next());
                student.setGrade(scanner.next());
                student.setMaximumGrade(scanner.next());
                student.setChangeStatus(scanner.next());

                String str = scanner.next();
                if (str.startsWith("\""))
                    str += "," + scanner.next() + "," + scanner.next();

                student.setLastModified(str);

                str = scanner.nextLine();
                if (str.equals(","))
                    student.setFeedback("");

                else {
                    str = str.substring(1);
                    student.setFeedback(str);
                }
                studentData.add(student);
            }
            scanner.close();
            return true;
        }

        catch (IOException e) {
            System.out.println(e);
            return false;
        }
    }

    public ArrayList<StudentData> getStudentData() {
        return studentData;
    }

    public void printStudentData() {

        for (StudentData s : studentData) {

            System.out.print(s.getIdentifier() + "\t");
            System.out.print(s.getFullName() + "\t");
            System.out.print(s.getIDNumber() + "\t");
            System.out.print(s.getEmail() + "\t");
            System.out.print(s.getStatus() + "\t");
            System.out.print(s.getGrade() + "\t");
            System.out.print(s.getMaximumGrade() + "\t");
            System.out.print(s.getChangeStatus() + "\t");

            System.out.print(s.getLastModified() + "\t");

            if (s.getFeedback().equals(""))
                System.out.println("No comments");
            else
                System.out.println(s.getFeedback());
        }
    }

}
