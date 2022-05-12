package command.commands;

import command.ICommand;
import main.DiscordBot;
import main.MessageBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 * Command for an easy help of the bot
 * 
 * @author jonaw
 *
 */
public class HelpCommand implements ICommand {

	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {
		channel.sendMessageEmbeds(MessageBuilder.createEmbed("> Um das Setup auszuführen: \n > **" + DiscordBot.PREFIX
				+ " setup** \n\n > Um das Setup erfolgreich auszuführen \n > muss eine Kategorie 'Bugs' erstellt werden. \n \n > Alle nötigen Channel und die dazugehörige Rolle \n > werden automatisch mithilfe des Setups erstellt."))
				.queue();
	}

}
