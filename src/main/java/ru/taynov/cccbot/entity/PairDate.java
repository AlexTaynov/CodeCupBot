package ru.taynov.cccbot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PairDate {
    private LocalDate from;
    private LocalDate to;
}
