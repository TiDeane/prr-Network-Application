package prr.communications;

import prr.terminals.*;

public abstract class Communication {
    private int _id;

    private Terminal _sender;
    private Terminal _receiver;

    private boolean _communicationOngoing = true;
    private double _cost;
    private boolean _payed;
    
    public Communication(int id, Terminal sender, Terminal receiver) {
        _id = id;
        _sender = sender;
        _receiver = receiver;
        _payed = false;
    }

    public Terminal getSender() { return _sender; }

    public Terminal getReceiver() { return _receiver; }

        public int getId() {
        return _id;
    }

    public void setCost(double cost) {
        _cost = cost;
    }

    public abstract void setDuration(int duration);

    public double getCost() { return _cost; }

    public long getRoundedCost() {
        long roundedCost = (long) _cost;
        return roundedCost;
    }

    public void endCommunication() { 
        _communicationOngoing = false;
    }

    public void payedCommunication() {
        _payed = true;
    }

    public boolean checkSenderId(String key) {
        return _sender.getClientId().equals(key);
    }

    public boolean checkReceiverId(String key) {
        return _receiver.getClientId().equals(key);
    }

    /* returns "VOICE", "TEXT" or "VIDEO" */
    public abstract String type();

    /* returns the number of characters for text communications, and the duration
       for voice or video communications */
    public abstract int units();

    public String status() {
        return ((_communicationOngoing == true) ? "ONGOING" : "FINISHED");
    }

    @Override
    public String toString() {
        return type() + "|" + _id + "|" + _sender.getId() + "|" + _receiver.getId()
        + "|" + units() + "|" + getRoundedCost() + "|" + status();
    }
}