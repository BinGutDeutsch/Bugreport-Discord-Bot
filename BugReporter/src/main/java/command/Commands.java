package command;

import command.commands.HelpCommand;
import command.commands.SetupCommand;

/**
 * enum to setup all commands
 * 
 * @author jonaw
 *
 */
public enum Commands {

	/**
	 * Register the commands with call and description
	 */
	Setup(new SetupCommand(), "setup", "Erstellt alle nötigen Utensilien"), Help(new HelpCommand(), "help", "Hilfe");

	private ICommand m_Command;
	private String m_CommandName;
	private String m_Description;

	Commands(ICommand command, String commandName, String description) {
		m_Command = command;
		m_CommandName = commandName;
		m_Description = description;
	}

	public String getCommandName() {
		return m_CommandName;
	}

	public ICommand getCommand() {
		return m_Command;
	}

	public String getDescription() {
		return m_Description;
	}

	public Commands getByName(String commandName) {
		for (Commands command : Commands.values()) {
			if (command.getCommandName().equals(commandName))
				return command;
		}
		return null;
	}
}