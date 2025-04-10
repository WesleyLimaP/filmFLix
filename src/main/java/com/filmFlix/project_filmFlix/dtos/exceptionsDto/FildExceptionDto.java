package com.filmFlix.project_filmFlix.dtos.exceptionsDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FildExceptionDto extends ExceptionDto{
    private List<ErrorDto> errors = new ArrayList<>();

    public FildExceptionDto(LocalDate moment, Integer status, String error, String path) {
        super(moment, status, error, path);
    }


    public List<ErrorDto> getErrors() {
        return errors;
    }
}
