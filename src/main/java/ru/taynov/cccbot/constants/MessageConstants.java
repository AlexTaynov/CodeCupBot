package ru.taynov.cccbot.constants;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class MessageConstants {

    public static final String SEARCH_BY_POST_TEXT = """
                Выберите нужную должность из списка
            """;

    public static final String SEARCH_BY_PROJECT_TEXT = """
                Выберите нужный проект из списка
            """;

    public static final String ADMIN_TEXT = """
            Вы стали админом
            """;

    public static final String USER_TEXT = """
            Вы стали пользователем
            """;

    public static final String HELP_TEXT = """
            Команды для работы с ботом перечислены в меню, а также в списке ниже:
            /user, /admin - переключение статуса
            /project - вывод сотрудников проекта
            /post - вывод сотрудников заданной должности
            /add - добавление нового сотрудника
            /list - список с краткой информацией о сотрудниках
            /delete {id} - удаление сотрудника с конкретным номером
            /search - после ввода данной команды Вы можете вводить данные сотрудников: 
            ФИО, должность, проект, временной промежуток принятия на работу (через дефис без пробелов).
            В следующем виде:
                дд.мм.гггг-дд.мм.гггг или дд.мм.гггг- или -дд.мм.гггг.
                        
            Открываются карточки найденных сотрудников.
                        
            Редактирование и удаление происходит через карточку.
            """;

    public static final String START_TEXT = """
            Привет! Этот бот - финальное задание на Codemasters Code Cup.
                      
            Ваша роль: %s            
            Автор: @taynowski
                        
            Хорошего дня!
            """;

    public static final String EMPLOYEE_CARD = """
            ID: %d
            Сотрудник: %s
            Должность: %s
            Проект: %s
            """;

    public static final String EMPLOYEE_HIRED_DATE = """
            Дата прихода: %s
                        
            """;

    public static final String EMPLOYEES_LIST = """
            Найдены следующие сотрудники:
                        
            """;

    // 1. Ivanov Ivan, backend, project
    public static final String EMPLOYEE_ROW = """
            ID: %d. %s %s, %s, %s
            """;

    public static final String SEARCH_HINT = """
            Введите информацию для поиска
            """;

    public static final String ADMIN = "Админ";
    public static final String USER = "Пользователь";
    public static final String SET_FIRSTNAME = "Введите имя сотрудника";
    public static final String UNCOMPLETED_DELETED = "Черновик удален";
    public static final String SET_LASTNAME = "Введите фамилию сотрудника";
    public static final String SET_POST = "Введите должность";
    public static final String SET_PROJECT = "Введите проект";
    public static final String SET_MIDDLENAME = "Введите отчество ";
    public static final String SET_HIRED_DATE = "Введите дату прихода (формат дд.мм.гггг)";
    public static final String SET_PHOTO = "Отправьте аватар сотрудника";
    public static final String SET_PHOTO_ERROR = "Требуется изображение";
    public static final String SUCCESS = "Ок";
    public static final String TRY_AGAIN = "Сообщение содержит запрещенные символы";
    public static final String TRY_AGAIN_DATE = "Пример даты: 01.01.2023";
    public static final String EMPTY = "Ничего не найдено";
    public static final String CONTINUE_BUTTON = "Пропустить";
    public static final String EDIT_BUTTON = "Редактировать";
    public static final String DELETE_BUTTON = "Удалить";
    public static final String ERROR_TEXT = "Error occurred: ";
    public static final String NO_ACCESS_TEXT = "Недостаточно прав. Станьте админом " + CommandConstants.ADMIN;
}
