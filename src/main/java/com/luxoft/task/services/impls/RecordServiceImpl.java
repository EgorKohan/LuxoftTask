package com.luxoft.task.services.impls;

import com.luxoft.task.models.Record;
import com.luxoft.task.repositories.RecordRepository;
import com.luxoft.task.services.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;

    @Autowired
    public RecordServiceImpl(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @Override
    public List<Record> findAll() {
        return recordRepository.findAll();
    }

    @Override
    public Record findById(String id) {
        return recordRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Integer saveAll(Collection<Record> records) {
        List<Record> savedRecords = recordRepository.saveAll(records);
        log.debug("Saved {} records", savedRecords.size());
        return savedRecords.size();
    }

    @Override
    public Record deleteById(String id) {
        Record rec = findById(id);
        recordRepository.deleteById(id);
        log.debug("Record with id {} was deleted", rec.getId());
        return rec;
    }
}
