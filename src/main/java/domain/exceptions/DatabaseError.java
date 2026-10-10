package domain.exceptions;

public class DatabaseError extends RuntimeException {
    public DatabaseError(String message) {
        super(message);
    }
}
