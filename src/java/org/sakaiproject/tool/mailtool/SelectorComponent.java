package org.sakaiproject.tool.mailtool;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.component.UISelectMany;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * @author sgithens
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SelectorComponent extends UIOutput {

	public SelectorComponent() {
		
	}
	
	public void encodeBegin(FacesContext context) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		String clientId = getClientId(context);
		
		writer.startElement("div", this);
		writer.writeAttribute("id", "selectdiv", "");
		writer.endElement("div");
		
	}
	
	 
	public void decode(FacesContext context, UIComponent component) {
		String clientId = component.getClientId(context);
		Map requestParameterMap = context.getExternalContext()
									.getRequestParameterMap();
		System.out.println("SWG:inside Decode");
		for (Iterator i = requestParameterMap.keySet().iterator(); i.hasNext();)
		{
			String key = (String) i.next();
			System.out.println("SWG:decode key: " + key + " value: " + requestParameterMap.get(key));
			
		}
		UIInput comp = (UIInput) component;
		comp.setSubmittedValue(comp);
	}
}
