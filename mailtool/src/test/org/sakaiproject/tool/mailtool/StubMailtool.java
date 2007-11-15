package org.sakaiproject.tool.mailtool;

/**
 * Overrides {@link Mailtool} methods such that that class
 * can be instantiated in the absence of a fully-formed
 * Sakai environment.
 * 
 * @author dmccallum@unicon.net
 *
 */
public class StubMailtool extends Mailtool {

	protected String getSiteID() {
		return "THIS_IS_A_STUB_SITE_ID";
	}
	
	protected String getSiteTitle() {
		return "THIS_IS_A_STUB_SITE_TITLE";
	}

	protected String getSiteRealmID() {
		return "/site/" + getSiteID();
	}
	
	public String getGroupAwareRole() {
		return groupAwareRoleDefault;
	}
	
	protected String getConfigParam(String parameter) {
		return "";
	}
	
	public int readMaxNumAttachment() {
		return 10000;
	}
	
	protected String getSiteType() {
		return "";
	}
	
	public void getRecipientSelectors() {
		// do nothing
	}
	
	public void checkifGroupAwareRoleExist() {
		// do nothing
	}
	
	public boolean isEmailArchiveAddedToSite() {
		return false;
	}
	
}
