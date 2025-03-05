package com.andre.learning.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TaskDTO {

    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
