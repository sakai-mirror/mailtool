<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<%--
Mailtool.userTree returns DataModel from SelectByTree.getRows

--%>


<%-- Tree Table --%>
	<h:dataTable value="#{Mailtool.recipientSelector.dataModel}" var="row">
		<h:column>
			<h:selectBooleanCheckbox id="selectAllCheckbox" rendered="#{row.collapsed}" value="#{row.allSelected}" />
			<h:outputLabel for="selectAllCheckbox" rendered="#{row.collapsed}" value="#{msgs.all_prefix} #{row.pluralRolename}" />
			
			<h:commandLink rendered="#{row.collapsed}" actionListener="#{row.actionExpand}" onmouseup="submit()" value="#{msgs.select_individuals_button}" />
			
			<h:outputText rendered="#{not row.collapsed}" value="#{msgs.select_individual_prefix} " />
			<h:outputText rendered="#{not row.collapsed}" value="#{row.pluralRolename}" />
			<h:commandLink rendered="#{not row.collapsed}" actionListener="#{row.actionCollapse}" onmouseup="submit()" value="#{msgs.collapse_button}" />
			
			<h:dataTable rendered="#{not row.collapsed}" value="#{row.userTable}" var="user_row" >
				<h:column>
					<h:selectBooleanCheckbox id="indCol1Checkbox" value="#{user_row.selected1}" />
				</h:column>
				<h:column>
					<h:outputLabel for="indCol1Checkbox" value="#{user_row.user1.displayname}" />
				</h:column>
				<h:column>
					<h:selectBooleanCheckbox id="indCol2Checkbox" value="#{user_row.selected2}" rendered="#{user_row.render2}" />
				</h:column>
				<h:column>
					<h:outputLabel for="indCol2Checkbox" rendered="#{user_row.render2}" value="#{user_row.user2.displayname}" />
				</h:column>
				
			</h:dataTable>
			
		</h:column>
	</h:dataTable>