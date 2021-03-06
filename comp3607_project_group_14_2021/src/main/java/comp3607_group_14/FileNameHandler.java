package comp3607_group_14;

import java.util.regex.Pattern;

/**
 * This class provides functionality on the renaming of files
 */
public class FileNameHandler {

    /**
     * This method checks if a file needs renaming
     * 
     * @param fileName
     * @param studentData
     * @return boolean
     */
    public boolean needsNameChange(String fileName, StudentData studentData) {

        String[] fileNameParts = fileName.split("_");

        if (!fileNameParts[0].equals(studentData.getFullName()))
            return true;

        if (!fileNameParts[1].equals(studentData.getIdentifier()))
            return true;

        if (!fileNameParts[2].equals("assignsubmission") || !fileNameParts[3].equals("file"))
            return true;

        return false;
    }

    /**
     * This method checks a file's name for the full name of a student
     * 
     * @param fileName
     * @param student
     * @return boolean
     */
    public boolean checkFileForFullName(String fileName, StudentData student) {

        String temp1 = fileName.trim().replaceAll(" ", "");
        String name = student.getFullName();
        String temp2 = name.trim().replaceAll(" ", "");
        temp2 = temp2.trim().replaceAll("_", "");

        boolean nameCheck = Pattern.compile(Pattern.quote(temp2), Pattern.CASE_INSENSITIVE).matcher(temp1).find();

        return nameCheck;
    }

    /**
     * This method verifies the file name format
     * 
     * @param fileName
     * @param student
     * @return String
     */
    public String verifyFileNameType(String fileName, StudentData student) {

        if ((fileName.contains(student.getIdentifier())))
            return "basic";

        else
            return "hard";
    }

}
