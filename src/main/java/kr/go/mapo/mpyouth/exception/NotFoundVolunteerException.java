package kr.go.mapo.mpyouth.exception;

public class NotFoundVolunteerException extends RuntimeException{
    public NotFoundVolunteerException() {
        super();
    }

    public NotFoundVolunteerException(String message) {
        super(message);
    }

    public NotFoundVolunteerException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundVolunteerException(Throwable cause) {
        super(cause);
    }

    protected NotFoundVolunteerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
