package ru.taynov.cccbot.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private String post;
    private String projectName;
    private String photoId;
    private LocalDate hiredDate;
}
