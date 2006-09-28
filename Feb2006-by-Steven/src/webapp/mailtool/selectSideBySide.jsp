<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://sakaiproject.org/jsf/sakai" prefix="sakai" %>

<h:panelGrid columns="3">

	<h:panelGrid columns="1">
		<h:outputText value="#{msgs.select_from_list}"/>
	<h:selectManyListbox size="10" value="#{Mailtool.recipientSelector.sourceSelected}">
	<%--	<h:selectManyListbox size="10"> --%>
    		<f:selectItems value="#{Mailtool.recipientSelector.sourceListbox}"/>
		</h:selectManyListbox>
	</h:panelGrid>

	<h:panelGrid columns="1">
		<sakai:button_bar>
			<sakai:button_bar_item
				action="#{Mailtool.recipientSelector.processAddButton}"
				value="#{msgs.add_button}"
				rendered="true"
				immediate="false" />
		</sakai:button_bar>
		
		<sakai:button_bar>
			<sakai:button_bar_item
				action="#{Mailtool.recipientSelector.processRemoveButton}"
				value="#{msgs.remove_button}"
				rendered="true"
				immediate="false" />
		</sakai:button_bar>
	</h:panelGrid>
	
	<h:panelGrid columns="1">
		<h:outputText value="#{msgs.message_recipients}"/>
		<h:selectManyListbox size="10" value="#{Mailtool.recipientSelector.sinkSelected}">
    		<f:selectItems value="#{Mailtool.recipientSelector.sinkListbox}"/>
		</h:selectManyListbox>
	</h:panelGrid>

</h:panelGrid>
