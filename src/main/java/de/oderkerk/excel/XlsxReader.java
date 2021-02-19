package de.oderkerk.excel;
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
import org.apache.maven.plugin.logging.Log;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to perform all read operations of xlsx files
 */
public class XlsxReader {
    private Log log;

    /**
     * Set the maven logger
     * @param log maven logger
     */
    public XlsxReader(Log log){
        this.log = log;
    }

    /**
     * Read the workbook
     * @param xlsxFile to get the workbook from
     * @return workbook of xlsx file
     * @throws IOException Error during read operation
     */
    public Workbook getWorkbook(File xlsxFile) throws IOException {
        log.info("Getting workbook of file "+xlsxFile.getName());
        try(InputStream inp = new FileInputStream(xlsxFile)){
            return WorkbookFactory.create(inp);
        }
    }

    /**
     * Read all sheets of a workbook
     * @param wb workbook to be read
     * @return List of sheets
     */
    public List<Sheet> readAllSheetsOfWorkbook(Workbook wb)
    {
        log.info("Getting all Sheets of workbook");
        List<Sheet> sheetList = new ArrayList<>();
        for (int i= 0 ; i<wb.getNumberOfSheets(); i++) {
            sheetList.add(wb.getSheetAt(i));
        }
        log.info(sheetList.size() +" Sheets found in workbook");
        return sheetList;
    }

    /**
     * Closes the workbook
     * @param wb to be closed
     * @throws IOException error during file operations
     */
    public void closeWorkbook(Workbook wb) throws IOException {
        log.debug("Closing workbook");
        wb.close();
    }

}
