package domain.exceptions;

public class NoUserFound extends RuntimeException {
    public NoUserFound(String message) {
        super(message);
    }
}
