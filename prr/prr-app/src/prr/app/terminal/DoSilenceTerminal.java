package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import prr.terminals.SilenceState;
import prr.exceptions.TerminalAlreadyInStateException;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Silence the terminal.
 */
class DoSilenceTerminal extends TerminalCommand {

	DoSilenceTerminal(Network context, Terminal terminal) {
		super(Label.MUTE_TERMINAL, context, terminal);
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			_receiver.setState(new SilenceState(_receiver));
		} catch (TerminalAlreadyInStateException e) {
			_display.popup(Message.alreadySilent());
		}
	}
}
