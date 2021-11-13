import java.io.File;
import java.nio.file.*;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {

        Path currentActiveDirectory = Paths.get("").toAbsolutePath();

        String source = currentActiveDirectory.normalize().toString();

        source = source.replace("\\", "/");

        source += "/filesToRename";

        String destination = source + "/renamedFiles";

        CSVReader csvReader = new CSVReader(source);
        csvReader.readCSV();

        FolderHandler folderHandler = new FolderHandler(source, destination);

        FileCopier fileCopier = new FileCopier(source, destination);

        FileRenamer fileRenamer = new FileRenamer(destination, csvReader.getStudentData());

        System.out.println(fileRenamer.renameFiles() + " files renamed.");

        // folderHandler.checkEmptyFolder(source);

        // folderHandler.createDestinationFolder();

        System.out.println("Done");

    }
}
