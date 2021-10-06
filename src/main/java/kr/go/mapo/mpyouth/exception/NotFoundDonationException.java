package kr.go.mapo.mpyouth.exception;

//@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "재능기부가 없습니다.")
public class NotFoundDonationException extends RuntimeException{
    public NotFoundDonationException() {
        super();
    }

    public NotFoundDonationException(String message) {
        super(message);
    }

    public NotFoundDonationException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundDonationException(Throwable cause) {
        super(cause);
    }

    protected NotFoundDonationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
