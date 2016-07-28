package com.dellnaresh.csv;

import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by nmiriyal on 27/07/2016.
 */
@Component
public class CsvReader implements Reader {
    private Logger logger = LoggerFactory.getLogger(CsvReader.class);

    @Override
    public List<String[]> read(String file) {
        CSVReader reader = new CSVReader(readCSVFile(file));
        try {
            return reader.readAll();
        } catch (IOException e) {
            logger.error("Error reading the file");
        }
        return null;
    }

    private java.io.Reader readCSVFile(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        try {
            return new FileReader(file);
        } catch (FileNotFoundException e) {
            logger.error("Error reading the file", e);
        }
        return null;
    }
}
