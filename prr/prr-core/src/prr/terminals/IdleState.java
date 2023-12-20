package prr.terminals;

public class IdleState extends TerminalState {
    public IdleState(Terminal terminal) {
        super(terminal);
    }

    public String status() {
        return "IDLE";
    }
}