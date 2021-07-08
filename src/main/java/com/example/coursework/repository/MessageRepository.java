package com.example.coursework.repository;

import com.example.coursework.domain.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Integer> {

    @Query("SELECT m FROM Message m " +
            "WHERE :isFiltered = FALSE " +
            "OR LOWER(m.tag) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Iterable<Message> findAllByFilter(boolean isFiltered, String filter);
}
