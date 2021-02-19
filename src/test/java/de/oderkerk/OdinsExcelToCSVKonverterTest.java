package de.oderkerk;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * OdinsExcelToCSVKonverterTest
 */
public class OdinsExcelToCSVKonverterTest {


    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void execute() {
        OdinsExcelToCSVKonverter odinsExcelToCSVKonverter = new OdinsExcelToCSVKonverter();
        String[] filter = new String[1];
        filter[0]="*";
        odinsExcelToCSVKonverter.setInputFilter(filter);
        odinsExcelToCSVKonverter.setInputFolder("src/test/resources");
        odinsExcelToCSVKonverter.setOutfolder("target");
        try {
            odinsExcelToCSVKonverter.execute();
            assertTrue(true);
        } catch (MojoExecutionException | MojoFailureException e) {
            fail(e.getMessage());
        }


    }
    @Test
    void executeFailed() {
        OdinsExcelToCSVKonverter odinsExcelToCSVKonverter = new OdinsExcelToCSVKonverter();
        String[] filter = new String[1];
        filter[0]="*";
        odinsExcelToCSVKonverter.setInputFilter(filter);
        odinsExcelToCSVKonverter.setInputFolder("src/xxxx");
        odinsExcelToCSVKonverter.setOutfolder("src/xxx");
        try {
            odinsExcelToCSVKonverter.execute();
            fail("Exception expected");
        } catch (MojoExecutionException e) {
            fail("wrong exception");
        } catch (MojoFailureException e) {
            fail(e.getMessage());
        } catch (IllegalArgumentException iax)
        {
            assertTrue(true);
        }



    }


}