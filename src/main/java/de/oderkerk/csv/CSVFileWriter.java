package de.oderkerk.csv;
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

import java.io.*;
import java.util.List;

/**
 * Class for all write operations of csv files
 */
public class CSVFileWriter {
    private Log log;


    /**
     * Setting the maven logger to this class
     * @param log maven plugin logger
     *
     */
    public CSVFileWriter(Log log) {
        this.log=log;
    }

    /**
     * Write Data to CSV File
     * @param rowsToWrite Input data to wite
     * @param outputFile targetfile
     * @throws IOException error during file operation
     */
    public void writeDataToCSV(List<String> rowsToWrite,File outputFile) throws IOException {

        log.debug("Writing data to file " + outputFile);
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            int i = 0;
            for (String row : rowsToWrite) {
                i++;
                writer.write(row);
                if (i < rowsToWrite.size()) writer.newLine();
            }
        }

    }
}
