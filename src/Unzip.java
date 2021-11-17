import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzip {

    private static final int BUFFER_SIZE = 4096;

    public void unzip(String zipFilePath, String destDir) {

        File dir = new File(destDir);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        FileInputStream fis;

        try {

            fis = new FileInputStream(zipFilePath);
            ZipInputStream inputStream = new ZipInputStream(fis);
            ZipEntry zip = inputStream.getNextEntry();

            while (zip != null) {

                String filePath = destDir + File.separator + zip.getName();

                if (!zip.isDirectory()) {
                    // if the entry is a file, extracts it
                    extractFile(inputStream, filePath);
                } else {
                    // if the entry is a directory, make the directory
                    File newFile = new File(filePath);
                    newFile.mkdirs();
                }

                inputStream.closeEntry();
                zip = inputStream.getNextEntry();
            }
            inputStream.closeEntry();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
}