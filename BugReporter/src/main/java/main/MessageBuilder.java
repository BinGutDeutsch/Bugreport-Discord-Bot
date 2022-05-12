package main;

import java.awt.Color;
import java.time.OffsetDateTime;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;

/**
 * Helpclass for creating embeded messages such as new Bugreports and simple
 * Message like errors.
 * 
 * @author jonaw
 *
 */
public class MessageBuilder {

	/**
	 * Creates an embeded message for a new or moved bugreport
	 * 
	 * @param member      The Member who wrote the bugreport or managed the
	 *                    bugreport
	 * @param description The description of the bugreport
	 * @param color       The color of the embeded message
	 * @param accepted    Boolean if the bugreport got accepted or denied. If
	 *                    <code>null</code> then the bugreport just got written
	 * @return the embeded message
	 */
	public static MessageEmbed createReport(Member member, String description, Color color, Boolean accepted) {
		EmbedBuilder eb = new EmbedBuilder();

		eb.setColor(color);
		eb.setTitle("Bugreport");
		eb.setThumbnail(DiscordBot.getCurrentBot().getShardManager().getSelfUser().getAvatarUrl());
		eb.setDescription(description);
		eb.setTimestamp(OffsetDateTime.now());

		if (accepted == null) {
			eb.setFooter(member.getUser().getAsTag(), member.getUser().getEffectiveAvatarUrl());
		} else if (accepted) {
			eb.setFooter("Angenommen von: " + member.getUser().getAsTag(), member.getUser().getEffectiveAvatarUrl());
		} else {
			eb.setFooter("Abgelehnt von: " + member.getUser().getAsTag(), member.getUser().getEffectiveAvatarUrl());
		}

		return eb.build();
	}

	/**
	 * Creates a basic embededmessage for messages like help or errormessages
	 * 
	 * @param description The Description which is the output in the message. Not
	 *                    <code>null</code>
	 * @return The embededmessage
	 */
	public static MessageEmbed createEmbed(String description) {
		EmbedBuilder eb = new EmbedBuilder();

		eb.setColor(Color.lightGray);
		eb.setTitle("Bugreport");
		eb.setDescription(description);
		eb.setTimestamp(OffsetDateTime.now());
		return eb.build();
	}
}
