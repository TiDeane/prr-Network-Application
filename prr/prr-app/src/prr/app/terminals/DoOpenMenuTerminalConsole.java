package prr.app.terminals;

import java.io.IOException;

import prr.Network;
import prr.app.exceptions.UnknownTerminalKeyException;
import prr.exceptions.UnknownTerminalException;
import prr.app.terminal.Menu;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Open a specific terminal's menu.
 */
class DoOpenMenuTerminalConsole extends Command<Network> {

	DoOpenMenuTerminalConsole(Network receiver) {
		super(Label.OPEN_MENU_TERMINAL, receiver);
		addStringField("id", Prompt.terminalKey());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			String id = stringField("id");
			Menu menu = new Menu(_receiver, _receiver.searchTerminal(id));
			menu.open();
		} catch (UnknownTerminalException e) { 
			throw new UnknownTerminalKeyException(e.getKey());
		}
	}
}