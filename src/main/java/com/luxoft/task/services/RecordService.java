package com.luxoft.task.services;

import com.luxoft.task.models.Record;

import java.util.Collection;
import java.util.List;

public interface RecordService {

    List<Record> findAll();

    Record findById(String id);

    Integer saveAll(Collection<Record> records);

    Record deleteById(String id);

}
