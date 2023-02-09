package com.luxoft.task.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "records")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Record {

    @Id
    @NotBlank(message = "Id must be non blank string!")
    private String id;

    private String name;

    private String description;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime updatedTimestamp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Record rec = (Record) o;
        return id != null && Objects.equals(id, rec.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
