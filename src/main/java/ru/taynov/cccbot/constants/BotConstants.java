package ru.taynov.cccbot.constants;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static ru.taynov.cccbot.constants.CommandConstants.ADD;
import static ru.taynov.cccbot.constants.CommandConstants.HELP;
import static ru.taynov.cccbot.constants.CommandConstants.LIST;
import static ru.taynov.cccbot.constants.CommandConstants.SEARCH;
import static ru.taynov.cccbot.constants.CommandConstants.START;

public class BotConstants {
    public static final List<BotCommand> LIST_OF_COMMANDS = List.of(
            new BotCommand(START, "приветствие"),
            new BotCommand(ADD, "добавление сотрудника"),
            new BotCommand(LIST, "краткая информация о сотрудниках"),
            new BotCommand(SEARCH, "поиск карточки сотрудника"),
            new BotCommand(HELP, "помощь")
    );

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
}
