<%-- HTML JSF tag libary --%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%-- Core JSF tag library --%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%-- Main Sakai tag library --%>
<%@ taglib uri="http://sakaiproject.org/jsf/sakai" prefix="sakai" %>

<f:view>
	<f:loadBundle basename="org.sakaiproject.tool.mailtool.Messages" var="msgs" />
<sakai:view_container title="Sent Email">
<h:form>
	<sakai:view_content>
		<sakai:messages />
<h:panelGroup>	
		<h:outputText escape="false" value="#{Mailtool.results}" />
</h:panelGroup>
<br/>
<%--
<h:panelGroup rendered="#{Mailtool.num_files > 0}"><h:outputText value="Number of Attachment(s): " /><h:outputText value="#{Mailtool.num_files}" /></h:panelGroup><br/>
<h:panelGroup rendered="#{Mailtool.num_files > 0}"><h:outputText value="Attachments: " /><f:verbatim><br/></f:verbatim></h:panelGroup>
<h:panelGroup rendered="#{Mailtool.num_files > 0}"><h:outputText value="#{Mailtool.files[0]} " /><f:verbatim><br/></f:verbatim></h:panelGroup>
<h:panelGroup rendered="#{Mailtool.num_files > 1}"><h:outputText value="#{Mailtool.files[1]} " /><f:verbatim><br/></f:verbatim></h:panelGroup>
<h:panelGroup rendered="#{Mailtool.num_files > 2}"><h:outputText value="#{Mailtool.files[2]} " /><f:verbatim><br/></f:verbatim></h:panelGroup>
<br/>
--%>	
		<sakai:button_bar>
			<sakai:button_bar_item
				action="main_onepage"
				value="#{msgs.ok_button}"
				rendered="true"
				immediate="false" />
		</sakai:button_bar>	
	
	</sakai:view_content>
</h:form>
</sakai:view_container>

</f:view>