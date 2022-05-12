package listener;

import main.DiscordBot;
import main.MessageBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Listener for the Commands of the bot
 * 
 * @author jonaw
 *
 */
public class CommandListener extends ListenerAdapter {

	/**
	 * Event listens to messages starting the the configurated prefix. If the
	 * Command was not found by the CommandManager it will responde with an
	 * appropriate message
	 */
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String msg = event.getMessage().getContentDisplay();
		if (!msg.startsWith(DiscordBot.PREFIX)) {
			return;
		}
		TextChannel textChannel = event.getTextChannel();
		Member m = event.getMember();
		if (event.isFromType(ChannelType.TEXT)) {
			String[] args = msg.substring(DiscordBot.PREFIX.length()).split(" ");
			if (args.length > 0) {
				if (!DiscordBot.getCurrentBot().getCmdManager().perform(args[1], m, textChannel, event.getMessage())) {
					textChannel.sendMessageEmbeds(MessageBuilder.createEmbed("> Befehl wurde nicht gefunden")).queue();
				}
			}
		}
	}

}