package prr.app.terminal;

import prr.Network;
import prr.app.exceptions.UnknownTerminalKeyException;
import prr.terminals.Terminal;
import prr.exceptions.UnknownTerminalException;
import prr.exceptions.DestinationIsOffException;
import prr.exceptions.DestinationIsBusyException;
import prr.exceptions.DestinationIsSilentException;
import prr.exceptions.UnsupportedAtOriginException;
import prr.exceptions.UnsupportedAtDestinationException;
import prr.exceptions.TerminalAlreadyInStateException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for starting communication.
 */
class DoStartInteractiveCommunication extends TerminalCommand {

	DoStartInteractiveCommunication(Network context, Terminal terminal) {
		super(Label.START_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
		addStringField("id", Prompt.terminalKey());
		addOptionField("type", Prompt.commType(), "VIDEO", "VOICE");
	}

	@Override
	protected final void execute() throws CommandException {
        try {
			_receiver.startInteractiveCommunication(_network.searchTerminal(stringField("id")), optionField("type"));
		} catch (UnknownTerminalException e) {
        	throw new UnknownTerminalKeyException(e.getKey());
		} catch (DestinationIsOffException e1) {
			_display.popup(Message.destinationIsOff(e1.getKey()));
		} catch (DestinationIsBusyException e2) {
			_display.popup(Message.destinationIsBusy(e2.getKey()));
		} catch (DestinationIsSilentException e2) {
			_display.popup(Message.destinationIsSilent(e2.getKey()));
		} catch (UnsupportedAtOriginException e3) {
			_display.popup(Message.unsupportedAtOrigin(e3.getKey(), e3.getType()));
		} catch (UnsupportedAtDestinationException e4) {
			_display.popup(Message.unsupportedAtOrigin(e4.getKey(), e4.getType()));
		} catch (TerminalAlreadyInStateException e5) {
			/* should not happen */
		}
	}
}
