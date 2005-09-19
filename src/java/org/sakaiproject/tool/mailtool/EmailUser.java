package org.sakaiproject.tool.mailtool;

public class EmailUser {
	String m_userid = "";
	String m_displayname = "";
	String m_email = "";
	
	public EmailUser(String userid, String displayname, String email)
	{
		m_userid = userid;
		m_displayname = displayname;
		m_email = email;
	}
	
	public boolean equals(EmailUser user)
	{
		if (user.getUserid().equals(m_userid) &&
			user.getEmail().equals(m_email))
		{
			return true;
		}
		else
			return false;
		
	}
	
	
	public String getDisplayname() {
		return m_displayname;
	}
	public void setDisplayname(String m_displayname) {
		this.m_displayname = m_displayname;
	}
	public String getEmail() {
		return m_email;
	}
	public void setEmail(String m_email) {
		this.m_email = m_email;
	}
	public String getUserid() {
		return m_userid;
	}
	public void setUserid(String m_userid) {
		this.m_userid = m_userid;
	}
	
	public String getNiceEmail()
	{
		String nicestring = "";
		
		if (m_displayname == "" || m_displayname == null)
			return m_email;
		else
			nicestring = m_displayname + " <" + m_email + ">";
		
		return nicestring;
	}
}
