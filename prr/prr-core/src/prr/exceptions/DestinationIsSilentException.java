package prr.exceptions;

public class DestinationIsSilentException extends ClientTerminalException {
    public DestinationIsSilentException(String key) {
        super(key);
    }
}