package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import prr.exceptions.DestinationIsOffException;
import prr.exceptions.UnknownTerminalException;
import prr.exceptions.UnknownClientException;
import prr.app.exceptions.UnknownTerminalKeyException;
import prr.app.exceptions.UnknownClientKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for sending a text communication.
 */
class DoSendTextCommunication extends TerminalCommand {

        DoSendTextCommunication(Network context, Terminal terminal) {
                super(Label.SEND_TEXT_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
                addStringField("id", Prompt.terminalKey());
                addStringField("text", Prompt.textMessage());
        }

        @Override
        protected final void execute() throws CommandException {
                try {
                        _receiver.sendTextCommunication(_network.searchTerminal(stringField("id")), stringField("text"));
                } catch (UnknownTerminalException e) {
                        throw new UnknownTerminalKeyException(e.getKey());
                } catch (DestinationIsOffException e1) {
                        _display.popup(Message.destinationIsOff(e1.getKey()));
                } catch (UnknownClientException e2) {
                        throw new UnknownClientKeyException(e2.getKey());
                }
        }
} 
