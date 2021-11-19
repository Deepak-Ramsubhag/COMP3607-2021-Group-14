import java.io.File;
import java.nio.file.*;
import java.util.ArrayList;

public class FileFixer {
    public static void main(String[] args) throws Exception {

        Path currentActiveDirectory = Paths.get("").toAbsolutePath();

        String source = currentActiveDirectory.normalize().toString();

        source += File.separator + "filesToRename";

        String destination = source + File.separator + "renamedFiles";

        FolderHandler folderHandler = new FolderHandler(source, destination);

        FileCopier fileCopier = new FileCopier(source, destination);

        if (folderHandler.numFilesInFolder(source) <= 0) {
            System.out.println("No files found. Exiting . . .\n");
            return;
        }

        else {

            String zf = folderHandler.getZippedFolderName();

            System.out.println(zf + " found.\n");

            if (zf.equals("Multiple incompatible types of files") || zf.equals("Multiple zipped folders")) {
                System.out.println("Exiting. . .\n");
                return;
            }

            if (!zf.equals("No zipped folders")) {
                String zippedFolderPath = source + File.separator + zf;
                Unzip unzipper = new Unzip();
                unzipper.unzip(zippedFolderPath, source);
            }

            String CSVName = folderHandler.getCSVName();

            if (CSVName.equals("No CSVs") || CSVName.equals("Multiple CSVs")) {
                System.out.println(CSVName + " found.\nExiting. . .\n");
                return;
            }

            CSVReader csvReader = new CSVReader(source, CSVName);

            if (!csvReader.readCSV()) {
                System.out.println("Exiting. . .\n");
                return;
            }

            if (!folderHandler.createDestinationFolder()) {
                System.out.println("Exiting. . .\n");
                return;
            }

            fileCopier.copyFiles();

            ArrayList<String> originalFileNames = folderHandler.getFileNames(destination);
            FileRenamer fileRenamer = new FileRenamer(destination, csvReader.getStudentData(), originalFileNames);
            System.out.println(fileRenamer.renameFiles() + " files renamed.");

            ArrayList<String> renamedFileNames = folderHandler.getFileNames(destination);
            MissingSubmissions ms = new MissingSubmissions(destination, csvReader.getStudentData(), renamedFileNames);
            int numMissingSubmissions = ms.writeToCSV();
            System.out.println("Number of missing submissions: " + numMissingSubmissions);

        }
        return;

    }

}
