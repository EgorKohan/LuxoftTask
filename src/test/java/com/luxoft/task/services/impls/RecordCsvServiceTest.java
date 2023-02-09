package com.luxoft.task.services.impls;

import com.luxoft.task.models.Record;
import com.luxoft.task.utils.DateUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

class RecordCsvServiceTest {

    private final RecordCsvService recordCsvService = new RecordCsvService();

    @Test
    @SneakyThrows
    void whenConsumeCsvFile_thenOK() {
        MultipartFile multipartFile = new MockMultipartFile("report.csv", "report.csv", "text/csv", this.getClass().getResourceAsStream("/csvs/valid.csv"));

        List<Record> records = recordCsvService.csvToList(multipartFile);

        assertThat(records, not(empty()));
    }

    @Test
    @SneakyThrows
    void whenConsumeNotCsvFile_thenException() {
        MultipartFile multipartFile = new MockMultipartFile("report.csv", "report.csv", "img/png", this.getClass().getResourceAsStream("/csvs/valid.csv"));

        Executable executable = () -> recordCsvService.csvToList(multipartFile);

        Assertions.assertThrows(ResponseStatusException.class, executable);
    }

    @Test
    void givenValidCsv_whenRead_thenOK() {
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/csvs/valid.csv");
        Record record = new Record("1", "EGOR", "RECORD 1", DateUtils.parseDateOrNull("2011-12-03T10:15:30", DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        List<Record> records = recordCsvService.csvToList(resourceAsStream);

        assertThat(records, hasItem(record));
    }

    @Test
    void givenCsvWithEmptyField_whenRead_thenOK() {
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/csvs/emptyFields.csv");
        Record record = new Record("1", "", "", null);

        List<Record> records = recordCsvService.csvToList(resourceAsStream);

        assertThat(records, hasItem(record));
    }

    @Test
    void givenCsvWithEmptyId_whenRead_thenEmptyList() {
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/csvs/emptyIdCortage.csv");

        List<Record> records = recordCsvService.csvToList(resourceAsStream);

        assertThat(records, empty());
    }

    @Test
    void givenCsvWithInvalidDateFormat_whenRead_thenDateIsNull() {
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/csvs/invalidDateFormat.csv");

        List<Record> records = recordCsvService.csvToList(resourceAsStream);
        Record record = records.get(0);

        assertThat(record, notNullValue());
        assertThat(record.getUpdatedTimestamp(), nullValue());
    }

    @Test
    void givenHighLoadedFile_whenRead_thenOK(){
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/csvs/loadedFile.csv");

        List<Record> records = recordCsvService.csvToList(resourceAsStream);

        assertThat(records, hasSize(299));
    }

}