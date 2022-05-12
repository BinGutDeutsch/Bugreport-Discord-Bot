package main;

import javax.security.auth.login.LoginException;

import command.CommandManager;
import listener.CommandListener;
import listener.ReportListener;
import listener.ReportManageListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

/**
 * Main class for creating the JDA instance
 * 
 * @author jonaw
 *
 */
public class DiscordBot {

	private static DiscordBot instance;
	public final static String PREFIX = ".bug";

	/**
	 * JDA instance of the Bot
	 */
	private JDA m_ShardManager;
	/**
	 * Command manager to perform commands
	 */
	private CommandManager m_CmdManager;
	/**
	 * Channel manager for the configurations of the textchannel
	 */
	private BugchannelManager m_ChannelManager;
	public static JDABuilder builder;

	public static void main(String[] args) throws LoginException {
		try {
			new DiscordBot();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public DiscordBot() throws LoginException {
		instance = this;
		builder = JDABuilder.createDefault("");

		m_CmdManager = new CommandManager();
		m_ChannelManager = new BugchannelManager();
		builder.setStatus(OnlineStatus.ONLINE);
		builder.setActivity(Activity.watching("Bugreports"));
		builder.addEventListeners(new CommandListener());
		builder.addEventListeners(new ReportListener());
		builder.addEventListeners(new ReportManageListener());
		m_ShardManager = builder.build();
	}

	public CommandManager getCmdManager() {
		return m_CmdManager;
	}

	public BugchannelManager getChannelManager() {
		return m_ChannelManager;
	}

	public JDA getShardManager() {
		return m_ShardManager;
	}

	public static DiscordBot getCurrentBot() {
		return instance;
	}
};