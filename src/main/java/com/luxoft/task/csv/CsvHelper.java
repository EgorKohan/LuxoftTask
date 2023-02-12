package com.luxoft.task.csv;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CsvHelper {

    public static <T> List<T> readCsvToList(String[] headers, InputStream in, Function<CSVRecord, T> mappingFunction) throws IOException {
        CSVParser parser = CSVFormat.DEFAULT.
                withHeader(headers)
                .withFirstRecordAsHeader()
                .parse(new InputStreamReader(in));

        List<T> list = new ArrayList<>();
        parser.forEach(csvRecord -> mappingFunction.andThen(list::add).apply(csvRecord));
        return list.stream().filter(Objects::nonNull).distinct().toList();
    }


}