<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<%--
Mailtool.rolesTable returns DataModel from SelectByRolesTable.getRoles

--%>

<h:dataTable value="#{Mailtool.recipientSelector.dataModel}" var="role">
	<h:column>
		<h:selectBooleanCheckbox id="col1check" value="#{role.selected}"/>
	</h:column>
	<h:column>
		<h:outputLabel value="#{role.name}" for="col1check" />
	</h:column>
</h:dataTable>