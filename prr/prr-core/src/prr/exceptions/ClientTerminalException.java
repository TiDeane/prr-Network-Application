package prr.exceptions;

public abstract class ClientTerminalException extends Exception {
    private String _key;

    public ClientTerminalException(String key) {
        _key = key;
    }

    public String getKey() {
        return _key;
    }
}
