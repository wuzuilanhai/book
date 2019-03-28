package com.biubiu.web;

import com.alibaba.fastjson.JSON;
import com.biubiu.auth.exception.AuthenticationException;
import com.biubiu.auth.exception.AuthorizationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

/**
 * Created by Haibiao.Zhang on 2019-03-28 15:31
 */
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(AuthenticationException.class)
    public Response authenticationException(AuthenticationException ex) {
        return Response.fail(Response.UNAUTHORIZED, ex.getLocalizedMessage());
    }

    @ExceptionHandler(AuthorizationException.class)
    public Response authorizationException(AuthorizationException ex) {
        return Response.fail(Response.FORBIDDEN, ex.getLocalizedMessage());
    }

    @ExceptionHandler(Throwable.class)
    public Response throwable(Throwable t) {
        String errorMessage = t.getLocalizedMessage();
        String[] contents = errorMessage.split("; content:\n");
        if (contents.length > 1) {
            String content = contents[1];
            Map map = (Map) JSON.parse(content);
            String message = (String) map.get("message");
            return Response.fail(Response.INTERNAL_ERROR, message);
        }
        return Response.fail(Response.INTERNAL_ERROR, t.getLocalizedMessage());
    }

    @ExceptionHandler(BindException.class)
    public Response bindException(BindException e) {
        FieldError fieldError = e.getFieldError();
        return Response.fail(Response.BAD_REQUEST, fieldError.getDefaultMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response bindException(MethodArgumentNotValidException e) {
        StringBuilder message = new StringBuilder();
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        for (ObjectError error : allErrors) {
            message.append(error.getDefaultMessage()).append(";");
        }
        message.deleteCharAt(message.length() - 1);
        return Response.fail(Response.BAD_REQUEST, message.toString());
    }

}
