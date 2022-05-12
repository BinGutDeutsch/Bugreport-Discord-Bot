package main;

/**
 * Helpclass to configurate the channel names. If you want to configure the
 * channel names, just rename the member attributes.
 * 
 * @author jonaw
 *
 */
public class BugchannelManager {
	/**
	 * if want to but a blank char in the name u have to fill it with "-"
	 * 
	 * if you want to put emojis in -> follow example below
	 */
	private String m_Report = "「📖」bugs-melden";
	private String m_Open = "「📖」bugs-offen";
	private String m_Accepted = "「📖」bugs-angenommen";
	private String m_Denied = "「📖」bugs-abgelehnt";

	public String getReport() {
		return m_Report;
	}

	public String getOpen() {
		return m_Open;
	}

	public String getAccepted() {
		return m_Accepted;
	}

	public String getDenied() {
		return m_Denied;
	}

}
