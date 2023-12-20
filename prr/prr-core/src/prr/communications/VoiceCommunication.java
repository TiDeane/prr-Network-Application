package prr.communications;

import prr.terminals.*;

public class VoiceCommunication extends Communication {
    private int _duration = 0;
    
    public VoiceCommunication(int id, Terminal sender, Terminal receiver) {
        super(id, sender, receiver);
    }

    public void setDuration(int duration) {
        _duration = duration;
    }

    public String type() {
        return "VOICE";
    }

    public int units() {
        return _duration;
    }
}