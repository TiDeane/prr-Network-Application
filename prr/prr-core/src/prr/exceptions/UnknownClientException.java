package prr.exceptions;

public class UnknownClientException extends ClientTerminalException {
    public UnknownClientException(String key) {
        super(key);
    }
}
