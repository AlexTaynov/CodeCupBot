package ru.taynov.cccbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.taynov.cccbot.entity.Employee;
import ru.taynov.cccbot.repository.EmployeeRepository;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeesService {
    private final SequenceGeneratorService sequenceGenerator;
    private final EmployeeRepository repository;

    public boolean uncompletedIsValid(long chatId) {
        var employee = repository.findLastUncompleted(chatId);
        if (employee == null) return true;
        return employee.getLastName() != null && employee.getFirstName() != null &&
                employee.getPost() != null && employee.getProjectName() != null;
    }

    public List<Employee> search(String firstName, String lastName) {
        if (firstName != null && lastName != null)
            return repository.findAllByLastNameAndFirstName(lastName, firstName);

        if (firstName != null)
            return repository.findAllByName(firstName);

        if (lastName != null)
            return repository.findAllByName(lastName);

        return repository.findAll();
    }

    public void setFirstName(long chatId, String firstName) {
        var employee = getLastEmployee(chatId);
        employee.setFirstName(firstName);
        repository.save(employee);
        log.info("Employee with id: " + employee.getId() + " has updated firstname: " + firstName);
    }

    public void setLastName(long chatId, String lastName) {
        var employee = getLastEmployee(chatId);
        employee.setLastName(lastName);
        repository.save(employee);
        log.info("Employee with id: " + employee.getId() + " has updated lastname: " + lastName);
    }

    public void setMiddleName(long chatId, String middleName) {
        var employee = getLastEmployee(chatId);
        employee.setMiddleName(middleName);
        repository.save(employee);
        log.info("Employee with id: " + employee.getId() + " has updated middlename: " + middleName);
    }

    public void setPost(long chatId, String post) {
        var employee = getLastEmployee(chatId);
        employee.setPost(post);
        repository.save(employee);
        log.info("Employee with id: " + employee.getId() + " has updated post: " + post);
    }

    public void setProjectName(long chatId, String projectName) {
        var employee = getLastEmployee(chatId);
        employee.setProjectName(projectName);
        repository.save(employee);
        log.info("Employee with id: " + employee.getId() + " has updated projectName: " + projectName);
    }

    public void setPhotoId(long chatId, String photoId) {
        var employee = getLastEmployee(chatId);
        employee.setPhotoId(photoId);
        repository.save(employee);
        log.info("Employee with id: " + employee.getId() + " has updated photoId: " + photoId);
    }

    public void setHiredDate(long chatId, LocalDate hiredDate) {
        var employee = getLastEmployee(chatId);
        employee.setHiredDate(hiredDate);
        repository.save(employee);
        log.info("Employee with id: " + employee.getId() + " has updated hiredDate: " + hiredDate);
    }

    public Employee setCompleted(long chatId) {
        var employee = repository.findLastUncompleted(chatId);
        if (employee != null) {
            employee.setCompleted(true);
            repository.save(employee);
            log.info("Employee with id: " + employee.getId() + " successfully created by userId: " + chatId);
        }
        return employee;
    }

    public void deleteUncompleted(long chatId) {
        repository.deleteUncompleted(chatId);
        log.info("Employee's draft is deleted");
    }

    public boolean setUncompleted(long chatId, Long id) {
        var employee = repository.findById(id).orElse(null);
        if (employee != null) {
            employee.setCompleted(false);
            repository.save(employee);
            log.info("Employee with id: " + employee.getId() + " was made draft by userId: " + chatId);
            return true;
        }
        return false;
    }

    public boolean delete(long chatId, Long id) {
        var employee = repository.findById(id).orElse(null);
        if (employee != null) {
            repository.delete(employee);
            log.info("Employee with id: " + employee.getId() + " was deleted by chatId: " + chatId);
            return true;
        }
        log.warn("Employee with id: " + id + " not found");
        return false;
    }

    private Employee getLastEmployee(long chatId) {
        var employee = repository.findLastUncompleted(chatId);
        if (employee == null) {
            employee = buildEmployee(chatId);
        }
        return employee;
    }

    private Employee buildEmployee(long chatId) {
        return Employee.builder()
                .id(sequenceGenerator.generateSequence(Employee.SEQUENCE_NAME))
                .chatId(chatId)
                .build();
    }

}

