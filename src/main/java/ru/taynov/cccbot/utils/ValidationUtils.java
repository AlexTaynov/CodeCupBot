package ru.taynov.cccbot.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static ru.taynov.cccbot.constants.BotConstants.DATE_FORMATTER;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationUtils {

    private static final String NAME_PATTERN = "^[ёЁА-Яа-яA-Za-z ,.'-]+$";
    private static final String LABEL_PATTERN = "^[0-9ёЁА-Яа-яA-Za-z ,.'-]+$";
    private static final String LONG_ID_PATTERN = "^\\d+$";

    public static boolean validateLongId(String text) {
        return text.matches(LONG_ID_PATTERN);
    }

    public static boolean validateLabel(String text) {
        return text.matches(LABEL_PATTERN);
    }

    public static boolean validateName(String text) {
        return text.matches(NAME_PATTERN);
    }

    public static LocalDate validateDate(String text) {
        try {
            return LocalDate.parse(text, DATE_FORMATTER);
        } catch (Exception e) {
            return null;
        }

    }

}
