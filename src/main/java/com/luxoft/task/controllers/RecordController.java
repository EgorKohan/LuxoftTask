package com.luxoft.task.controllers;

import com.luxoft.task.models.Record;
import com.luxoft.task.services.CsvService;
import com.luxoft.task.services.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/records")
public class RecordController {

    private final CsvService<Record> csvService;
    private final RecordService recordService;

    @Autowired
    public RecordController(CsvService<Record> csvService, RecordService recordService) {
        this.csvService = csvService;
        this.recordService = recordService;
    }

    @GetMapping
    public List<Record> findAll() {
        return recordService.findAll();
    }

    @GetMapping("/{id}")
    public Record findById(@PathVariable("id") String id) {
        return recordService.findById(id);
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Object> uploadCsv(@RequestParam("file") MultipartFile file) {
        List<Record> records = csvService.csvToList(file);
        Integer count = recordService.saveAll(records);
        return ResponseEntity.ok(count);
    }

    @DeleteMapping("/{id}")
    public Record deleteById(@PathVariable("id") String id) {
        return recordService.deleteById(id);
    }

}
