import java.io.File;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.IOException;

public class MissingSubmissions {

    String destination;
    StudentDataCollection studentDataCollection;
    Iterator iterator;
    int numMissingSubmissions = 0;

    public MissingSubmissions(String destination, ArrayList<StudentData> studentData) {
        this.destination = destination;
        this.studentDataCollection = new StudentDataCollection(studentData);
        this.iterator = studentDataCollection.createIterator();
    }

    private String findMissingFiles() {

        ArrayList<String> fileNames = this.getFileNames();
        StringBuilder builder = new StringBuilder();
        boolean found = false;

        builder.append(
                "Identifier,Full name,ID number,Email address,Status,Grade,Maximum Grade,Grade can be changed,Last modified (grade),Feedback comments"
                        + "\n");

        while (iterator.hasNext()) {
            StudentData student = (StudentData) iterator.next();
            search: for (String filename : fileNames) {

                if (filename.contains(student.getFullName()) == true) {
                    found = true;
                    break search;
                }
            }

            if (found == false) {
                builder.append("Participant " + student.getIdentifier() + "," + student.getFullName() + ",");
                builder.append(student.getIDNumber() + "," + student.getEmail() + ",");
                builder.append(student.getStatus() + "," + student.getGrade() + ",");
                builder.append(student.getMaximumGrade() + "," + student.getChangeStatus() + ",");
                builder.append(student.getLastModified() + "," + student.getFeedback() + "\n");
                numMissingSubmissions++;
            }
            found = false;
        }

        iterator.reset();
        return builder.toString();
    }

    private ArrayList<String> getFileNames() {

        ArrayList<String> fileNames = new ArrayList<String>();

        try {
            File[] files = new File(destination).listFiles();

            for (File file : files) {

                if (file.isFile()) {
                    fileNames.add(file.getName());
                }
            }
        }

        catch (Exception e) {
            System.out.println("Destination path error");
        }

        return fileNames;
    }

    public int writeToCSV() {

        try {
            PrintWriter writer = new PrintWriter(destination + "/Missing Submissions.csv");
            writer.write(findMissingFiles());
            writer.close();
            return numMissingSubmissions;
        }

        catch (IOException e) {
            System.out.println(e);
            return 0;
        }
    }

}