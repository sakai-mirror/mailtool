package org.sakaiproject.tool.mailtool;

import java.util.List;
import javax.faces.component.UIComponent;

public interface RecipientSelector {

	//Method to populate groups and roles
	public void populate(List /* EmailGroup */ emailgroups);
	
	//Method to get everyone to email
	public List /* EmailUser */ getSelectedUsers();
	
	public List /* EmailGroup */ getSelectedUsersByGroup();
	

}
