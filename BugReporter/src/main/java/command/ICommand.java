package command;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 * Interface for all Commands
 * 
 * @author jonaw
 *
 */
public interface ICommand {

	/**
	 * Interface for Command. The method has to be called to performe the Command
	 * 
	 * @param member  Member who called the method. Not <code>null</code>
	 * @param channel Channel where the command has been called. Not
	 *                <code>null</code>
	 * @param message Message that has been sent with the Command. Not
	 *                <code>null</code>
	 */
	public void performCommand(Member member, TextChannel channel, Message message);

}