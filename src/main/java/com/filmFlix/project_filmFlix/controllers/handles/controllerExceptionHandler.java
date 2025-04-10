package com.filmFlix.project_filmFlix.controllers.handles;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.filmFlix.project_filmFlix.Exceptions.DuplacationEntityException;
import com.filmFlix.project_filmFlix.Exceptions.ResourcesNotFoundException;
import com.filmFlix.project_filmFlix.Exceptions.UnauthorizedException;
import com.filmFlix.project_filmFlix.dtos.exceptionsDto.ErrorDto;
import com.filmFlix.project_filmFlix.dtos.exceptionsDto.ExceptionDto;
import com.filmFlix.project_filmFlix.dtos.exceptionsDto.FildExceptionDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;

@RestControllerAdvice
public class controllerExceptionHandler {

    @ExceptionHandler(ResourcesNotFoundException.class)
    public ResponseEntity<ExceptionDto> resourcesNotFound(ResourcesNotFoundException e, HttpServletRequest request){
        var status = HttpStatus.NOT_FOUND;
        ExceptionDto dto = new ExceptionDto(LocalDate.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(dto);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionDto> authException(AuthenticationException e, HttpServletRequest request){
        var status = HttpStatus.NOT_FOUND;
        ExceptionDto dto = new ExceptionDto(LocalDate.now(), status.value(), "usuário nao encontrado", request.getRequestURI());
        return ResponseEntity.status(status).body(dto);
    }

    @ExceptionHandler(DuplacationEntityException.class)
    public ResponseEntity<ExceptionDto> exceptionDtoResponseEntity(DuplacationEntityException e, HttpServletRequest request){
        var status = HttpStatus.CONFLICT;
        var dto = new ExceptionDto(LocalDate.now(), status.value(), e.getMessage(), request.getRequestURI() );
        return ResponseEntity.status(status).body(dto);
    }

    @ExceptionHandler(JWTCreationException.class)
    public ResponseEntity<ExceptionDto> jwtException (JWTCreationException e, HttpServletRequest request){
        var status = HttpStatus.BAD_REQUEST;
        ExceptionDto dto = new ExceptionDto(LocalDate.now(), status.value(), "erro ao criar token", request.getRequestURI());
        return ResponseEntity.status(status).body(dto);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> methodException (MethodArgumentNotValidException e, HttpServletRequest request){
        var status = HttpStatus.BAD_REQUEST;
        FildExceptionDto dto = new FildExceptionDto(LocalDate.now(), status.value(), "dados invalidos", request.getRequestURI());
        for(FieldError error: e.getFieldErrors()){
            ErrorDto errorDto = new ErrorDto(error.getField(), error.getDefaultMessage());
            dto.getErrors().add(errorDto);
        }
        return ResponseEntity.status(status).body(dto);
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<ExceptionDto> illegalException (InvalidDataAccessApiUsageException e, HttpServletRequest request){
        var status = HttpStatus.BAD_REQUEST;
        ExceptionDto dto = new ExceptionDto(LocalDate.now(), status.value(), "campo requerido", request.getRequestURI());
        return ResponseEntity.status(status).body(dto);
    }
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionDto> unauthorizedExcption (UnauthorizedException e, HttpServletRequest request){
        var status = HttpStatus.UNAUTHORIZED;
        ExceptionDto dto = new ExceptionDto(LocalDate.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(dto);
    }
}
