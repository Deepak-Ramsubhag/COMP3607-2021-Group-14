import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CSVReader {

    private String source;
    private ArrayList<StudentData> studentData;
    private String CSVName;

    public CSVReader(String source, String CSVName) {
        studentData = new ArrayList<StudentData>();
        this.source = source;
        this.CSVName = CSVName;
    }

    public boolean readCSV() {

        try {

            FileReader csvFile = new FileReader(source + File.separator + CSVName);
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

}
