package com.andre.learning.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Entity
@Table(name = "TASK")
@EqualsAndHashCode
@Slf4j
@NoArgsConstructor
public class Task {

    @Id
    @Column(name = "task_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "completed")
    private boolean completed = false;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
