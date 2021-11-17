import java.io.File;

public class FolderHandler {

    private String source;
    private String destination;

    public FolderHandler(String source, String destination) {
        this.destination = destination;
        this.source = source;
    }

    public boolean checkEmptyFolder(String location) {

        File directory = new File(location);

        if (directory.isDirectory()) {
            String[] files = directory.list();
            if (files.length > 0) {
                System.out.println("Source folder is not empty.\n");
                return false;
            } else {
                System.out.println("Source folder is empty.\n");
                return true;
            }
        }

        else {
            System.out.println("Source directory is not valid.\n");
            return true;
        }
    }

    public boolean createDestinationFolder() {

        File directory = new File(destination);

        if (directory.exists()) {
            System.out.println("Destination folder already exists.\n");
            return false;
        }

        else {
            boolean flag = directory.mkdir();

            if (flag) {
                System.out.println("Destination folder successfully created.\n");
                return true;
            }

            else {
                System.out.println("Destination folder not successfully created.");
                return false;
            }
        }
    }

}
