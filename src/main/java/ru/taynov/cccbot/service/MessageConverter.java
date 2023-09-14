package ru.taynov.cccbot.service;

import org.springframework.stereotype.Component;
import ru.taynov.cccbot.entity.Employee;

import java.util.List;

import static ru.taynov.cccbot.constants.BotConstants.DATE_FORMATTER;
import static ru.taynov.cccbot.constants.MessageConstants.EMPLOYEES_LIST;
import static ru.taynov.cccbot.constants.MessageConstants.EMPLOYEE_CARD;
import static ru.taynov.cccbot.constants.MessageConstants.EMPLOYEE_HIRED_DATE;
import static ru.taynov.cccbot.constants.MessageConstants.EMPLOYEE_ROW;

@Component
public class MessageConverter {

    public String listOfEmployee(List<Employee> list) {
        var result = new StringBuilder().append(EMPLOYEES_LIST);
        for (int i = 0; i < list.size(); i++) {
            var employee = list.get(i);
            result.append(EMPLOYEE_ROW.formatted(i + 1,
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getPost(),
                    employee.getProjectName())
            );
        }

        return result.toString();
    }

    public String employeePage(Employee employee) {
        return EMPLOYEE_CARD.formatted(buildName(employee), employee.getPost(), employee.getProjectName())
                + buildHiredDate(employee);
    }

    private String buildName(Employee employee) {
        var result = new StringBuilder().append(employee.getFirstName()).append(" ");
        if (employee.getMiddleName() != null) result.append(employee.getMiddleName()).append(" ");
        result.append(employee.getLastName());
        return result.toString();
    }

    private String buildHiredDate(Employee employee) {
        if (employee.getHiredDate() == null) return "";
        return EMPLOYEE_HIRED_DATE.formatted(employee.getHiredDate().format(DATE_FORMATTER));
    }
}
