package ru.taynov.cccbot.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommandConstants {
    public static final String START = "/start";
    public static final String ADD = "/add";
    public static final String LIST = "/list";
    public static final String SEARCH = "/search";
    public static final String LIST_BY_PROJECT = "/project";
    public static final String LIST_BY_POST = "/post";
    public static final String HELP = "/help";
    public static final String DELETE = "/delete";
    public static final String EDIT = "/edit";
    public static final String ADMIN = "/admin";
    public static final String USER = "/user";
}
