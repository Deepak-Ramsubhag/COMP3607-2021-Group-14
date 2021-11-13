import java.io.File;
import java.io.FilenameFilter;
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

    public int renameFiles() {

        int count = 0;

        ArrayList<String> fileNames = getFileNames();

        for (String originalFileName : fileNames) {

            File fileToRename = new File(destination + "/" + originalFileName);

            while (iterator.hasNext()) {

                StudentData student = (StudentData) iterator.next();

                if (needsNameChange(originalFileName, student)) {
                    System.out.println(originalFileName + " does not need renaming.\n");
                    iterator.reset();
                    break;
                }

                if (verifyFileNameType(originalFileName, student) == 1) {

                    String newFileName = "";
                    newFileName += student.getFullName() + "_";
                    newFileName += student.getIdentifier() + "_";
                    newFileName += "assignsubmission_file_";
                    String originalSubmissionName = getOriginalSubmissionName(originalFileName, student);
                    newFileName += originalSubmissionName;

                    File renameFile = new File(destination + "/" + newFileName);

                    boolean flag = fileToRename.renameTo(renameFile);

                    if (flag == false)
                        System.out.println("Error renaming " + originalFileName);

                    else {
                        System.out.println(originalFileName + " Renamed to: " + newFileName + "\n");
                        count++;
                    }

                    iterator.reset();
                    break;
                }
            }
        }
        return count;

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

    private boolean needsNameChange(String fileName, StudentData studentData) {

        String[] fileNameParts = fileName.split("_");

        if (!fileNameParts[0].equals(studentData.getFullName()))
            return false;

        if (!fileNameParts[1].equals(studentData.getIdentifier()))
            return false;

        if (!fileNameParts[2].equals("assignsubmission") || !fileNameParts[3].equals("file"))
            return false;

        return true;
    }

    private int verifyFileNameType(String fileName, StudentData student) {

        String fullName = getFullNameFromFileName(fileName);

        if (fullName.equals(student.getFullName()) && (fileName.contains(student.getIdentifier())))
            return 1;

        else
            return 2;
    }

    private String getFullNameFromFileName(String fileName) {

        String fileNameParts[] = fileName.split("_");
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

    private String getOriginalSubmissionName(String fileName, StudentData student) {

        String fileNameParts[] = fileName.split("_");
        String originalSubmission = "";
        int i;
        int arraySize = fileNameParts.length;

        for (i = 0; i < arraySize; i++) {
            if (fileNameParts[i].equals(student.getIdentifier())) {
                i++;
                while (i < arraySize) {
                    originalSubmission += fileNameParts[i];
                    i++;
                    if (i != arraySize)
                        originalSubmission += "_";
                }
                return originalSubmission;
            }
        }

        return "No submission name";
    }

}
