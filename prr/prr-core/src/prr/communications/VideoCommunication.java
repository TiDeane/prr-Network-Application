package prr.communications;

import prr.terminals.*;

public class VideoCommunication extends Communication {
    private int _duration = 0;
    
    public VideoCommunication(int id, Terminal sender, Terminal receiver) {
        super(id, sender, receiver);
    }

    public void setDuration(int duration) {
        _duration = duration;
    }

    public String type() {
        return "VIDEO";
    }

    public int units() {
        return _duration;
    }
}