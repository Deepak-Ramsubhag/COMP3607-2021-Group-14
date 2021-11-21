package com.example;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertTrue;


class AppTest {
    //Uzip Variables
    String zipsource = "C:/Users/jiles/Documents/GitHub/COMP3607-2021-Group-14/filesToRename/sample3 case_i.zip";
    String zipdest = "C:/Users/jiles/Documents/GitHub/COMP3607-2021-Group-14/filesToRename/";
    UnzipFiles uzo = new UnzipFiles();
    
    @Test
    void testUnzip() {
        boolean test = uzo.unzip(zipsource, zipdest);
        
        assertTrue(test);
    }

    
}
