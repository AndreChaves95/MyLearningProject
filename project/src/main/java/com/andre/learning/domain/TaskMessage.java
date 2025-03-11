package com.andre.learning.domain;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long taskId;
    private String title;
    private String description;
    private boolean completed;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
