package command.commands;

import java.awt.Color;
import java.util.EnumSet;
import java.util.List;

import command.ICommand;
import main.BugchannelManager;
import main.MessageBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 * Manages the perform of the Setup Command and the responses
 * 
 * @author jonaw
 *
 */
public class SetupCommand implements ICommand {

	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {
		if (!m.hasPermission(Permission.ADMINISTRATOR, null)) {
			m.getUser().openPrivateChannel().queue((ch) -> {
				ch.sendMessageEmbeds(MessageBuilder
						.createEmbed("> Du hast keine Berechtigung auf diesem Server um ein Setup auszuführen."))
						.queue();
			});
			return;
		}
		if (setupChannel(m.getGuild(), channel)) {
			channel.sendMessageEmbeds(MessageBuilder.createEmbed("> Das Setup wurde erfolgreich ausgeführt.")).queue();
		}
	}

	/**
	 * Sets up the channel
	 * 
	 * @param guild   the Guild where the setup was started
	 * @param channel the channel where setup was started
	 * @return <code>true</code> if setup was successful. <code>false</code> if an
	 *         error occurred.
	 */
	private boolean setupChannel(Guild guild, TextChannel channel) {
		boolean channelChecked = checkChannel(guild, channel);
		if (!channelChecked) {
			return false;
		}
		createChannel(guild);
		createRole(guild);
		return true;
	}

	/**
	 * Checks if the bugs category exists or if more then one exists. If one exists
	 * it will delete all textchannels in it.
	 * 
	 * @param guild   The guild where the command was performed
	 * @param channel The Channel where the command was performed
	 * @return <code>true</code> if channel got deleted. <code>false</code> if there
	 *         is no bugs category or there is more then one.
	 */
	private boolean checkChannel(Guild guild, TextChannel channel) {
		List<Category> bugsCategory = guild.getCategoriesByName("Bugs", true);
		if (bugsCategory.size() == 0) {
			channel.sendMessageEmbeds(
					MessageBuilder.createEmbed("> Es muss zuerst eine Kategorie namens \"Bugs\" erstellt werden."))
					.queue();
			return false;
		}
		if (bugsCategory.size() > 1) {
			channel.sendMessageEmbeds(
					MessageBuilder.createEmbed("> Es existieren mehr als eine Kategorie namens \"Bugs\".")).queue();
			return false;
		}
		List<GuildChannel> channels = bugsCategory.get(0).getChannels();
		for (GuildChannel guildChannel : channels) {
			guildChannel.delete().queue();
		}

		return true;

	}

	/**
	 * Creates the all necessary textchannel to use the bot
	 * 
	 * @param guild the guild where the channel have to be created
	 */
	private void createChannel(Guild guild) {
		List<Category> categorys = guild.getCategoriesByName("Bugs", true);
		Category category = categorys.get(0);
		category.createTextChannel("bugs-melden").queue();
		category.createTextChannel("bugs-progress")
				.addPermissionOverride(guild.getPublicRole(), EnumSet.of(Permission.MESSAGE_SEND),
						EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_ADD_REACTION))
				.addPermissionOverride(guild.getRoleById(BugchannelManager.m_ManageRole), EnumSet.of(Permission.VIEW_CHANNEL),
						EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_ADD_REACTION))
				.queue();
		category.createTextChannel("bugs-offen")
				.addPermissionOverride(guild.getPublicRole(), EnumSet.of(Permission.VIEW_CHANNEL),
						EnumSet.of(Permission.MESSAGE_SEND, Permission.MESSAGE_ADD_REACTION))
				.queue();

	}

	/**
	 * Creates the role to manage bugreports if it doesnt already exist
	 * 
	 * @param guild The guild where the role has to be created
	 */
	private void createRole(Guild guild) {
		if (guild.getRolesByName("Bugs verwalten", true).size() == 0) {
			guild.createRole().setName("Bugs verwalten").setColor(Color.MAGENTA).queue();
		}
	}
}
