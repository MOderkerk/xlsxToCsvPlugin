package de.oderkerk;
/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import de.oderkerk.csv.CSVFileWriter;
import de.oderkerk.excel.XlsxReader;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.maven.plugin.logging.Log;
import org.apache.poi.ss.usermodel.*;

import javax.naming.ConfigurationException;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileConverter {
    private static final String FILEDELIMITER="/";
    private final Log log;

    /**
     * Setting the maven logger
     * @param log maven logger
     */
    public FileConverter(Log log)
    {
        this.log=log;
    }

    /**
     * Generated the csv files from the xlsx files in the inputfolder matching the filterrules
     * @param inputFolder Folder to scan for xlsx files
     * @param outputFolder Folder to save the csv files
     * @param inputFilter Folder to scan for files
     * @throws ConfigurationException thrown if no
     */
    public void generateCSVFiles(String inputFolder,String outputFolder,String[] inputFilter) throws ConfigurationException {
        log.info("Generating CSV Files from " + inputFolder +" to " +outputFolder +" with filter : " + Arrays.toString(inputFilter));
        List<File> filesToBeConverted = scanForInputFiles(inputFolder,inputFilter);
        if (filesToBeConverted.isEmpty())
        {
            log.error("No files found in the given folder with the given filter");
            throw new ConfigurationException("No files found in the given folder with the given filter");
        }
    }



    /**
     * Converts the xlsx file to one or multiple csv files executing the following steps:
     * <ul>
     * <li>Read the xlsx File</li>
     * <li>Identify all tabs with data</li>
     * <li>Convert each tab to a separate csv file</li>
     * </ul>
     * @param filesToBeConverted Input files
     * @param outputFolder target folder
     */
    void convertFiles(List<File> filesToBeConverted,String outputFolder) throws IOException {
        XlsxReader reader = new XlsxReader(log);
        for (File file: filesToBeConverted)
        {
            Workbook wb = reader.getWorkbook(file);
            List<Sheet> sheetList=reader.readAllSheetsOfWorkbook(wb);
            for (Sheet sheet:sheetList)
            {
                convertSheetToCSV(sheet,outputFolder);
            }
            reader.closeWorkbook(wb);
        }
    }

    private void convertSheetToCSV(Sheet sheet, String outputFolder) throws IOException {

        List<String> dataForOutput=new ArrayList<>();
        Iterator<Row> rowIterator = sheet.iterator();
        while(rowIterator.hasNext())
        {
            Row row = rowIterator.next();
            Iterator<Cell> cells =row.cellIterator();
            StringBuilder builder=new StringBuilder();

            while(cells.hasNext()){
                Cell cell = cells.next();

                switch (cell.getCellType())
                {
                    case _NONE:
                        break;
                    case STRING:
                    case BLANK:
                        builder.append(cell.getStringCellValue()) ;
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell))
                        {
                            SimpleDateFormat format=new SimpleDateFormat("dd.MM.yyyy");
                            builder.append(format.format(cell.getDateCellValue()));
                        }
                        else {
                            builder.append(cell.getNumericCellValue());
                        }
                        break;
                    case BOOLEAN:
                        builder.append(cell.getBooleanCellValue());
                        break;
                    default:
                }
                if (cells.hasNext())
                {
                    builder.append(";");
                }
            }
            dataForOutput.add(builder.toString());
            CSVFileWriter csvFileWriter = new CSVFileWriter(log);
            File outputfile=new File(outputFolder+FILEDELIMITER+sheet.getSheetName()+".csv");
            csvFileWriter.writeDataToCSV(dataForOutput,outputfile);
        }



    }

    /**
     * Reads the inputfolder and filters the found files using the filter options
     * @param inputFolder folder to scan
     * @param inputFilter filter used during filtering
     * @return List of Files which need to be converted
     */
    List<File> scanForInputFiles(String inputFolder, String[] inputFilter) {
        log.info("scan for input files in folder "+ inputFolder);
        log.info("Only files with the extension xlsx are returned");
        inputFilter = checkFilter(inputFilter);
        File f = new File(inputFolder);
        List<File> filteredFiles = new ArrayList<>();
        for (String filter: inputFilter)
        {
            log.info("Scanning with filter " +filter);
            FileFilter filefilter = new WildcardFileFilter(filter);
            try {
                filteredFiles.addAll(Arrays.asList(f.listFiles(filefilter)));
            }
            catch (NullPointerException nullPointerException)
            {
                log.warn("No files found");
            }
        }
        List<File> resultList=new ArrayList<>();
        for (File file:filteredFiles)
        {
            if (file.getName().endsWith(".xlsx")) resultList.add(file);
        }
        log.debug("File(s) found after filtering " + resultList.toString());
        if (resultList.isEmpty())
        {
            throw new IllegalArgumentException("No files to convert found. Check inputfolder or filter");
        }
        return resultList;
    }

    /**
     * Check the input of the filter. If null Wildcard * is set
     * @param inputFilter filter to be checked
     * @return cleaned filter
     */
    private String[] checkFilter(String[] inputFilter) {
        if (inputFilter == null || inputFilter.length==0 )
        {
            inputFilter = createDummyFilter();
        }
        else
        {
            if (Arrays.stream(inputFilter).allMatch(Objects::isNull)){
                throw new IllegalArgumentException("Filter must not be null");
            }
        }
        return inputFilter;
    }

    /**
     * Setting filter to *
     * @return new dummy filter to get all files
     */
    private String[] createDummyFilter() {
        String[] inputFilter;
        inputFilter = new String[1];
        inputFilter[0]="*";
        return inputFilter;
    }
}
