# Odins xlsx to csv File Converter Plugin

## System Requirements

Component | Version
-------|---------
Maven |	3.6.0
JDK	| 1.8
Memory	| No minimum requirement.
Disk Space |	No minimum requirement.

At this time only tested with Java 1.8 . More versions will be tested soon.
## Description
With this plugin you can extract testdata from a xlsx file and save each tab of the input file automatically
to separate csv files.
<br>Please note that you should use unique tab names in your xlsx file, because during the convertion existing files will be overridden without warning.
## Usage
You should specify the version in your project's plugin configuration:
```<project>
...
<build>
 <!-- To define the plugin version in your parent POM -->
 <pluginManagement>
  <plugins>
    <plugin>
      <groupId>de.oderkerk</groupId>
      <artifactId>OdinsExcelToCSVFileConverterPlugin</artifactId>
      <version>1.0.3</version>
     </plugin>
...
  </plugins>
 </pluginManagement>
 <!-- To use the plugin goals in your POM or parent POM -->
 <plugins>
  <plugin>
    <groupId>de.oderkerk</groupId>
    <artifactId>OdinsExcelToCSVFileConverterPlugin</artifactId>
    <version>1.0.3</version>
    <configuration>
            <inputFilter>
               <filter>*</filter>
            </inputFilter>
            <inputFolder> folder with xlsx </inputFolder>
            <outfolder> targetfolder to save csv </outfolder>
    </configuration>
  </plugin>
...
 </plugins>
</build>
...
</project>
``` 
## Goals

Goal | Description
-----| ----------
OdinsExcelToCSVFileConverterPlugin:convertFiles | Goal to convert the xlsx file(s) to csv file(s)

## Parameters and Config

To use the plugin you have to specify the following parameters:

Parameter | Description | Example
----------|-------------|--------
inputFilter | Add filters for selecting the correct files in the input folder. Multiply filters are combined as OR. Wildcards are supported. If no filter is given wildcard * is assumed (Optional)| \<inputFilter><br> \<filter>xxx\</filter> <br>\</inputFilter>
outputFolder | Place to save the csv files | \<outputFolder>scr/test/resources\</outputFolder>
inputFolder | Place where the xlsx files are stored | \<inputFolder>src/test/resources\</inputFolder>

    
To start the generation automatically during the build process
you can set the following configuration to the plugin:

```
                 <executions>
                    <execution>
                        <phase>pre-integration-test</phase>
                        <goals>
                            <goal>convertFiles</goal>
                        </goals>
                    </execution>
                </executions>
                
```






