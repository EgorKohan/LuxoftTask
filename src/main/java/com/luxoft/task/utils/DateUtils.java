package com.luxoft.task.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {

    @Nullable
    public static LocalDateTime parseDateOrNull(@Nullable String date, DateTimeFormatter formatter) {
        if (date == null) return null;
        try {
            return LocalDateTime.parse(date, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

}
