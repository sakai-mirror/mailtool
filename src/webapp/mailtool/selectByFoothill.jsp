<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://sakaiproject.org/jsf/sakai" prefix="sakai" %>
<%@ taglib uri="http://sakaiproject.org/jsf/mailtool" prefix="mailtool" %>

<%--
<mailtool:selector value="#{Mailtool.emailGroups}"/>
--%>

<h:panelGrid columns="3">
<%--
	<h:panelGrid columns="1">
		<h:outputText value="Send to all:" />	
		<h:selectManyCheckbox layout="pageDirection" value="#{Mailtool.recipientSelector.checkedRoles}">
			<f:selectItems value="#{Mailtool.recipientSelector.rolesChecklist}"/>
		</h:selectManyCheckbox>	
	</h:panelGrid>
--%>
	<h:panelGrid columns="1">
		<h:outputText value="#{msgs.recipients}" />
		
		<h:selectManyListbox id="selectTo" size="15" value="#{Mailtool.recipientSelector.selectedItems}">
	  		<f:selectItems value="#{Mailtool.recipientSelector.listbox}"/>
		</h:selectManyListbox>
	</h:panelGrid>
	
	<h:panelGrid columns="1">
		
		<sakai:button_bar>
			<sakai:button_bar_item
				action="#{Mailtool.recipientSelector.processSelectAll}"
				value="#{msgs.select_all_button}"
				rendered="true"
				immediate="false" />
		</sakai:button_bar>	
			
		<sakai:button_bar>	
			<sakai:button_bar_item
				action="#{Mailtool.recipientSelector.processSelectNone}"
				value="#{msgs.select_none_button}"
				rendered="true"
				immediate="false" />
		</sakai:button_bar>	
	</h:panelGrid>


</h:panelGrid>
