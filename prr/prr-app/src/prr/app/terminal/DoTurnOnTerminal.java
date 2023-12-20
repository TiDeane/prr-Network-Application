package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import prr.terminals.IdleState;
import prr.exceptions.TerminalAlreadyInStateException;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Turn on the terminal.
 */
class DoTurnOnTerminal extends TerminalCommand {

	DoTurnOnTerminal(Network context, Terminal terminal) {
		super(Label.POWER_ON, context, terminal);
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			_receiver.setState(new IdleState(_receiver));
		} catch (TerminalAlreadyInStateException e) {
			_display.popup(Message.alreadyOn());
		}
	}
}
