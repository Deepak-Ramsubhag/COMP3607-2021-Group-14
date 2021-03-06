package comp3607_group_14;

import java.io.File;
import java.util.ArrayList;

/**
 * This class is responsible for renaming files according to the defined naming convention
 */
public class FileRenamer {

    /**
     * This variable stores the file path of the renamed assignment files
     */
    private String destination;

    /**
     * This variable stores a collection of student data
     */
    private StudentDataCollection studentDataCollection;

    /**
     * This variable stores an object which is used to traverse the collection of
     * studentData
     */
    private Iterator iterator;

    /**
     * This variable stores an arraylist of studentData
     */
    private ArrayList<String> fileNames;

    /**
     * This variable stores the strategy that is used to rename files
     */
    private RenamingStrategy strategy;

    /**
     * This variable stores an object which is used to provide functionality for
     * renaming files
     */
    private FileNameHandler fileNameHandler;

    /**
     * This variable stores the number of flagged files
     */
    private int numFlaggedFiles = 0;

    /**
     * This method instantiates a FileRenamer object
     */
    public FileRenamer(String destination, ArrayList<StudentData> studentData, ArrayList<String> fileNames) {
        studentDataCollection = new StudentDataCollection(studentData);
        this.destination = destination;
        this.fileNames = fileNames;
        iterator = studentDataCollection.createIterator();
        fileNameHandler = new FileNameHandler();
    }

    /**
     * This method renames all the files in the destination folder and returns the
     * number of renamed files
     * 
     * @return int
     */
    public int renameFiles() {

        int count = 0;
        numFlaggedFiles = 0;

        try {
            for (String originalFileName : fileNames) {

                boolean renamed = false;

                File fileToRename = new File(destination + File.separator + originalFileName);

                while (iterator.hasNext()) {

                    StudentData student = (StudentData) iterator.next();

                    String identifer = student.getIdentifier();
                    String IDnumber = student.getIDNumber();

                    boolean containsName = fileNameHandler.checkFileForFullName(originalFileName, student);

                    if (containsName || originalFileName.contains(identifer) || originalFileName.contains(IDnumber)) {

                        if (!fileNameHandler.needsNameChange(originalFileName, student)) {
                            System.out.println(originalFileName + " does not need renaming.\n");
                            iterator.reset();
                            renamed = true;
                            break;
                        }

                        String fileNameType = fileNameHandler.verifyFileNameType(originalFileName, student);
                        String newFileName = getNewFileName(originalFileName, student, fileNameType);
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
                if (!renamed) {
                    System.out.println("Error renaming " + originalFileName + "\n");
                    numFlaggedFiles++;
                }

                iterator.reset();
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Error renaming files.");
        }
        return count;
    }

    /**
     * This method sets the renaming strategy to be used
     * 
     * @param strategy
     */
    private void setStrategy(RenamingStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * This method gets the new file name of a file
     * 
     * @param fileName
     * @param student
     * @param fileNameType
     * @return String
     */
    private String getNewFileName(String fileName, StudentData student, String fileNameType) {

        String newFileName = "";

        if (fileNameType.equals("basic"))
            setStrategy(new BasicRenamingStrategy());

        if (fileNameType.equals("hard"))
            setStrategy(new HardRenamingStrategy());

        newFileName = strategy.renameFile(fileName, student);

        return newFileName;
    }

    /**
     * This method returns the number of flagged files
     * 
     * @return int
     */
    public int getNumFlaggedFiles() {
        return numFlaggedFiles;
    }
}
