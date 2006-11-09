package org.sakaiproject.tool.mailtool;

import java.util.ArrayList;
import java.util.List;

public class EmailGroup {
	protected EmailRole m_emailrole = null;
	protected List /* EmailUser */ m_emailusers = new ArrayList();
	
	public EmailGroup(EmailRole emailrole, List /* EmailUser */ emailusers)
	{
		m_emailrole = emailrole;
		m_emailusers = emailusers;
	}

	public String getRolePlural() 
	{
		return m_emailrole.getRoleplural();
	}

	public EmailRole getEmailrole() {
		return m_emailrole;
	}
	public void setEmailrole(EmailRole m_emailrole) {
		this.m_emailrole = m_emailrole;
	}
	public List getEmailusers() {
		return m_emailusers;
	}
	public void setEmailusers(List m_emailusers) {
		this.m_emailusers = m_emailusers;
	}

}