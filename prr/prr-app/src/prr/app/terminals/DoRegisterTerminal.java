package prr.app.terminals;

import java.io.IOException;

import prr.Network;
import prr.app.exceptions.DuplicateTerminalKeyException;
import prr.app.exceptions.InvalidTerminalKeyException;
import prr.app.exceptions.UnknownClientKeyException;
import prr.exceptions.DuplicateTerminalException;
import prr.exceptions.InvalidTerminalException;
import prr.exceptions.UnknownClientException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;

/**
 * Register terminal.
 */
class DoRegisterTerminal extends Command<Network> {

	DoRegisterTerminal(Network receiver) {
		super(Label.REGISTER_TERMINAL, receiver);
		addStringField("id", Prompt.terminalKey());
		addOptionField("type", Prompt.terminalType(), "BASIC", "FANCY");
		addStringField("clientKey", Prompt.clientKey());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
        	String id = stringField("id");
			String type = stringField("type");
			String clientKey = stringField("clientKey");
		
			String[] fields = { type, id, clientKey, "IDLE" };
			_receiver.registerTerminal(fields);
		} 
		  catch (DuplicateTerminalException e) {
			throw new DuplicateTerminalKeyException(e.getKey());
		} catch (InvalidTerminalException e1) {
			throw new InvalidTerminalKeyException(e1.getKey());
		} catch (UnknownClientException e2) {
			throw new UnknownClientKeyException(e2.getKey());
		}
	}
}