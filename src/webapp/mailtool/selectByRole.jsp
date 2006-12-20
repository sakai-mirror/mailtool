<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<%--
Mailtool.rolesTable returns DataModel from SelectByRolesTable.getRoles

--%>
<h:panelGroup rendered="#{Mailtool.currentMode=='compose' }">	
	<h:dataTable value="#{Mailtool.recipientSelector.dataModel}" var="role"  styleClass="listHier"  columnClasses="attach,checkbox" border="0" style="margin:0">
		<h:column>
			<h:selectBooleanCheckbox id="col1check" value="#{role.selected}"/>
		</h:column>
		<h:column>
			<h:outputLabel value="#{role.name}" for="col1check" style="white-space:nowrap" />
		</h:column>
	</h:dataTable>
</h:panelGroup>
<h:panelGroup rendered="#{Mailtool.currentMode=='options' }" style="height:100%;overflow:hidden;display:block;margin:.5em 0;padding:.3em;color:#555 !important" styleClass="inopPanel" >
	<h:outputText  value="Preview (inactive)"  style="padding:.5em"/>
	<h:dataTable value="#{Mailtool.recipientSelector.dataModel}" var="role" styleClass="listHier"  columnClasses="attach,checkbox" border="0">
		<h:column>
			<h:selectBooleanCheckbox disabled="true" id="col1check" value="#{role.selected}"/>
		</h:column>
		<h:column>
			<h:outputLabel value="#{role.name}" for="col1check" style="color:#777;white-space:nowrap" />
		</h:column>
	</h:dataTable>
</h:panelGroup>