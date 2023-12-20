package prr.communications;

import prr.terminals.*;

public class TextCommunication extends Communication {
    private String _text = "";

    public TextCommunication(int id, Terminal sender, Terminal receiver, String text) {
        super(id, sender, receiver);
        _text = text;
        endCommunication(); /* text communications are instantaneous */
    }

    public void setDuration(int duration) {
        /* text communications don't have a duration */
    }

    public String type() {
        return "TEXT";
    }

    public int units() {
        return _text.length();
    }
}