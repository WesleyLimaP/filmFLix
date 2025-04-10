package com.filmFlix.project_filmFlix.dtos.exceptionsDto;

import java.time.LocalDate;

public class ExceptionDto {

        LocalDate moment;
        Integer status;
        String error;
        String path;

    public ExceptionDto(LocalDate moment, Integer status, String error, String path) {
        this.moment = moment;
        this.status = status;
        this.error = error;
        this.path = path;
    }

    public ExceptionDto() {
    }

    public LocalDate getMoment() {
        return moment;
    }

    public void setMoment(LocalDate moment) {
        this.moment = moment;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
