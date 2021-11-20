package com.example;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

public class FileCopier {

    private String source;
    private String destination;
    private FileFilter filter;

    public FileCopier(String source, String destination) {
        this.destination = destination;
        this.source = source;
        filter = new FileFilter() {

            @Override
            public boolean accept(File file) {
                if (file.getName().endsWith(".pdf")) {
                    return true;
                }
                return false;
            }

        };
    }

    public void copyFiles() throws IOException {
        Path src = Paths.get(source);
        Path dest = Paths.get(destination);

        Stream<Path> files = Files.walk(src, 1);

        files.forEach(file -> {

            try {
                if (filter.accept(file.toFile())) {
                    Files.copy(file, dest.resolve(src.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        files.close();
    }
}