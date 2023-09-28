package ru.taynov.cccbot.constants;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static ru.taynov.cccbot.constants.CommandConstants.ADD;
import static ru.taynov.cccbot.constants.CommandConstants.ADMIN;
import static ru.taynov.cccbot.constants.CommandConstants.DELETE;
import static ru.taynov.cccbot.constants.CommandConstants.LIST;
import static ru.taynov.cccbot.constants.CommandConstants.LIST_BY_POST;
import static ru.taynov.cccbot.constants.CommandConstants.LIST_BY_PROJECT;
import static ru.taynov.cccbot.constants.CommandConstants.SEARCH;
import static ru.taynov.cccbot.constants.CommandConstants.START;
import static ru.taynov.cccbot.constants.CommandConstants.USER;

public class BotConstants {
    public static final List<BotCommand> LIST_OF_COMMANDS = List.of(
            new BotCommand(START, "приветствие"),
            new BotCommand(ADD, "добавление сотрудника"),
            new BotCommand(LIST, "краткая информация о сотрудниках"),
            new BotCommand(SEARCH, "поиск карточки сотрудника"),
            new BotCommand(LIST_BY_PROJECT, "поиск по проекту"),
            new BotCommand(LIST_BY_POST, "поиск по должности"),
            new BotCommand(ADMIN, "Стать админом"),
            new BotCommand(USER, "Стать пользователем"),
            new BotCommand(DELETE, "Удалить сотрудника")
    );

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
}
