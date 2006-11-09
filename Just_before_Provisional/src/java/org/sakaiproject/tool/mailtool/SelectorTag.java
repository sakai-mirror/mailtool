package org.sakaiproject.tool.mailtool;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;

public class SelectorTag extends UIComponentTag {
	public String getRendererType() { return null; }
	public String getComponentType() { return "MailtoolSelector"; }
	
	private String value = null;
	public void setValue(String value) { this.value = value; }
	
	public void setProperties(UIComponent component) {
		super.setProperties(component);
		
		FacesContext context = FacesContext.getCurrentInstance();
		Application app = context.getApplication();
		ValueBinding vb = app.createValueBinding(value);
		component.setValueBinding("value", vb);
	}
	
	public void release() {
		super.release();
		value = null;
	}
}
