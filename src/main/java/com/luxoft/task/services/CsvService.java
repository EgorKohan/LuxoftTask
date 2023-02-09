package com.luxoft.task.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface CsvService<T> {


    List<T> csvToList(MultipartFile file);

    List<T> csvToList(InputStream in);

}
