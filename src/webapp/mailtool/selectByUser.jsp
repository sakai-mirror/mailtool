<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<%-- Users Table --%>
<%-- var=row is a SelectByUserTable.TableEntry      --%>
<h:panelGroup rendered="#{Mailtool.currentMode=='compose' }">
<h:dataTable value="#{Mailtool.recipientSelector.dataModel}" var="row">
	<h:column>
		<h:selectBooleanCheckbox id="col1check" value="#{row.selected1}" />
	</h:column>
	<h:column>
		<h:outputLabel value="#{row.user1.displayname}" for="col1check"/>
	</h:column>
	<h:column>
		<h:selectBooleanCheckbox rendered="#{row.render2}" value="#{row.selected2}" id="col2check" />
	</h:column>
	<h:column>
		<h:outputLabel rendered="#{row.render2}" value="#{row.user2.displayname}" for="col2check" />
	</h:column>
</h:dataTable>
</h:panelGroup>

<h:panelGroup rendered="#{Mailtool.currentMode=='options' }">
<h:dataTable value="#{Mailtool.recipientSelector.dataModel}" var="row" columnClasses="gray-out, gray-out, gray-out, gray-out">
	<h:column>
		<h:selectBooleanCheckbox disabled="true" id="col1check" value="#{row.selected1}" />
	</h:column>
	<h:column>
		<h:outputLabel value="#{row.user1.displayname}" for="col1check" style="color:#777" />
	</h:column>
	<h:column>
		<h:selectBooleanCheckbox disabled="true" rendered="#{row.render2}" value="#{row.selected2}" id="col2check" />
	</h:column>
	<h:column>
		<h:outputLabel rendered="#{row.render2}" value="#{row.user2.displayname}" for="col2check"  style="color:#777"/>
	</h:column>
</h:dataTable>
</h:panelGroup>