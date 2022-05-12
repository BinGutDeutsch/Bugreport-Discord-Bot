package command;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 * Manages the Commands and the calls of the commands
 * 
 * @author jonaw
 *
 */
public class CommandManager {

	public ConcurrentHashMap<String, ICommand> m_Commands;

	public CommandManager() {
		m_Commands = new ConcurrentHashMap<String, ICommand>();
		for (Commands command : Commands.values()) {
			m_Commands.put(command.getCommandName(), command.getCommand());
		}
	}

	public boolean perform(String command, Member m, TextChannel channel, Message message) {
		ICommand cmd;
		if ((cmd = m_Commands.get(command.toLowerCase())) != null) {
			cmd.performCommand(m, channel, message);
			return true;
		}
		;
		return false;
	}

	public List<String> getCommands() {
		return m_Commands.keySet().stream().map(key -> key).collect(Collectors.toList());
	}
}