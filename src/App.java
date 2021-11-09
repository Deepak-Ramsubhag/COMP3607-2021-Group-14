import java.nio.file.*;

public class App {
    public static void main(String[] args) throws Exception {

        StudentDataGenerator sdg = new StudentDataGenerator();
        sdg.generateList();

        Path currentActiveDirectory = Paths.get("").toAbsolutePath();

        String destination = currentActiveDirectory.normalize().toString();

        destination = destination.replace("\\", "/");

        destination += "/RenamedFiles";

        FileRenamer fileRenamer = new FileRenamer(destination, sdg.getStudentData());

        fileRenamer.renameFiles();

    }
}
