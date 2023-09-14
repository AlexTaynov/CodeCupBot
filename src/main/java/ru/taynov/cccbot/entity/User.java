package ru.taynov.cccbot.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.taynov.cccbot.enums.State;

@Data
@Builder
@Document(collection = "users")
public class User {
    @Id
    private Long chatId;
    @Builder.Default
    private State state = State.START;

    private String username;
}
