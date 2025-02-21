package com.andre.learning.customexceptions;

import java.sql.SQLIntegrityConstraintViolationException;

public class TaskIdDuplicatedException extends SQLIntegrityConstraintViolationException {

    public TaskIdDuplicatedException(String message) {
        super(message);
    }

}
