package spring_course.my_bank_transactions.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{
   @Override
   protected ResponseEntity< Object > handleMissingServletRequestParameter( MissingServletRequestParameterException ex,
                                                                            HttpHeaders headers,
                                                                            HttpStatus status,
                                                                            WebRequest request ) {
      return ResponseEntity.badRequest().body( ex.getMessage() );
   }

   @Override
   protected ResponseEntity< Object > handleMethodArgumentNotValid( MethodArgumentNotValidException ex,
                                                                    HttpHeaders headers,
                                                                    HttpStatus status,
                                                                    WebRequest request ) {
      return ResponseEntity.badRequest().body( ex.getMessage() );
   }
}
