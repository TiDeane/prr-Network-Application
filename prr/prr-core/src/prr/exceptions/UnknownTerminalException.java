package prr.exceptions;

public class UnknownTerminalException extends ClientTerminalException {
    public UnknownTerminalException(String key) {
        super(key);
    }
}