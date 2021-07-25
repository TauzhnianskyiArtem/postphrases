package com.example.coursework.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    String text;
    String tag;

    @ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User author;

    String fileName;

    public String getAuthorName(){
        return this.author.getUsername();
    }
}
