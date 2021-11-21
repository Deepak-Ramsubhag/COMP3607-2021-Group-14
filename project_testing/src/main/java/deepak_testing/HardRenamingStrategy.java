package deepak_testing;

public class HardRenamingStrategy implements RenamingStrategy {

    public String renameFile(String fileName, StudentData student) {
        String newFileName = "";
        newFileName += student.getFullName() + "_";
        newFileName += student.getIdentifier() + "_";
        newFileName += "assignsubmission_file_";
        String originalSubmissionName = fileName;
        newFileName += originalSubmissionName;

        return newFileName;
    }

}
