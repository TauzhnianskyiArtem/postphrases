package com.example.coursework.repos;

import com.example.coursework.domain.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepo extends CrudRepository<Message, Integer> {
    List<Message> findByTag(String tag);

    @Query("SELECT m FROM Message m " +
            "WHERE :isFiltered = FALSE " +
            "OR LOWER(m.tag) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Iterable<Message> findAllByFilter(boolean isFiltered, String filter);
}
