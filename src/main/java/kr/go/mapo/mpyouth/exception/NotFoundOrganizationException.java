package kr.go.mapo.mpyouth.exception;

public class NotFoundOrganizationException extends RuntimeException {
    public NotFoundOrganizationException() {
        super();
    }

    public NotFoundOrganizationException(String message) {
        super(message);
    }

    public NotFoundOrganizationException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundOrganizationException(Throwable cause) {
        super(cause);
    }

    protected NotFoundOrganizationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
