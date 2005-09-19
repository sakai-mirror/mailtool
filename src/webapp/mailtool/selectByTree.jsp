<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<%--
Mailtool.userTree returns DataModel from SelectByTree.getRows

--%>


<%-- Tree Table --%>
	<h:dataTable value="#{Mailtool.recipientSelector.dataModel}" var="row">
		<h:column>
			<h:selectBooleanCheckbox rendered="#{row.collapsed}" value="#{row.allSelected}" />
			<h:outputText rendered="#{row.collapsed}" value="All " />
			<h:outputText rendered="#{row.collapsed}" value="#{row.pluralRolename}" />
			<h:commandLink rendered="#{row.collapsed}" actionListener="#{row.actionExpand}" onmouseup="submit()" value="Select Individuals" />
			
			
			<h:outputText rendered="#{not row.collapsed}" value="Select Individual " />
			<h:outputText rendered="#{not row.collapsed}" value="#{row.pluralRolename}" />
			<h:commandLink rendered="#{not row.collapsed}" actionListener="#{row.actionCollapse}" onmouseup="submit()" value="Collapse" />
			
			<h:dataTable rendered="#{not row.collapsed}" value="#{row.userTable}" var="user_row" >
				<h:column>
					<h:selectBooleanCheckbox value="#{user_row.selected1}" />
				</h:column>
				<h:column>
					<h:outputText value="#{user_row.user1.displayname}" />
				</h:column>
				<h:column>
					<h:selectBooleanCheckbox value="#{user_row.selected2}" rendered="#{user_row.render2}" />
				</h:column>
				<h:column>
					<h:outputText rendered="#{user_row.render2}" value="#{user_row.user2.displayname}" />
				</h:column>
				
			</h:dataTable>
			
		</h:column>
	</h:dataTable>