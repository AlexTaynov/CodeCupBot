package ru.taynov.cccbot.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.taynov.cccbot.entity.PairDate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.taynov.cccbot.constants.BotConstants.DATE_FORMATTER;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParametersExtractor {

    public static Long extractLongParameter(String text) {
        return Arrays.stream(text.split(" ", 0)).skip(1).findFirst()
                .map(Long::parseLong).orElse(null);
    }

    public static String extractStringParameter(String text) {
        return Arrays.stream(text.split(" ", 0)).skip(1).findFirst().orElse(null);
    }

    public static PairDate extractDateParameter(String text) {
        Matcher matcher = Pattern.compile("^\\d{2}\\.\\d{2}\\.\\d{4}-\\d{2}\\.\\d{2}\\.\\d{4}$").matcher(text);
        if (matcher.find()) {
            var t = matcher.group();
            return new PairDate(LocalDate.parse(t.substring(0, t.indexOf("-")), DATE_FORMATTER),
                    LocalDate.parse(t.substring(t.indexOf("-") + 1), DATE_FORMATTER)
            );
        }
        matcher = Pattern.compile("^-\\d{2}\\.\\d{2}\\.\\d{4}$").matcher(text);
        if (matcher.find()) {
            var t = matcher.group();
            return new PairDate(null,
                    LocalDate.parse(t.substring(t.indexOf("-") + 1), DATE_FORMATTER)
            );
        }

        matcher = Pattern.compile("^\\d{2}\\.\\d{2}\\.\\d{4}-$").matcher(text);
        if (matcher.find()) {
            var t = matcher.group();
            return new PairDate(LocalDate.parse(t.substring(0, t.indexOf("-")), DATE_FORMATTER), null);
        }
        return null;
    }
}
