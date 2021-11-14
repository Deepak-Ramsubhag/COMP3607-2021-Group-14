import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

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

            boolean renamed = false;

            if (originalFileName.contains(".pdf")) {
                File fileToRename = new File(destination + "/" + originalFileName);

                while (iterator.hasNext()) {

                    StudentData student = (StudentData) iterator.next();

                    String identifer = student.getIdentifier();
                    String IDnumber = student.getIDNumber();

                    boolean containsName = checkFileForFullName(originalFileName, student);

                    if (containsName || originalFileName.contains(identifer) || originalFileName.contains(IDnumber)) {

                        if (!needsNameChange(originalFileName, student)) {
                            System.out.println(originalFileName + " does not need renaming.\n");
                            iterator.reset();
                            renamed = true;
                            break;
                        }

                        String fileType = verifyFileNameType(originalFileName, student);

                        String newFileName = "";

                        if (fileType.equals("medium"))
                            newFileName = renameFileMedium(originalFileName, student);

                        else if (fileType.equals("hard"))
                            newFileName = renameFileHard(originalFileName, student);

                        File renameFile = new File(destination + "/" + newFileName);

                        boolean flag = fileToRename.renameTo(renameFile);

                        if (flag == false)
                            renamed = false;

                        else {
                            System.out.println(originalFileName + " Renamed to: " + newFileName + "\n");
                            renamed = true;
                            count++;
                        }

                        iterator.reset();
                        break;
                    }

                }
                if (!renamed)
                    System.out.println("Error renaming " + originalFileName + "\n");
            }
            iterator.reset();
        }
        return count;
    }

    private boolean checkFileForFullName(String fileName, StudentData student) {

        String temp1 = fileName.trim().replaceAll(" ", "");

        String name = student.getFullName();
        String temp2 = name.trim().replaceAll(" ", "");
        temp2 = temp2.trim().replaceAll("_", "");

        boolean nameCheck = Pattern.compile(Pattern.quote(temp2), Pattern.CASE_INSENSITIVE).matcher(temp1).find();

        return nameCheck;
    }

    private String renameFileHard(String fileName, StudentData student) {
        String newFileName = "";
        newFileName += student.getFullName() + "_";
        newFileName += student.getIdentifier() + "_";
        newFileName += "assignsubmission_file_";
        newFileName += fileName;

        return newFileName;

    }

    private String renameFileMedium(String fileName, StudentData student) {
        String newFileName = "";
        newFileName += student.getFullName() + "_";
        newFileName += student.getIdentifier() + "_";
        newFileName += "assignsubmission_file_";
        String originalSubmissionName = getOriginalSubmissionName(fileName, student);
        newFileName += originalSubmissionName;

        return newFileName;

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
            return true;

        if (!fileNameParts[1].equals(studentData.getIdentifier()))
            return true;

        if (!fileNameParts[2].equals("assignsubmission") || !fileNameParts[3].equals("file"))
            return true;

        return false;
    }

    private String verifyFileNameType(String fileName, StudentData student) {

        if ((fileName.contains(student.getIdentifier())))
            return "medium";

        else
            return "hard";
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
