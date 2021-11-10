import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

public class FileCopier {

    private String source;
    private String destination;

    public FileCopier(String source, String destination){
        this.destination = destination;
        this.source = source;
    }

    public void copier () throws IOException{
        Path src  = Paths.get(source);
        Path dest = Paths.get(destination);

        Stream<Path> files = Files.walk(src);


        files.forEach(file -> {
            try {
                Files.copy(file, dest.resolve(src.relativize(file)),
                        StandardCopyOption.REPLACE_EXISTING);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });
        files.close();
    }
}
    

