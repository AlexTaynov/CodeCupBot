package ru.taynov.cccbot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.taynov.cccbot.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
