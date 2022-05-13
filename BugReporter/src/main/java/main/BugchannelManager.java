package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Helpclass to configurate the channel names. If you want to configure the
 * channel names, just rename the member attributes.
 * 
 * @author jonaw
 *
 */
public class BugchannelManager {
	public static String m_Report;
	public static String m_Open;
	public static String m_Accepted;
	public static String m_Denied;
	public static String m_ManageRole;

	public static void update() {
		m_Report = getConfigProp("report_channel_id");
		m_Open = getConfigProp("open_channel_id");
		m_Accepted = getConfigProp("accepted_channel_id");
		m_Denied = getConfigProp("denied_channel_id");
		m_ManageRole = getConfigProp("role_id");
	}

	private static String getConfigProp(String channel) {
		File configFile = new File("bugreport.properties");
		String host = "";
		try {
			FileReader reader = new FileReader(configFile);
			Properties props = new Properties();
			props.load(reader);

			host = props.getProperty(channel);

			reader.close();
		} catch (FileNotFoundException ex) {
			// file does not exist
		} catch (IOException ex) {
			// I/O error
		}
		return host;
	}
	

}
