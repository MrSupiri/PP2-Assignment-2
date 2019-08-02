package Helpers;

public class NoSpaceLeftInStoreException extends RuntimeException {
    public NoSpaceLeftInStoreException(String errorMessage) {
        super(errorMessage);
    }
}
