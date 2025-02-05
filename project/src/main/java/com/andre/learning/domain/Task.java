package com.andre.learning.domain;

import java.sql.Timestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "TASK")
@EqualsAndHashCode
@Slf4j
@NoArgsConstructor
public class Task {

    @Id
    private Long id;
    private String title;
    private String description;
    private boolean completed = false;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
