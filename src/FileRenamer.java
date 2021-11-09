import java.io.File;
import java.util.ArrayList;

public class FileRenamer {

    String destination;
    StudentDataCollection studentDataCollection;
    Iterator iterator;

    public FileRenamer(String destination, ArrayList<StudentData> studentData) {
        studentDataCollection = new StudentDataCollection(studentData);
        this.destination = destination;
        iterator = studentDataCollection.createIterator();
    }

    public void renameFiles() {

        ArrayList<String> fileNames = getFileNames();

        for (String str : fileNames) {

            File fileToRename = new File(destination + "/" + str);

            while (iterator.hasNext()) {

                StudentData student = (StudentData) iterator.next();
                String fullName = student.getFullName();

                if (getFullNameFromPDF(str).equals(fullName)) {

                    String newFileName = "";
                    newFileName += fullName + "_";
                    newFileName += student.getIdentifier() + "_";
                    newFileName += "assignsubmission_file_";
                    newFileName += getOriginalFileNameFromPDF(str);
                    newFileName += ".pdf";

                    File renameFile = new File(destination + "/" + newFileName);

                    boolean flag = fileToRename.renameTo(renameFile);

                    if (flag == false)
                        System.out.println("Error renaming " + str);

                    System.out.println(str + "\n\nRenamed to:\n\n" + newFileName + "\n\n");
                    printDashes(60);

                    iterator.reset();
                    break;

                }
            }
        }
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

    private String getFullNameFromPDF(String str) {

        String fileNameParts[] = str.split("_");
        String fullName = "";
        int i = 1;

        while (fileNameParts[i].matches(".*[a-z].*")) {

            if (fullName.equals(""))
                fullName += fileNameParts[i];
            else
                fullName += " " + fileNameParts[i];
            i++;
        }
        return fullName;
    }

    private String getOriginalFileNameFromPDF(String str) {

        String fileNameParts[] = str.split("_");
        String originalFileName = "";
        int i = 1;
        int arraySize = fileNameParts.length;

        while (fileNameParts[i].matches(".*[a-z].*"))
            i++;

        while (i + 2 < arraySize) {
            originalFileName += fileNameParts[i];
            i++;
            if (i + 2 != arraySize)
                originalFileName += "_";
        }

        return originalFileName;
    }

    private void printDashes(int n) {

        for (int i = 0; i < n; i++)
            System.out.print("-");
        System.out.println("\n\n");
    }

}
