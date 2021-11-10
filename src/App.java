import java.io.File;
import java.nio.file.*;

public class App {
    public static void main(String[] args) throws Exception {

        StudentDataGenerator sdg = new StudentDataGenerator();
        sdg.generateList();

        Path currentActiveDirectory = Paths.get("").toAbsolutePath();

        String source = currentActiveDirectory.normalize().toString();

        source = source.replace("\\", "/");

        source += "/filesToRename";

        String destination = source + "/renamedFiles";

        FileCopier fileCopier = new FileCopier(source, destination);

        // File folderChecker = new File(destination);

        // if (folderChecker.length() > 0) {
        // System.out.println(folderChecker.length());
        // System.out.println("renamedFiles folder is not empty.");
        // return;

        // } else
        fileCopier.copyFiles();

        FileRenamer fileRenamer = new FileRenamer(destination, sdg.getStudentData());

        fileRenamer.renameFiles();

        System.out.println("Done");

    }
}
