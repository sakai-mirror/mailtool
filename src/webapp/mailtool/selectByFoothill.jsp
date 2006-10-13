<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://sakaiproject.org/jsf/sakai" prefix="sakai" %>
<%@ taglib uri="http://sakaiproject.org/jsf/mailtool" prefix="mailtool" %>

<h:panelGrid columns="3" cellspacing="0" cellpadding="0">

	<h:panelGrid columns="1" cellspacing="0" cellpadding="3">

		<h:selectManyListbox id="selectTo" size="5" value="#{Mailtool.recipientSelector.selectedItems}">
	  		<f:selectItems value="#{Mailtool.recipientSelector.listbox}"/>
		</h:selectManyListbox>
	</h:panelGrid>

	<h:panelGrid columns="1" cellspacing="0" cellpadding="0">

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

