package prr.exceptions;

public class DuplicateTerminalException extends ClientTerminalException {
    public DuplicateTerminalException(String key) {
        super(key);
    }
}
