package listener;

import java.awt.Color;

import main.BugchannelManager;
import main.DiscordBot;
import main.MessageBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

/**
 * Listener for the Reports and manages if new bugreports are written
 * 
 * @author jonaw
 *
 */
public class ReportListener extends ListenerAdapter {

	/**
	 * Eventmethod gets called if a message is written. If the message is in the
	 * right channel for the bugreports and it is not a webhook, the message will be
	 * deleted a new EmbededMessage will be sent to the open Bugreports channel.
	 * 
	 * @param event Includes all informations about the event. Not <code>null</code>
	 */
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String msg = event.getMessage().getContentDisplay();
		Member m = event.getMember();
		if (!event.isFromType(ChannelType.TEXT)) {
			return;
		}
		TextChannel textChannel = event.getTextChannel();
		Guild guild = event.getGuild();
		if (!textChannel.equals(guild.getTextChannelById(BugchannelManager.m_Report))) {
			System.out.println(DiscordBot.getCurrentBot().getShardManager().getTextChannelById(BugchannelManager.m_Report));
			return;
		}

		if (event.getMessage().isWebhookMessage()) {
			return;
		}
		System.out.println("rtichtige channel");

		guild.getTextChannelById(BugchannelManager.m_Open)
				.sendMessageEmbeds(MessageBuilder.createReport(m,
						"> **User:** " + m.getUser().getAsTag() + " \n" + "> **Description:** " + msg, Color.gray,
						null))
				.setActionRow(Button.success("Annehmen", "Annehmen"), Button.danger("Ablehnen", "Ablehnen")).queue();
		event.getMessage().delete().queue();

	}
	
	@Override
	public void onReady(ReadyEvent event) {
		BugchannelManager.update();
	}

}
