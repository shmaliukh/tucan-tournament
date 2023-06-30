package xyz.vshmaliukh.tucantournament;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class ReadDataTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/data_example_basketball.csv", numLinesToSkip = 1)
    void readDataFromFileTest(String playerName,
                              String nickname,
                              int number,
                              String teamName,
                              int scoredPoints,
                              int rebounds,
                              int assists) {

    }

}
