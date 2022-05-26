package listener;

import java.awt.Color;

import main.BugchannelManager;
import main.MessageBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

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
		if (!textChannel.equals(guild.getTextChannelById(BugchannelManager.m_Progress))) {
			return;
		}
		Member m = event.getMember();
		if (!m.getRoles().contains(guild.getRoleById(BugchannelManager.m_ManageRole))) {
			event.replyEmbeds(MessageBuilder.createEmbed(
					"> Du hast keine **Berechtigung** um Bugs zu verwalten. \n > Sollte dies ein Fehler sein melde dich bei der Administration."))
					.setEphemeral(true).queue();
			return;
		}

		if (event.getComponentId().equals("bearbeitet")) {
			moveReport(event.getMessageId(), guild, m, Color.green, true, "Bearbeitet");
		} else if (event.getComponentId().equals("inBearbeitung")) {
			moveReport(event.getMessageId(), guild, m, Color.yellow, false, "In Bearbeitung");
		} else if (event.getComponentId().equals("pruef")) {
			moveReport(event.getMessageId(), guild, m, Color.yellow, false, "In Prüfung");
		} else if (event.getComponentId().equals("keinBug")) {
			moveReport(event.getMessageId(), guild, m, Color.red, false, "Kein Bug");
		}
	}

	/**
	 * Helpmethod to move an embededmessage from one textchannel to another
	 * textchannel.
	 * 
	 * @param messageID the message which has to be moved
	 * @param guild		the guild to get the necessary textchannels
	 * @param member    the member who managed the bugreport
	 * @param color     the color for the embedmessage
	 * @param accepted  if the bugreport got accepted or denied
	 */
	private void moveReport(String messageID, Guild guild, Member member, Color color, boolean accepted, String status) {

		TextChannel tcProg = guild.getTextChannelById(BugchannelManager.m_Progress);
		TextChannel tcOpen = guild.getTextChannelById(BugchannelManager.m_Managed);

		tcProg.retrieveMessageById(messageID).queue((msg) -> {
			tcOpen.sendMessageEmbeds(MessageBuilder.createReport(member, msg.getEmbeds().get(0).getDescription(), color,
					accepted, status)).queue();

			tcProg.sendMessageEmbeds(MessageBuilder.createReport(member, msg.getEmbeds().get(0).getDescription(), color,
					accepted, status))
					.setActionRow(Button.success("bearbeitet", "Bearbeitet"), Button.primary("pruef", "In Prüfung"),
							Button.primary("inBearbeitung", "In Bearbeitung"), Button.danger("keinBug", "Kein Bug"))
					.queue();
			msg.delete().queue();
		});
	}
}
