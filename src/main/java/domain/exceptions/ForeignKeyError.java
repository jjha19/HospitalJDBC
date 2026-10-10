package domain.exceptions;

public class ForeignKeyError extends RuntimeException {
    public ForeignKeyError(String message) {
        super(message);
    }
}
