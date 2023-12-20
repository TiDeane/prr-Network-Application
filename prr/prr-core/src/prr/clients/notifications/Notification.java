package prr.clients.notifications;

import prr.terminals.*;

public abstract class Notification {
    Terminal _terminal;
    String _type;
    /* _type = "O2S", "O2I", "B2I" ou "S2I" */

    public Notification(Terminal terminal, String type) {
        _terminal = terminal;
        _type = type;
    }

    public String getTerminalId() {
        return _terminal.getId();
    }

    @Override
    public String toString() {
        return _type + "|" + _terminal.getId();
    }
}