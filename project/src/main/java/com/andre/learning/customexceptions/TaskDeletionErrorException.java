package com.andre.learning.customexceptions;

public class TaskDeletionErrorException extends RuntimeException {

    public TaskDeletionErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
