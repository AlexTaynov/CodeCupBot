package ru.taynov.cccbot.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Builder
@Document(collection = "employees")
public class Employee {
    @Transient
    public static final String SEQUENCE_NAME = "employees_sequence";
    @Id
    private Long id;
    private Long chatId;
    private String firstName;
    private String lastName;
    private String middleName;
    private String post;
    private String projectName;
    private String photoId;
    private LocalDate hiredDate;
    @Builder.Default
    private boolean isCompleted = false;
}
