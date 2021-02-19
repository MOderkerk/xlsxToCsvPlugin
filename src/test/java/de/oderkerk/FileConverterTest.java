package de.oderkerk;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FileConverterTest
 */
public class FileConverterTest {

    FileConverter fileConverter;
    @BeforeEach
    void setUp() {
        fileConverter = new FileConverter(new OdinsExcelToCSVKonverter().getLog());
    }

    @AfterEach
    void tearDown() {
    }



    @Test
    void scanForInputFiles2FileFound() {
        String[] filter = new String[]{"Testfile*.xlsx"};
        List<File> filesList=fileConverter.scanForInputFiles("src/test/resources",filter);
        assertEquals(2,filesList.size());
    }
    @Test
    void scanForInputFiles2FileFoundWithEmptyFilter() {
        try {
            String[] filter = new String[]{" "};
            List<File> filesList = fileConverter.scanForInputFiles("src/test/resources", filter);
            assertEquals(0, filesList.size());
        }
        catch (IllegalArgumentException iax){
            assertNotNull(iax);
        }
    }
    @Test
    void scanForInputFiles2FileFoundWithNullAsFilter() {
        try {
            String[] filter = new String[1];
            List<File> filesList = fileConverter.scanForInputFiles("src/test/resources", filter);
            fail("IllegalArgument Exception missing");
        }
        catch (IllegalArgumentException ex)
        {
            assertTrue(true);
        }
        catch (Exception ex)
        {
            fail("Wrong exception occured");
        }
    }
    @Test
    void scanForInputFilesWrongFileTypesFound() {

        try {
            String[] filter = new String[]{"Testfile*.csv"};
            List<File> filesList = fileConverter.scanForInputFiles("src/test/resources", filter);
        }
        catch (IllegalArgumentException iax)
        {
            assertNotNull(iax);
        }
    }

    @Test
    void convertFileToCSV()
    {
        List<File> fileList = Arrays.asList(new File("src/test/resources/Testfile1.xlsx"));
        try {
            fileConverter.convertFiles(fileList,"target");

        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
}