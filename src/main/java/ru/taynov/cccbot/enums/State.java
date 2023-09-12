package ru.taynov.cccbot.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum State {
    START(false),
    SHOW_ALL(false),
    SEARCH(false),
    DELETE_CONFIRM(false),
    ADD(false),
    EDIT(false),
    CHANGE_FIRSTNAME(false),
    CHANGE_LASTNAME(false),
    CHANGE_POST(false),
    CHANGE_PROJECT(false),
    CHANGE_HIRING_DATE(true),
    CHANGE_PHOTO(true);

    private final Boolean isOptional;

}

