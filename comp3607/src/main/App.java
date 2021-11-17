import java.nio.file.*;

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

        MissingSubmissions missingSubmissions = new MissingSubmissions(destination, csvReader.getStudentData());

        if (folderHandler.checkEmptyFolder(source))
            return;

        else {
            if (!folderHandler.createDestinationFolder())
                return;

            else {
                fileCopier.copier();
                System.out.println(fileRenamer.renameFiles() + " files renamed.");
            }
        }

        int numMissingSubmissions = missingSubmissions.writeToCSV();

        System.out.println("Number of missing submissions: " + numMissingSubmissions);

        return;

    }
}
