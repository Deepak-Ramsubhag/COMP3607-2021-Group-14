package com.example;
import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class FileRenamer {

    private String destination;
    private StudentDataCollection studentDataCollection;
    private Iterator iterator;
    private ArrayList<String> fileNames;
    private RenamingStrategy strategy;

    public FileRenamer(String destination, ArrayList<StudentData> studentData, ArrayList<String> fileNames) {
        studentDataCollection = new StudentDataCollection(studentData);
        this.destination = destination;
        this.fileNames = fileNames;
        iterator = studentDataCollection.createIterator();
    }

    public int renameFiles() {

        int count = 0;

        try {
            for (String originalFileName : fileNames) {

                boolean renamed = false;

                File fileToRename = new File(destination + File.separator + originalFileName);

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

                        String fileNameType = verifyFileNameType(originalFileName, student);
                        String newFileName = renameFile(originalFileName, student, fileNameType);
                        File renameFile = new File(destination + File.separator + newFileName);
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

                iterator.reset();
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Error renaming files.");
        }
        return count;
    }

    public void setStrategy(RenamingStrategy strategy) {
        this.strategy = strategy;
    }

    private boolean checkFileForFullName(String fileName, StudentData student) {

        String temp1 = fileName.trim().replaceAll(" ", "");
        String name = student.getFullName();
        String temp2 = name.trim().replaceAll(" ", "");
        temp2 = temp2.trim().replaceAll("_", "");

        boolean nameCheck = Pattern.compile(Pattern.quote(temp2), Pattern.CASE_INSENSITIVE).matcher(temp1).find();

        return nameCheck;
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

    private String renameFile(String fileName, StudentData student, String fileNameType) {

        String newFileName = "";

        if (fileNameType.equals("medium"))
            setStrategy(new MediumRenamingStrategy());

        if (fileNameType.equals("hard"))
            setStrategy(new HardRenamingStrategy());

        newFileName = strategy.renameFile(fileName, student);

        return newFileName;

    }
}
