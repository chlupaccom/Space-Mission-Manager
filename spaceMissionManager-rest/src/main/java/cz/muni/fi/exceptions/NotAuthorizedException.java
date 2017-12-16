package cz.muni.fi.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason="You are not authorized for this action!")
public class NotAuthorizedException extends RuntimeException{
}
