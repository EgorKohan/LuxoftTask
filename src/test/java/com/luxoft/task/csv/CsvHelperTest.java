package com.luxoft.task.csv;

import com.luxoft.task.models.Record;
import com.luxoft.task.utils.DateUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

class CsvHelperTest {

    @Test
    @SneakyThrows
    void givenValidCSV_whenRead_thenOK() {
        String[] headers = {"ID", "NAME", "DESCRIPTION", "UPDATED_TIMESTAMP"};
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/csvs/valid.csv");
        Record record = new Record("1", "EGOR", "RECORD 1", DateUtils.parseDateOrNull("2011-12-03T10:15:30", DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        List<Record> records = CsvHelper.readCsvToList(headers, resourceAsStream, csvRecord -> new Record(csvRecord.get("ID"),
                        csvRecord.get("NAME"),
                        csvRecord.get("DESCRIPTION"),
                        DateUtils.parseDateOrNull(csvRecord.get("UPDATED_TIMESTAMP"), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                )
        );
        assertThat(records, hasItem(record));
    }

    @Test
    @SneakyThrows
    void givenValidCSV_whenReadWithInvalidHeaders_thenOK() {
        String[] headers = {"ID", "NICKNAME", "DESCRIPTION"};
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/csvs/valid.csv");
        Record record = new Record("1", "EGOR", "RECORD 1", DateUtils.parseDateOrNull("2011-12-03T10:15:30", DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        List<Record> records = CsvHelper.readCsvToList(headers, resourceAsStream, csvRecord -> new Record(csvRecord.get("ID"),
                        csvRecord.get("NAME"),
                        csvRecord.get("DESCRIPTION"),
                        DateUtils.parseDateOrNull(csvRecord.get("UPDATED_TIMESTAMP"), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                )
        );
        assertThat(records, hasItem(record));
    }

    @Test
    @SneakyThrows
    void givenValidCSV_whenReadInvalidColumn_thenException() {
        String[] headers = {"ID", "NAME", "DESCRIPTION", "UPDATED_TIMESTAMP"};
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/csvs/valid.csv");

        Executable executable = () -> CsvHelper.readCsvToList(headers, resourceAsStream, csvRecord -> new Record(csvRecord.get("ID"),
                        csvRecord.get("NICKNAME"),
                        csvRecord.get("DESCRIPTION"),
                        DateUtils.parseDateOrNull(csvRecord.get("TIMESTAMP"), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                )
        );


        Assertions.assertThrows(IllegalArgumentException.class, executable, "Mapping for NICKNAME");
    }

}