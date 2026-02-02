package org.studyeasy.SpringRestdemo.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.studyeasy.SpringRestdemo.payload.ExceptionPayload.ErrorResponseDTO;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

      // 400 - Validation errors (@Valid)
      @ExceptionHandler(MethodArgumentNotValidException.class)
      public ResponseEntity<ErrorResponseDTO> handleValidationException(
              MethodArgumentNotValidException ex,
              HttpServletRequest request) {
  
          String message = ex.getBindingResult()
                  .getFieldErrors()
                  .get(0)
                  .getDefaultMessage();
  
          ErrorResponseDTO error = new ErrorResponseDTO(
                  HttpStatus.BAD_REQUEST.value(),
                  "Validation Failed",
                  message,
                  request.getRequestURI()
          );
  
          return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
      }
  
      // Account not found
      @ExceptionHandler(AccountNotFoundException.class)
      public ResponseEntity<ErrorResponseDTO> handleAccountNotFound(
              AccountNotFoundException ex,
              HttpServletRequest request) {
  
          ErrorResponseDTO error = new ErrorResponseDTO(
                  HttpStatus.NOT_FOUND.value(),
                  "Account Not Found",
                  ex.getMessage(),
                  request.getRequestURI()
          );
  
          return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
      }
  
      // Authentication errors
      @ExceptionHandler(AuthenticationException.class)
      public ResponseEntity<ErrorResponseDTO> handleAuthenticationException(
              AuthenticationException ex,
              HttpServletRequest request) {
  
          ErrorResponseDTO error = new ErrorResponseDTO(
                  HttpStatus.UNAUTHORIZED.value(),
                  "Authentication Failed",
                  ex.getMessage(),
                  request.getRequestURI()
          );
  
          return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
      }
  
      // Generic Exception (fallback)
      @ExceptionHandler(Exception.class)
      public ResponseEntity<ErrorResponseDTO> handleGlobalException(
              Exception ex,
              HttpServletRequest request) {
  
          ErrorResponseDTO error = new ErrorResponseDTO(
                  HttpStatus.INTERNAL_SERVER_ERROR.value(),
                  "Internal Server Error",
                  ex.getMessage(),
                  request.getRequestURI()
          );
  
          return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }