package prr.terminals;

public class BusyState extends TerminalState {
    public BusyState(Terminal terminal) {
        super(terminal);
    }

    public String status() {
        return "BUSY";
    }

    public String changeToIdle() {
        return "B2I";
    }
}