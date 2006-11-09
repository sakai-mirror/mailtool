<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<%-- Users Table --%>
<%-- var=row is a SelectByUserTable.TableEntry      --%>
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
