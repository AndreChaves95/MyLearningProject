package com.andre.learning.customexceptions;

import com.fasterxml.jackson.core.JsonProcessingException;

public class TaskCompletionException extends JsonProcessingException {

    public TaskCompletionException(String msg) {
        super(msg);
    }

    public TaskCompletionException(String msg, Throwable rootCause) {
        super(msg, rootCause);
    }
}
