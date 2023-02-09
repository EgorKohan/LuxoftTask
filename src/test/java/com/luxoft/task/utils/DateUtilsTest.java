package com.luxoft.task.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

class DateUtilsTest {

    @Test
    void whenIsoLocalDateTime_thenOK() {
        String date = "2011-12-03T10:15:30";

        LocalDateTime localDateTime = DateUtils.parseDateOrNull(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        assertThat(localDateTime, notNullValue());
    }

    @Test
    void whenInvalidString_thenNull() {
        String date = "Error date";

        LocalDateTime localDateTime = DateUtils.parseDateOrNull(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        assertThat(localDateTime, nullValue());
    }

}