package de.oderkerk.excel;

import de.oderkerk.OdinsExcelToCSVKonverter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for XlsxReaderTest
 */
public class XlsxReaderTest {

    XlsxReader xlsxReader ;
    @BeforeEach
    void setUp() {
        xlsxReader = new XlsxReader(new OdinsExcelToCSVKonverter().getLog());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getWorkbook() {

        File f = new File("src/test/resources/Testfile1.xlsx");
        try {
            Workbook wb =xlsxReader.getWorkbook(f);
            assertNotNull(wb);
            wb.close();
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void readAllSheetsOfWorkbook() {
        File f = new File("src/test/resources/Testfile1.xlsx");
        try {
            Workbook wb =xlsxReader.getWorkbook(f);
            assertNotNull(wb);
            List<Sheet> sheetList = xlsxReader.readAllSheetsOfWorkbook(wb);
            assertEquals(1,sheetList.size());
            wb.close();
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void closeWorkbook() {
        File f = new File("src/test/resources/Testfile1.xlsx");
        try {
            Workbook wb =xlsxReader.getWorkbook(f);
            wb.close();
            assertTrue(true);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
}