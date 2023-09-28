package ru.taynov.cccbot.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.taynov.cccbot.entity.Employee;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    @Query(value = "{ isCompleted: true, projectName: '?0'}", sort = "{ lastName : 1,  firstName : 1 }")
    List<Employee> findAllByProject(String project);

    @Query(value = "{ isCompleted: true, post: '?0'}", sort = "{ lastName : 1,  firstName : 1 }")
    List<Employee> findAllByPost(String post);

    @Query(value = "{ isCompleted: true }", sort = "{ lastName : 1,  firstName : 1 }")
    List<Employee> findAll();

    @Query(value = """
            { '$or':[ {'firstName': {$regex: '^?0', $options: 'i' }}, {'lastName': {$regex: '^?0', $options: 'i' }} ], isCompleted: true}
            """, sort = "{ lastName : 1,  firstName : 1  }")
    List<Employee> findAllByName(String name);

    @Query(value = "{ 'lastName': {$regex: '^?0', $options: 'i' }, 'firstName': {$regex: '^?1', $options: 'i' }, isCompleted: true}",
            sort = "{ lastName : 1,  firstName : 1  }")
    List<Employee> findAllByLastNameAndFirstName(String lastName, String firstName);

    @Query(value = "{chatId: ?0, isCompleted: false}")
    Employee findLastUncompleted(Long chatId);

    @Query(delete = true, value = "{isCompleted: false, chatId: ?0}")
    void deleteUncompleted(Long chatId);
}