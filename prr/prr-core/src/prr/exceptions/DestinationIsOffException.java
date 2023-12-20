package prr.exceptions;

public class DestinationIsOffException extends ClientTerminalException {
    public DestinationIsOffException(String key) {
        super(key);
    }
}