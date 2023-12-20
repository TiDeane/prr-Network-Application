package prr.exceptions;

public class OriginIsOffException extends ClientTerminalException {
    public OriginIsOffException(String key) {
        super(key);
    }
}