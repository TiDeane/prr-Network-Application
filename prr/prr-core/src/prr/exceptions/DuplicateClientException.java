package prr.exceptions;

public class DuplicateClientException extends ClientTerminalException {
    public DuplicateClientException(String key) {
        super(key);
    }
}
