package prr.app.lookups;

import prr.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Show unused terminals (without communications).
 */
class DoShowLowestClientPayment extends Command<Network> {

	DoShowLowestClientPayment (Network receiver) {
		super(Label.SHOW_LOWEST_CLIENT_PAYMENT, receiver);
	}

	@Override
	protected final void execute() throws CommandException {
        _display.popup(_receiver.getLowestClientPayment());
	}
}