<%-- HTML JSF tag libary --%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%-- Core JSF tag library --%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%-- Main Sakai tag library --%>
<%@ taglib uri="http://sakaiproject.org/jsf/sakai" prefix="sakai" %>

<f:view>
<f:loadBundle basename="org.sakaiproject.tool.mailtool.Messages" var="msgs"/>

<sakai:view_container title="Sent Email">

<h:form>
	<sakai:view_content>
		<sakai:messages />
		
		
<sakai:button_bar>

	<sakai:button_bar_item
		action="send"
		value="#{msgs.send_mail_button}"
		rendered="#{Mailtool.allowedToSend}"
		immediate="false" />
		
	<sakai:button_bar_item
		action="cancel"
		value="#{msgs.cancel_mail_button}"
		rendered="#{Mailtool.allowedToSend}"
		immediate="false" />
		

</sakai:button_bar>	
	
	</sakai:view_content>
</h:form>

</sakai:view_container>

</f:view>