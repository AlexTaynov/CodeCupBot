package ru.taynov.cccbot.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmployeeDataRepository {
    private final MongoTemplate mongoTemplate;

    public List<String> getAllProjects() {
        return mongoTemplate.getCollection("employees")
                .distinct("projectName", String.class)
                .into(new ArrayList<>());
    }

    public List<String> getAllPosts() {
        return mongoTemplate.getCollection("employees")
                .distinct("post", String.class)
                .into(new ArrayList<>());
    }

}
