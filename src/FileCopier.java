import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

public class FileCopier {

    private String source;
    private String destination;

    public FileCopier(String source, String destination) {
        this.destination = destination;
        this.source = source;
    }

    public void copyFiles() throws IOException {
        Path src = Paths.get(source);
        Path dest = Paths.get(destination);

        Stream<Path> files = Files.walk(src, 1);

        files.forEach(file -> {
            try {
                if (file.compareTo(dest) != 0) {
                    Files.copy(file, dest.resolve(src.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        files.close();
    }
}