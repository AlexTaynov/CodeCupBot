package ru.taynov.cccbot.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParametersExtractor {

    public static Long extractLongParameter(String text) {
        return Arrays.stream(text.split(" ", 0)).skip(1).findFirst()
                .map(Long::parseLong).orElse(null);
    }
}
