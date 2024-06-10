package com.example.profile.repository;

import com.example.profile.entity.UserProfile;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends Neo4jRepository<UserProfile, String> {
    boolean existsByFirstNameAndLastName(String firstName, String lastName);
}
