import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class Unzip {
    private String zippedFile;
    private String destDir;

    public Unzip( String zippedFile, String destDir){
        this.zippedFile = zippedFile;
        this.destDir = destDir;
    }

    private static void unzip(String zipFilePath, String destDir) {
        
        File dir = new File(destDir);

        if(!dir.exists()) {
            dir.mkdirs();
        }
        
        FileInputStream fis;
        byte[] buffer = new byte[1024];

        try {

            fis = new FileInputStream(zipFilePath);
            ZipInputStream inputStream = new ZipInputStream(fis);
            ZipEntry zip = inputStream.getNextEntry();
            
            while(!zip.equals(null)){

                String fileName = zip.getName();
                File newFile = new File(destDir + File.separator + fileName);
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                
                int len;
                while ((len = inputStream.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                
                fos.close();
                inputStream.closeEntry();
                zip = inputStream.getNextEntry();
            }
            //close last ZipEntry
            inputStream.closeEntry();
            inputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
}
