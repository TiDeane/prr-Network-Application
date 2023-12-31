package prr.app.clients;

import prr.Network;
import prr.exceptions.UnknownClientException;
import prr.app.exceptions.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show the payments and debts of a client.
 */
class DoShowClientPaymentsAndDebts extends Command<Network> {

	DoShowClientPaymentsAndDebts(Network receiver) {
		super(Label.SHOW_CLIENT_BALANCE, receiver);
		addStringField("key", Prompt.key());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			String key = stringField("key");
        	Message.clientPaymentsAndDebts(key, _receiver.getClient(key).getRoundedPayments(),
									   	   _receiver.getClient(key).getRoundedDebts());
		} catch (UnknownClientException e) {
			throw new UnknownClientKeyException(e.getKey());
		}
	}	
}
