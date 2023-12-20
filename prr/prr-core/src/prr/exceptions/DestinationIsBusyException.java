package prr.exceptions;

public class DestinationIsBusyException extends ClientTerminalException {
    public DestinationIsBusyException(String key) {
        super(key);
    }
}