package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Helpclass to configurate the channel names. Channel IDs have to be put in the
 * bugreport.properties manuelly
 * 
 * @author jonaw
 *
 */
public class BugchannelManager {
	public static String m_Report;
	public static String m_Managed;
	public static String m_Progress;
	public static String m_ManageRole;

	public static void update() {
		m_Report = getConfigProp("report_channel_id");
		m_Managed = getConfigProp("managed_channel_id");
		m_Progress = getConfigProp("progress_channel_id");
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
