package com.luxoft.task.services.impls;

import com.luxoft.task.csv.CsvHelper;
import com.luxoft.task.models.Record;
import com.luxoft.task.services.CsvService;
import com.luxoft.task.utils.DateUtils;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class RecordCsvService implements CsvService<Record> {

    private static final String[] HEADERS = {"ID", "NAME", "DESCRIPTION", "UPDATED_TIMESTAMP"};

    @Override
    public List<Record> csvToList(@NonNull MultipartFile file) {
        if (!"text/csv".equals(file.getContentType()))
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "File must be CSV format!");
        InputStream inputStream;
        try {
            log.info("Reading file {}", file.getOriginalFilename());
            inputStream = file.getInputStream();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return csvToList(inputStream);
    }

    @Override
    public List<Record> csvToList(InputStream fileInputStream) {
        try {
            return CsvHelper.readCsvToList(HEADERS, fileInputStream,
                    csvRecord -> {
                        String id = csvRecord.get("ID");
                        if (id.isBlank()) return null;

                        LocalDateTime ldt = DateUtils.parseDateOrNull(csvRecord.get("UPDATED_TIMESTAMP"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

                        return new Record(
                                id,
                                csvRecord.get("NAME"),
                                csvRecord.get("DESCRIPTION"),
                                ldt
                        );
                    }
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            log.debug("Error occurred: ", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            log.debug("Error occurred: ", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CSV file doesn't fit needed standard");
        }
    }

}
