package com.ex.exceptions;

import com.ex.security.payloads.APIResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.webjars.NotFoundException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MyGlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> myResourceNotFoundException(ResourceNotFoundException e) {
        String message = e.getMessage();

        APIResponse res = new APIResponse(message, false);

        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIResponse> myAPIException(APIException e) {
        String message = e.getMessage();

        APIResponse res = new APIResponse(message, false);

        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> myMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> res = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach(err -> {
            String fieldName = ((FieldError) err).getField();
            String message = err.getDefaultMessage();

            res.put(fieldName, message);
        });

        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> myConstraintsVoilationException(ConstraintViolationException e) {
        Map<String, String> res = new HashMap<>();

        e.getConstraintViolations().forEach(voilation -> {
            String fieldName = voilation.getPropertyPath().toString();
            String message = voilation.getMessage();

            res.put(fieldName, message);
        });

        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> myAuthenticationException(AuthenticationException e) {

        String res = e.getMessage();

        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<APIResponse> myMissingPathVariableException(MissingPathVariableException e) {
        APIResponse res = new APIResponse(e.getMessage(), false);

        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public String myDataIntegrityException(NotFoundException e) {
        return "views/common/errorPage404";
    }

    @ExceptionHandler(ServiceException.class)
    public String handleServiceException(ServiceException ex, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        // Get the current request URL and redirect back to the same page
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.trim().equals(""))
            return "redirect:" + referer;
        else
            return "views/common/errorPage";
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrityViolationException(DataIntegrityViolationException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "views/common/errorPage";
    }

}
