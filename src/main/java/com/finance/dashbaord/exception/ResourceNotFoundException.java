package com.finance.dashbaord.exception;

import java.time.LocalDate;

public class ResourceNotFoundException  extends RuntimeException {
    public ResourceNotFoundException( String message)
    {
        super(message);
    }
}
