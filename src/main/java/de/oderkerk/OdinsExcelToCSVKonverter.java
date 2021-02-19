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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Goal that generates the testdata from the given xlsx file to csv files
 */
@Data
@EqualsAndHashCode
@Mojo(name = "convertFiles",defaultPhase = LifecyclePhase.GENERATE_RESOURCES )
public class OdinsExcelToCSVKonverter
    extends AbstractMojo {

    /**
     * Input folder where to find the xlsx file(s)
     */
    @Parameter( property = "oxlscsv-input", defaultValue = "/src/test/resources" )
    private String inputFolder;

    /**
     * Filter which xlsxfiles should be converted
     */
    @Parameter( property = "oxlscsv-input.inputfilter",defaultValue = "*")
    private String[] inputFilter;

    /**
     * Target path for the generated csv files
     */
    @Parameter( property = "oxlscsv-output", defaultValue = "/src/test/resources" )
    private String outfolder;

    /**
     * Funtion excecuted if goal is executed
     * @throws MojoExecutionException Error during mojo execution
     * @throws MojoFailureException Failure in mojo
     */
    @SneakyThrows
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        FileConverter fileConverter = new FileConverter(getLog());

        try {
        List<File> foundFiles=fileConverter.scanForInputFiles(inputFolder,inputFilter);

            fileConverter.convertFiles(foundFiles,outfolder);
        } catch (IOException | IllegalArgumentException e ) {
            logErrorData(e);
            throw e;
        }
    }

    private void logErrorData(Exception e) {
        getLog().error("Error during generating csv files. " + e);
        getLog().error(" Input used " + this.getInputFolder() +" for output " + this.getOutfolder() +" with filter " + Arrays.toString(this.inputFilter));
    }


}
