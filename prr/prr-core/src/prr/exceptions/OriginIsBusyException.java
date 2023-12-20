package prr.exceptions;

public class OriginIsBusyException extends ClientTerminalException {
    public OriginIsBusyException(String key) {
        super(key);
    }
}