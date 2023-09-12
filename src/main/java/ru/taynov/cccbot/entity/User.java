package ru.taynov.cccbot.entity;

import lombok.Data;
import ru.taynov.cccbot.enums.State;

@Data
public class User {
    private String id;
    private State state;
}
