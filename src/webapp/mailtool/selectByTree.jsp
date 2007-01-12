<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<%--
Mailtool.userTree returns DataModel from SelectByTree.getRows

--%>
                                                                                                

<%-- Tree Table --%>
<h:panelGroup rendered="#{Mailtool.currentMode=='compose' }">
	<h:dataTable value="#{Mailtool.recipientSelector.dataModel}" var="row"  border="0" style="margin:0;width:100%" cellpadding="0" cellspacing="0">
		<h:column>
			<h:panelGroup rendered="#{row.collapsed}">
				<h:selectBooleanCheckbox id="selectAllCheckbox" value="#{row.allSelected}" />
				<h:outputLabel for="selectAllCheckbox" value="#{msgs.all_prefix} #{row.pluralRolename} " />
				<h:outputText value=" - "  />
				<h:commandLink actionListener="#{row.actionExpand}" value="#{msgs.select_individuals_button} #{row.pluralRolename}" />
			</h:panelGroup>
			<h:panelGroup rendered="#{not row.collapsed}">
				<h:outputText value="#{msgs.select_individual_prefix} " />
				<h:outputText value="#{row.pluralRolename} "  />
				<h:outputText value=" - "  />
				<h:commandLink actionListener="#{row.actionCollapse}" value="#{msgs.collapse_button}" />
				<h:dataTable value="#{row.userTable}" var="user_row"  styleClass="listHier lines nolines hierItemBlockWrapper" columnClasses="attach,,attach,"  cellpadding="0" cellspacing="0" style="width:auto;font-weight:normal">
					<h:column>
						<h:selectBooleanCheckbox id="indCol1Checkbox" value="#{user_row.selected1}"  />
					</h:column>
					<h:column>
						<h:outputLabel for="indCol1Checkbox" value="#{user_row.user1.displayname}" style="white-space:nowrap" />
					</h:column>
					<h:column>
						<h:selectBooleanCheckbox id="indCol2Checkbox" value="#{user_row.selected2}" rendered="#{user_row.render2}" style="margin-left:2em;"/>
					</h:column>
					<h:column>
						<h:outputLabel for="indCol2Checkbox" rendered="#{user_row.render2}" value="#{user_row.user2.displayname}" style="white-space:nowrap" />
					</h:column>
				</h:dataTable>
			</h:panelGroup>
		</h:column>
	</h:dataTable>
</h:panelGroup>

<h:panelGroup rendered="#{Mailtool.currentMode=='options' }" style="height:100%;overflow:hidden;display:block;margin:.5em 0;padding:0;color:#555 !important" styleClass="inopPanel" >
		<h:outputText  value="Preview (inactive)"  style="padding:.5em"/>
		<h:dataTable value="#{Mailtool.recipientSelector.dataModel}" var="row"  border="0" style="margin:1em 0" cellpadding="0" cellspacing="0" >
			<h:column>
				<h:panelGroup rendered="#{row.collapsed}">
					<h:selectBooleanCheckbox disabled="true" id="selectAllCheckbox" value="#{row.allSelected}" />
					<h:outputLabel for="selectAllCheckbox" value="#{msgs.all_prefix} #{row.pluralRolename} " />
					<h:outputText value=" -  " />
					<h:commandLink actionListener="#{row.actionExpand}" value="#{msgs.select_individuals_button} #{row.pluralRolename}" />
				</h:panelGroup>
				<h:panelGroup rendered="#{not row.collapsed}">
					<h:outputText value="#{msgs.select_individual_prefix} " />
					<h:outputText value="#{row.pluralRolename} " />
					<h:outputText value=" -  " />
					<h:commandLink actionListener="#{row.actionCollapse}" value="#{msgs.collapse_button}" />
					<h:dataTable value="#{row.userTable}" var="user_row" columnClasses="attach,,attach,"  cellpadding="0" cellspacing="0" style="width:auto;font-weight:normal" border="0" styleClass="listHier lines nolines hierItemBlockWrapper">
						<h:column>
							<h:selectBooleanCheckbox disabled="true" id="indCol1Checkbox" value="#{user_row.selected1}" />
						</h:column>
						<h:column>
							<h:outputLabel for="indCol1Checkbox" value="#{user_row.user1.displayname}"  style="color:#777;white-space:nowrap" />
						</h:column>
						<h:column>
							<h:selectBooleanCheckbox disabled="true" id="indCol2Checkbox" value="#{user_row.selected2}" rendered="#{user_row.render2}" />
						</h:column>
						<h:column>
							<h:outputLabel for="indCol2Checkbox" rendered="#{user_row.render2}" value="#{user_row.user2.displayname}"  style="color:#777;white-space:nowrap"  />
						</h:column>
					</h:dataTable>
				</h:panelGroup>	
			</h:column>
		</h:dataTable>
</h:panelGroup>

