package prr.app.clients;

import prr.Network;
import prr.app.exceptions.DuplicateClientKeyException;
import prr.exceptions.DuplicateClientException;
import prr.app.exceptions.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Register new client.
 */
class DoRegisterClient extends Command<Network> {

    DoRegisterClient(Network receiver) {
        super(Label.REGISTER_CLIENT, receiver);
        addStringField("key", Prompt.key());
        addStringField("name", Prompt.name());
		addStringField("taxId", Prompt.taxId());
    }

    @Override
    protected final void execute() throws CommandException {
		    try {
            String key = stringField("key");
		    String name = stringField("name");
		    String taxId = stringField("taxId");

            String[] fields = { "CLIENT", key, name, taxId };
            _receiver.registerClient(fields);
		    } catch (DuplicateClientException e) {
			    throw new DuplicateClientKeyException(e.getKey());
		    }
    }

}