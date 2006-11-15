<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<%--
Mailtool.rolesTable returns DataModel from SelectByRolesTable.getRoles

--%>
<h:panelGroup rendered="#{Mailtool.currentMode=='compose' }">
<h:dataTable value="#{Mailtool.recipientSelector.dataModel}" var="role">
	<h:column>
		<h:selectBooleanCheckbox id="col1check" value="#{role.selected}"/>
	</h:column>
	<h:column>
		<h:outputLabel value="#{role.name}" for="col1check"  style="font-weight:bold;"/>
	</h:column>
</h:dataTable>
</h:panelGroup>

<h:panelGroup rendered="#{Mailtool.currentMode=='options' }">
<h:dataTable value="#{Mailtool.recipientSelector.dataModel}" var="role" columnClasses="gray-out, gray-out">
	<h:column>
		<h:selectBooleanCheckbox disabled="true" id="col1check" value="#{role.selected}"/>
	</h:column>
	<h:column>
		<h:outputLabel value="#{role.name}" for="col1check"  style="color:#777; font-weight:bold;"/>
	</h:column>
</h:dataTable>
</h:panelGroup>