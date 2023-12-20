package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import prr.exceptions.UnknownTerminalException;
import prr.app.exceptions.UnknownTerminalKeyException;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Add a friend.
 */
class DoAddFriend extends TerminalCommand {

	DoAddFriend(Network context, Terminal terminal) {
		super(Label.ADD_FRIEND, context, terminal);
		addStringField("friendId", Prompt.terminalKey());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			_receiver.addFriend(_network.searchTerminal(stringField("friendId")));
		} catch (UnknownTerminalException e) {
			throw new UnknownTerminalKeyException(e.getKey());
		}
	}
}
