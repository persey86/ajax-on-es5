package com.department.servlet.actions;

import com.department.exceptions.AppException;
import com.department.servlet.actions.impl.department.DepartmentController;
import com.department.servlet.actions.impl.user.UserController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 15.05.17.
 */

@ControllerAdvice(basePackageClasses = {
        DepartmentController.class, UserController.class
})

public class ErrorController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AppException.class)
    @ResponseBody
    ResponseEntity<?> handleControllerException(HttpServletRequest request, AppException ex) {
        HttpStatus status = getStatus(request);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", ex.getMessage());
        return new ResponseEntity<>(errorMap, headers, status);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
