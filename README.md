# prr Network Application

IST Object-Oriented Programming project, 2022/2023. The objective was to develop an application for managing a network of communication terminals, called prr. Generally, the program allows the registration, management and consultation of clients, terminals and communications.

-----------------------------------
It is possible to start the application with a text file specified by the Java import property.

The various entities have the formats described below. It is assumed that titles cannot contain the character '|'. There are no malformed entries.

Each line has a distinct description, but follows the following formats:

CLIENT|id|name|taxId
terminal-type|idTerminal|idClient|state
FRIENDS|idTerminal|idTerminal1,...,idTerminalN

- A client's `id` is composed of 6 characters and `taxID` of 6 digits;
- A terminal's `terminal-type` is _BASIC_ or _FANCY_, `idTerminal` is composed of 6 digits and `state` is _ON_, _OFF_ or _SILENCE_;

Client definitions always precede terminal definitions. Connections between friends are always after the definition of the remaining entities. For terminals, terminal-type is BASIC or FANCY.
