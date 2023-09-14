package ru.taynov.cccbot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.taynov.cccbot.config.BotConfig;
import ru.taynov.cccbot.entity.Employee;
import ru.taynov.cccbot.enums.State;
import ru.taynov.cccbot.service.EmployeesService;
import ru.taynov.cccbot.service.MessageConverter;
import ru.taynov.cccbot.service.UserStateService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.isNull;
import static ru.taynov.cccbot.constants.BotConstants.LIST_OF_COMMANDS;
import static ru.taynov.cccbot.constants.CommandConstants.ADD;
import static ru.taynov.cccbot.constants.CommandConstants.DELETE;
import static ru.taynov.cccbot.constants.CommandConstants.EDIT;
import static ru.taynov.cccbot.constants.CommandConstants.HELP;
import static ru.taynov.cccbot.constants.CommandConstants.LIST;
import static ru.taynov.cccbot.constants.CommandConstants.SEARCH;
import static ru.taynov.cccbot.constants.CommandConstants.START;
import static ru.taynov.cccbot.constants.MessageConstants.CONTINUE_BUTTON;
import static ru.taynov.cccbot.constants.MessageConstants.DELETE_BUTTON;
import static ru.taynov.cccbot.constants.MessageConstants.EDIT_BUTTON;
import static ru.taynov.cccbot.constants.MessageConstants.EMPTY;
import static ru.taynov.cccbot.constants.MessageConstants.ERROR_TEXT;
import static ru.taynov.cccbot.constants.MessageConstants.HELP_TEXT;
import static ru.taynov.cccbot.constants.MessageConstants.SEARCH_HINT;
import static ru.taynov.cccbot.constants.MessageConstants.SET_FIRSTNAME;
import static ru.taynov.cccbot.constants.MessageConstants.SET_HIRED_DATE;
import static ru.taynov.cccbot.constants.MessageConstants.SET_LASTNAME;
import static ru.taynov.cccbot.constants.MessageConstants.SET_MIDDLENAME;
import static ru.taynov.cccbot.constants.MessageConstants.SET_PHOTO;
import static ru.taynov.cccbot.constants.MessageConstants.SET_PHOTO_ERROR;
import static ru.taynov.cccbot.constants.MessageConstants.SET_POST;
import static ru.taynov.cccbot.constants.MessageConstants.SET_PROJECT;
import static ru.taynov.cccbot.constants.MessageConstants.START_TEXT;
import static ru.taynov.cccbot.constants.MessageConstants.SUCCESS;
import static ru.taynov.cccbot.constants.MessageConstants.TRY_AGAIN;
import static ru.taynov.cccbot.constants.MessageConstants.TRY_AGAIN_DATE;
import static ru.taynov.cccbot.constants.MessageConstants.UNCOMPLETED_DELETED;
import static ru.taynov.cccbot.enums.State.CHANGE_PROJECT;
import static ru.taynov.cccbot.utils.ParametersExtractor.extractLongParameter;
import static ru.taynov.cccbot.utils.ValidationUtils.validateDate;
import static ru.taynov.cccbot.utils.ValidationUtils.validateLabel;
import static ru.taynov.cccbot.utils.ValidationUtils.validateName;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    private final UserStateService stateService;
    private final BotConfig config;
    private final EmployeesService employeesService;
    private final MessageConverter messageConverter;

    public TelegramBot(UserStateService stateService, BotConfig config, EmployeesService employeesService, MessageConverter messageConverter) {
        this.stateService = stateService;
        this.config = config;
        this.employeesService = employeesService;
        this.messageConverter = messageConverter;
        try {
            this.execute(new SetMyCommands(LIST_OF_COMMANDS, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return config.getName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            var chatId = update.getMessage().getChatId();
            var username = update.getMessage().getChat().getUserName();
            var currentState = stateService.getStatus(chatId, username);

            if (update.getMessage().hasPhoto()) {
                handlePhoto(chatId, currentState, update.getMessage().getPhoto().get(0));
                return;
            }

            if (!update.getMessage().hasText()) return;

            var messageText = update.getMessage().getText();
            if (messageText.startsWith("/")) {
                if (!employeesService.uncompletedIsValid(chatId)) {
                    stateService.setStatus(chatId, State.START);
                    prepareAndSendMessage(chatId, UNCOMPLETED_DELETED);
                    employeesService.deleteUncompleted(chatId);
                } else {
                    employeesService.setCompleted(chatId);
                }

                if (messageText.startsWith(START)) {
                    prepareAndSendMessage(chatId, START_TEXT);
                    return;
                }

                if (messageText.startsWith(HELP)) {
                    prepareAndSendMessage(chatId, HELP_TEXT);
                    return;
                }

                if (messageText.startsWith(LIST)) {
                    showAllEmployees(chatId);
                    return;
                }

                if (messageText.startsWith(ADD)) {
                    stateService.setStatus(chatId, State.CHANGE_FIRSTNAME);
                    prepareAndSendMessage(chatId, SET_FIRSTNAME);
                    return;
                }

                if (messageText.startsWith(SEARCH)) {
                    stateService.setStatus(chatId, State.SEARCH);
                    prepareAndSendMessage(chatId, SEARCH_HINT);
                    return;
                }

            } else {

                if (currentState.equals(State.SEARCH)) {
                    search(chatId, messageText);
                    return;
                }

                if (currentState.equals(State.CHANGE_FIRSTNAME)) {
                    if (!validateName(messageText)) {
                        prepareAndSendMessage(chatId, TRY_AGAIN);
                        return;
                    }
                    employeesService.setFirstName(chatId, messageText);
                    prepareAndSendMessage(chatId, SET_LASTNAME);
                    stateService.setStatus(chatId, State.CHANGE_LASTNAME);
                    return;
                }

                if (currentState.equals(State.CHANGE_LASTNAME)) {
                    if (!validateName(messageText)) {
                        prepareAndSendMessage(chatId, TRY_AGAIN);
                        return;
                    }
                    employeesService.setLastName(chatId, messageText);
                    prepareAndSendMessage(chatId, SET_MIDDLENAME, buildContinueKeyboard());
                    stateService.setStatus(chatId, State.CHANGE_MIDDLENAME);
                    return;
                }

                if (currentState.equals(State.CHANGE_MIDDLENAME)) {
                    if (!validateLabel(messageText)) {
                        prepareAndSendMessage(chatId, TRY_AGAIN);
                        return;
                    }
                    employeesService.setMiddleName(chatId, messageText);
                    prepareAndSendMessage(chatId, SET_POST);
                    stateService.setStatus(chatId, State.CHANGE_POST);
                    return;
                }

                if (currentState.equals(State.CHANGE_POST)) {
                    if (!validateName(messageText)) {
                        prepareAndSendMessage(chatId, TRY_AGAIN);
                        return;
                    }
                    employeesService.setPost(chatId, messageText);
                    prepareAndSendMessage(chatId, SET_PHOTO, buildContinueKeyboard());
                    stateService.setStatus(chatId, State.CHANGE_PHOTO);
                    return;
                }

                if (currentState.equals(State.CHANGE_PHOTO)) {
                    if (messageText.equals(CONTINUE_BUTTON)) {
                        stateService.setStatus(chatId, State.CHANGE_PROJECT);
                        prepareAndSendMessage(chatId, SET_PROJECT);
                        return;
                    }
                    prepareAndSendMessage(chatId, SET_PHOTO_ERROR, buildContinueKeyboard());
                    return;
                }

                if (currentState.equals(State.CHANGE_PROJECT)) {
                    if (!validateLabel(messageText)) {
                        prepareAndSendMessage(chatId, TRY_AGAIN);
                        return;
                    }
                    employeesService.setProjectName(chatId, messageText);
                    prepareAndSendMessage(chatId, SET_HIRED_DATE, buildContinueKeyboard());
                    stateService.setStatus(chatId, State.CHANGE_HIRING_DATE);
                    return;
                }

                if (currentState.equals(State.CHANGE_HIRING_DATE)) {
                    LocalDate date = null;
                    if (!messageText.equals(CONTINUE_BUTTON)) {
                        date = validateDate(messageText);
                        if (date == null) {
                            prepareAndSendMessage(chatId, TRY_AGAIN_DATE);
                            return;
                        }
                    }

                    employeesService.setHiredDate(chatId, date);
                    var employee = employeesService.setCompleted(chatId);
                    if (employee != null) sendEmployeeCard(chatId, employee);
                    stateService.setStatus(chatId, State.START);
                    return;
                }
            }

        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            var chatId = update.getCallbackQuery().getMessage().getChatId();

            if (callbackData.startsWith(EDIT)) {
                var employeeId = extractLongParameter(callbackData);
                if (employeeId == null) return;

                employeesService.setUncompleted(chatId, employeeId);
                stateService.setStatus(chatId, State.CHANGE_FIRSTNAME);
                prepareAndSendMessage(chatId, SET_FIRSTNAME);
                return;
            }

            if (callbackData.startsWith(DELETE)) {
                var employeeId = extractLongParameter(callbackData);
                if (employeeId == null) return;

                var success = employeesService.delete(chatId, employeeId);
                if (success) prepareAndSendMessage(chatId, SUCCESS);
                else prepareAndSendMessage(chatId, EMPTY);
                return;
            }
        }
    }

    private void handlePhoto(Long chatId, State currentState, PhotoSize photo) {
        if (currentState.equals(State.CHANGE_PHOTO)) {
            employeesService.setPhotoId(chatId, photo.getFileId());
            prepareAndSendMessage(chatId, SET_PROJECT);
            stateService.setStatus(chatId, CHANGE_PROJECT);
            return;
        }
    }

    private void search(long chatId, String messageText) {
        var names = Arrays.stream(messageText.split(" ", 0)).limit(2).toList();
        var firstName = names.get(0);
        var lastName = (names.size() > 1) ? names.get(1) : null;

        if (!validateName(firstName)) {
            prepareAndSendMessage(chatId, TRY_AGAIN);
            return;
        }
        if (lastName != null && !validateName(lastName)) {
            prepareAndSendMessage(chatId, TRY_AGAIN);
            return;
        }

        var employees = employeesService.search(firstName, lastName);
        if (employees.isEmpty()) {
            prepareAndSendMessage(chatId, EMPTY);
            return;
        }
        employees.forEach(it -> sendEmployeeCard(chatId, it));
    }

    private void sendEmployeeCard(long chatId, Employee employee) {
        var message = messageConverter.employeePage(employee);
        var keyboard = buildEmployeePageKeyboard(employee.getId());
        if (employee.getPhotoId() == null) {
            prepareAndSendMessage(chatId, message, keyboard);
        } else {
            prepareAndSendPhotoMessage(chatId, employee.getPhotoId(), message, keyboard);
        }
    }

    private ReplyKeyboardMarkup buildContinueKeyboard() {
        var row = new KeyboardRow();
        row.add(CONTINUE_BUTTON);
        return ReplyKeyboardMarkup.builder().keyboard(List.of(row)).oneTimeKeyboard(true).build();
    }

    private InlineKeyboardMarkup buildEmployeePageKeyboard(long id) {
        var editButton = List.of(InlineKeyboardButton.builder().text(EDIT_BUTTON).callbackData(EDIT + " " + id).build());
        var deleteButton = List.of(InlineKeyboardButton.builder().text(DELETE_BUTTON).callbackData(DELETE + " " + id).build());
        return InlineKeyboardMarkup.builder().keyboard(List.of(editButton, deleteButton)).build();
    }

    private void showAllEmployees(long chatId) {
        var employees = employeesService.search(null, null);
        if (employees.isEmpty()) {
            prepareAndSendMessage(chatId, EMPTY);
            return;
        }

        prepareAndSendMessage(chatId, messageConverter.listOfEmployee(employees));
    }

    private void prepareAndSendMessage(long chatId, String textToSend) {
        prepareAndSendMessage(chatId, textToSend, null);
    }

    private void prepareAndSendMessage(long chatId, String textToSend, ReplyKeyboard keyboardMarkup) {
        var message = SendMessage.builder()
                .chatId(String.valueOf(chatId))
                .text(textToSend)
                .replyMarkup(
                        isNull(keyboardMarkup) ?
                                ReplyKeyboardRemove.builder().removeKeyboard(true).build() : keyboardMarkup)
                .build();
        executeMessage(message);
    }

    private void prepareAndSendPhotoMessage(long chatId, String photoId, String text, ReplyKeyboard keyboard) {
        var photoMessage = SendPhoto.builder()
                .chatId(String.valueOf(chatId))
                .photo(new InputFile(photoId))
                .caption(text)
                .replyMarkup(keyboard)
                .build();
        try {
            execute(photoMessage);
        } catch (TelegramApiException e) {
            log.error(ERROR_TEXT + e.getMessage());
        }

    }

    private void executeMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(ERROR_TEXT + e.getMessage());
        }
    }
}