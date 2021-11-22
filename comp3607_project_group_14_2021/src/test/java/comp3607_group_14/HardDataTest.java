package comp3607_group_14;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

@TestMethodOrder(OrderAnnotation.class)
public class HardDataTest {

    private String source;
    private FolderHandler folderHandler;

    @BeforeEach
    public void initialize() {
        Path currentActiveDirectory = Paths.get("").toAbsolutePath();
        source = currentActiveDirectory.normalize().toString();
        source += File.separator + "testingData" + File.separator + "hard" + File.separator + "filesToRename";

        folderHandler = new FolderHandler();
    }

    @Test
    @Order(1)
    public void testNumFilesInDestinationFolder() {

        boolean greaterThanZero = false;

        if (folderHandler.numFilesInFolder(source) > 0)
            greaterThanZero = true;

        assertTrue(greaterThanZero);
    }

    @Test
    @Order(2)
    public void testUnzippingFolder() {

        String zipFolderName = folderHandler.getZippedFolderName(source);
        assertEquals("sample5.zip", zipFolderName);

        String zippedFolderPath = source + File.separator + zipFolderName;
        System.out.println(zippedFolderPath);
        UnzipFiles unzipper = new UnzipFiles();
        boolean success = unzipper.unzip(zippedFolderPath, source);

        assertTrue(success);
    }

    @Test
    @Order(3)
    public void testReadingCSV() {

        String newSource = source + File.separator + "sample5";

        String CSVName = folderHandler.getCSVName(newSource);

        assertEquals("Sample 5 CSV.csv", CSVName);

        CSVReader csvReader = new CSVReader(newSource, CSVName);

        assertTrue(csvReader.readCSV());
    }

    @Test
    @Order(4)
    public void testCreateDestinationFolder() {

        String destination = source + File.separator + "renamedFiles";

        assertTrue(folderHandler.createDestinationFolder(destination));
    }

    @Test
    @Order(5)
    public void testFileCopier() throws IOException {

        String newSource = source + File.separator + "sample5";
        String destination = source + File.separator + "renamedFiles";

        FileCopier fileCopier = new FileCopier(newSource, destination);

        fileCopier.copyFiles();

        int numFiles = folderHandler.numFilesInFolder(destination);

        assertEquals(49, numFiles);
    }

    @Test
    @Order(6)
    public void testFileRenamer() {

        String newSource = source + File.separator + "sample5";
        String destination = source + File.separator + "renamedFiles";

        CSVReader csvReader = new CSVReader(newSource, "Sample 5 CSV.csv");
        csvReader.readCSV();
        ArrayList<String> originalFileNames = folderHandler.getFileNames(destination);

        FileRenamer fileRenamer = new FileRenamer(destination, csvReader.getStudentData(), originalFileNames);

        int numRenamedFiles = fileRenamer.renameFiles();

        assertEquals(48, numRenamedFiles);

        int numFlaggedFiles = fileRenamer.getNumFlaggedFiles();

        assertEquals(1, numFlaggedFiles);
    }

    @Test
    @Order(7)
    public void testMissingSubmissions() {
        String newSource = source + File.separator + "sample5";
        String destination = source + File.separator + "renamedFiles";

        CSVReader csvReader = new CSVReader(newSource, "Sample 5 CSV.csv");
        csvReader.readCSV();
        ArrayList<String> renamedFileNames = folderHandler.getFileNames(destination);

        MissingSubmissions ms = new MissingSubmissions(destination, csvReader.getStudentData(), renamedFileNames);

        assertEquals(1, ms.writeToCSV());
    }

}
