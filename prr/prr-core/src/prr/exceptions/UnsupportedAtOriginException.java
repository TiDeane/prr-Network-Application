package prr.exceptions;

public class UnsupportedAtOriginException extends Exception {
    private String _id;
    private String _type = "BASIC";

    public UnsupportedAtOriginException(String id, String type) {
        _id = id;
        _type = type;
    }

    public String getKey() { return _id; }
    
    public String getType() { return _type; }
}