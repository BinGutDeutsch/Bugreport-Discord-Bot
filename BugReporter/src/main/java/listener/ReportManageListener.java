package listener;

import java.awt.Color;

import main.BugchannelManager;
import main.MessageBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Listener for the management of the bugreports. Listens to Button interactions
 * and moves them between the channels
 * 
 * @author jonaw
 *
 */
public class ReportManageListener extends ListenerAdapter {

	/**
	 * Eventmethod gets called if a Button on an EmbededMessage is clicked. If the
	 * user is not allowed to manage Bugreports, he will get an errormessage. If the
	 * user has the right role to manage bugreports and clicks on one of the two
	 * buttons, the message will be moved to the corresponding channel.
	 * 
	 * @param event Includes all informations about the event. Not <code>null</code>
	 */
	@Override
	public void onButtonInteraction(ButtonInteractionEvent event) {
		TextChannel textChannel = event.getTextChannel();
		Guild guild = event.getGuild();
		if (!textChannel.equals(guild.getTextChannelById(BugchannelManager.m_Open))) {
			return;
		}
		Member m = event.getMember();
		if (!m.getRoles().contains(guild.getRoleById(BugchannelManager.m_ManageRole))) {
			event.replyEmbeds(MessageBuilder.createEmbed(
					"> Du hast keine **Berechtigung** um Bugs zu verwalten. \n > Sollte dies ein Fehler sein melde dich bei der Administration."))
					.setEphemeral(true).queue();
			return;
		}

		if (event.getComponentId().equals("Annehmen")) {
			moveReport(event.getMessageId(), textChannel, guild.getTextChannelById(BugchannelManager.m_Accepted), m,
					Color.green, true);
		} else if (event.getComponentId().equals("Ablehnen")) {
			moveReport(event.getMessageId(), textChannel, guild.getTextChannelById(BugchannelManager.m_Denied), m,
					Color.red, false);
		}
	}

	/**
	 * Helpmethod to move an embededmessage from one textchannel to another
	 * textchannel.
	 * 
	 * @param messageID the message which has to be moved
	 * @param tcFrom    the channel from where the message has to be moved
	 * @param tcTo      the channel where the message to be moved to
	 * @param member    the member who managed the bugreport
	 * @param color     the color for the embedmessage
	 * @param accepted  if the bugreport got accepted or denied
	 */
	private void moveReport(String messageID, TextChannel tcFrom, TextChannel tcTo, Member member, Color color,
			boolean accepted) {
		tcFrom.retrieveMessageById(messageID).queue((msg) -> {
			tcTo.sendMessageEmbeds(
					MessageBuilder.createReport(member, msg.getEmbeds().get(0).getDescription(), color, accepted))
					.queue();
			msg.delete().queue();
		});
	}
}
