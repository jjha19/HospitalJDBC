package domain.exceptions;

public class DuplicateUsernameError extends RuntimeException {
    public DuplicateUsernameError(String message) {
        super(message);
    }
}
