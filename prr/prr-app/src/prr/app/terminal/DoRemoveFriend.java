package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import prr.exceptions.UnknownTerminalException;
import prr.app.exceptions.UnknownTerminalKeyException;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Remove friend.
 */
class DoRemoveFriend extends TerminalCommand {

	DoRemoveFriend(Network context, Terminal terminal) {
		super(Label.REMOVE_FRIEND, context, terminal);
		addStringField("friendId", Prompt.terminalKey());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			_receiver.removeFriend(_network.searchTerminal(stringField("friendId")));
		} catch (UnknownTerminalException e) {
			throw new UnknownTerminalKeyException(e.getKey());
		}
	}
}
