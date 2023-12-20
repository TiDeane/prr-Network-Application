package prr.app.lookups;

import prr.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Show unused terminals (without communications).
 */
class DoShowMaxTerminalPayment extends Command<Network> {

	DoShowMaxTerminalPayment (Network receiver) {
		super(Label.SHOW_MAX_TERMINAL_PAYMENT, receiver);
	}

	@Override
	protected final void execute() throws CommandException {
        _display.popup(_receiver.getMaxTerminalPayment());
	}
}