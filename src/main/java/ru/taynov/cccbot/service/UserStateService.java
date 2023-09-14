package ru.taynov.cccbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.taynov.cccbot.entity.User;
import ru.taynov.cccbot.enums.State;
import ru.taynov.cccbot.repository.UserRepository;

import static ru.taynov.cccbot.enums.State.START;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserStateService {
    private final UserRepository userRepository;

    public void setStatus(Long chatId, State newState) {
        var user = userRepository.findById(chatId).orElse(null);
        if (user == null) {
            user = User.builder().chatId(chatId).build();
        }
        user.setState(newState);
        userRepository.save(user);
        log.info("ChatId " + chatId + " changed status to " + newState);
    }

    public State getStatus(Long chatId, String username) {
        var user = userRepository.findById(chatId).orElse(null);

        if (user == null) {
            user = User.builder().chatId(chatId)
                    .username(username)
                    .state(START).build();
            userRepository.save(user);
        }
        return user.getState();
    }
}
