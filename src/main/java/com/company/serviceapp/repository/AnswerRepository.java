package com.company.serviceapp.repository;

import com.company.serviceapp.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface AnswerRepository extends JpaRepository<Answer, UUID> {
    @Query(nativeQuery = true, value = "select * from answers where name=:answerName")
    Answer getAnswerByName(String answerName);
}
