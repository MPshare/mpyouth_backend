package kr.go.mapo.mpyouth.exception;

public class NotFoundLifeLongEduException extends RuntimeException {
    public NotFoundLifeLongEduException() {
        super();
    }

    public NotFoundLifeLongEduException(String message) {
        super(message);
    }

    public NotFoundLifeLongEduException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundLifeLongEduException(Throwable cause) {
        super(cause);
    }

    protected NotFoundLifeLongEduException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
