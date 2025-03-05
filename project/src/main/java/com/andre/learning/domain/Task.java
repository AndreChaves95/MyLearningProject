package com.andre.learning.domain;

import java.sql.Timestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@EqualsAndHashCode
@Slf4j
@NoArgsConstructor
public class Task {

    private Long taskId;
    private String title;
    private String description;
    private boolean completed = false;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
