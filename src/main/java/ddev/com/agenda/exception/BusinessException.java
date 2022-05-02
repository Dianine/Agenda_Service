package ddev.com.agenda.exception;

public class BusinessException extends RuntimeException{
    public BusinessException(String messege) {
        super(messege);
    }
}
