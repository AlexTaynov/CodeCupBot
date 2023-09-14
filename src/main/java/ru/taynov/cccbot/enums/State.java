package ru.taynov.cccbot.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum State {
    START,
    SHOW_ALL,
    SEARCH,
    ADD,
    EDIT,
    CHANGE_FIRSTNAME,
    CHANGE_LASTNAME,
    CHANGE_MIDDLENAME,
    CHANGE_POST,
    CHANGE_PROJECT,
    CHANGE_HIRING_DATE,
    CHANGE_PHOTO;
}

