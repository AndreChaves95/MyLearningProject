package com.andre.learning.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskMessage {

    private Long taskId;
    private String title;
    private String description;
    private boolean completed;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
