package ru.taynov.cccbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.taynov.cccbot.entity.Employee;
import ru.taynov.cccbot.utils.ParametersExtractor;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final EmployeesService employeesService;

    public List<Employee> search(String text) {

        var values = Arrays.stream(text.split(" ")).toList();

        var allEmployees = employeesService.search(null, null);

        return allEmployees.stream().filter(employee ->
                values.stream().map(it -> {
                    var dates = ParametersExtractor.extractDateParameter(it);
                    if (dates != null) {
                        if (employee.getHiredDate() == null) return true;
                        boolean before = dates.getFrom() == null || !dates.getFrom().isAfter(employee.getHiredDate());
                        boolean after = dates.getTo() == null || !dates.getTo().isBefore(employee.getHiredDate());
                        return (before && after);
                    }

                    return employee.getFirstName().equalsIgnoreCase(it) ||
                            employee.getLastName().equalsIgnoreCase(it) ||
                            (employee.getMiddleName() == null || employee.getMiddleName().equalsIgnoreCase(it)) ||
                            (employee.getPost() == null || employee.getPost().equalsIgnoreCase(it)) ||
                            (employee.getProjectName() == null || employee.getProjectName().equalsIgnoreCase(it));

                }).allMatch(Boolean.TRUE::equals)).toList();
    }
}
