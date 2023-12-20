package prr.exceptions;

public class InvalidTerminalException extends ClientTerminalException {
    public InvalidTerminalException(String key) {
        super(key);
    }
}
