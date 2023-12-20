package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import prr.exceptions.UnknownClientException;
import prr.exceptions.TerminalAlreadyInStateException;
import prr.exceptions.NoOngoingCommunicationException;
import prr.app.exceptions.UnknownClientKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for ending communication.
 */
class DoEndInteractiveCommunication extends TerminalCommand {

	DoEndInteractiveCommunication(Network context, Terminal terminal) {
		super(Label.END_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canEndCurrentCommunication());
		addIntegerField("duration", Prompt.duration());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
        	_receiver.endInteractiveCommunication(integerField("duration"));
			_display.popup(Message.communicationCost(_receiver.getLastInteractiveCommunicationCost()));
		} catch (UnknownClientException e) {
			/* should not happen */
			throw new UnknownClientKeyException(e.getKey()); 
		} catch (NoOngoingCommunicationException e1) {
			/* should not happen */
		} catch (TerminalAlreadyInStateException e2) {
			/* should not happen */
		}
	}
}
