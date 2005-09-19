<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<%--
Mailtool.rolesTable returns DataModel from SelectByRolesTable.getRoles

--%>

<h:dataTable value="#{Mailtool.recipientSelector.dataModel}" var="role">
	<h:column>
		<h:selectBooleanCheckbox value="#{role.selected}"/>
	</h:column>
	<h:column>
		<h:outputText value="#{role.name}" />
	</h:column>
</h:dataTable>
